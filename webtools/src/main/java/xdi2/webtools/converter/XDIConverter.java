package xdi2.webtools.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xdi2.core.Graph;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.XDIReader;
import xdi2.core.io.XDIReaderRegistry;
import xdi2.core.io.XDIWriter;
import xdi2.core.io.XDIWriterRegistry;
import xdi2.core.io.readers.AutoReader;
import xdi2.core.io.writers.XDIDisplayWriter;

/**
 * Servlet implementation class for Servlet: XDIConverter
 *
 */
public class XDIConverter extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 2578333401873629083L;

	private static Logger log = LoggerFactory.getLogger(XDIConverter.class);

	private static MemoryGraphFactory graphFactory;
	private static List<String> sampleInputs;

	static {

		graphFactory = MemoryGraphFactory.getInstance();
		graphFactory.setSortmode(MemoryGraphFactory.SORTMODE_ORDER);

		sampleInputs = new ArrayList<String> ();

		while (true) {

			InputStream inputStream = XDIConverter.class.getResourceAsStream("graph" + (sampleInputs.size() + 1) + ".xdi");
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int i;

			try {

				while ((i = inputStream.read()) != -1) outputStream.write(i);
				sampleInputs.add(new String(outputStream.toByteArray()));
			} catch (Exception ex) {

				break;
			} finally {

				try {

					inputStream.close();
					outputStream.close();
				} catch (Exception ex) {

				}
			}
		}
	}

	public XDIConverter() {

		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sample = request.getParameter("sample");
		if (sample == null) sample = "1";

		request.setAttribute("resultFormat", XDIDisplayWriter.FORMAT_NAME);
		request.setAttribute("writeImplied", null);
		request.setAttribute("writeOrdered", "on");
		request.setAttribute("writeInner", "on");
		request.setAttribute("writePretty", null);
		request.setAttribute("sampleInputs", Integer.valueOf(sampleInputs.size()));
		request.setAttribute("input", sampleInputs.get(Integer.parseInt(sample) - 1));

		request.getRequestDispatcher("/XDIConverter.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resultFormat = request.getParameter("resultFormat");
		String writeImplied = request.getParameter("writeImplied");
		String writeOrdered = request.getParameter("writeOrdered");
		String writeInner = request.getParameter("writeInner");
		String writePretty = request.getParameter("writePretty");
		String from = request.getParameter("from");
		String input = request.getParameter("input");
		String submit = request.getParameter("submit");
		String rawoutput = "";
		String output = "";
		String stats = "-1";
		String error = null;

		Properties xdiWriterParameters = new Properties();

		xdiWriterParameters.setProperty(XDIWriterRegistry.PARAMETER_IMPLIED, "on".equals(writeImplied) ? "1" : "0");
		xdiWriterParameters.setProperty(XDIWriterRegistry.PARAMETER_ORDERED, "on".equals(writeOrdered) ? "1" : "0");
		xdiWriterParameters.setProperty(XDIWriterRegistry.PARAMETER_INNER, "on".equals(writeInner) ? "1" : "0");
		xdiWriterParameters.setProperty(XDIWriterRegistry.PARAMETER_PRETTY, "on".equals(writePretty) ? "1" : "0");
		xdiWriterParameters.setProperty(XDIWriterRegistry.PARAMETER_HTML, "Html!".equals(submit) ? "1" : "0");

		XDIReader xdiReader = XDIReaderRegistry.forFormat(from, null);
		XDIWriter xdiResultWriter = XDIWriterRegistry.forFormat(resultFormat, xdiWriterParameters);
		Graph graph = graphFactory.openGraph();

		try {

			StringReader reader = new StringReader(input);
			StringWriter writer = new StringWriter();

			xdiReader.read(graph, reader);
			xdiResultWriter.write(graph, writer);

			rawoutput = writer.getBuffer().toString();
			output = StringEscapeUtils.escapeHtml(writer.getBuffer().toString());
		} catch (Exception ex) {

			log.error(ex.getMessage(), ex);
			error = ex.getMessage();
			if (error == null) error = ex.getClass().getName();
		}

		stats = "";
		stats += Long.toString(graph.getRootContextNode().getAllContextNodeCount()) + " context nodes. ";
		stats += Long.toString(graph.getRootContextNode().getAllRelationCount()) + " relations. ";
		stats += Long.toString(graph.getRootContextNode().getAllLiteralCount()) + " literals. ";
		stats += Long.toString(graph.getRootContextNode().getAllStatementCount()) + " statements. ";
		if (xdiReader != null) stats += "Input format: " + xdiReader.getFormat() + ((xdiReader instanceof AutoReader && ((AutoReader) xdiReader).getLastSuccessfulReader() != null) ? " (" + ((AutoReader) xdiReader).getLastSuccessfulReader().getFormat() + ")": "")+ ". ";

		graph.close();

		// display results

		if ("Html!".equals(submit)) {

			response.setContentType("text/html");
			response.getWriter().append(rawoutput);
			return;
		}

		request.setAttribute("sampleInputs", Integer.valueOf(sampleInputs.size()));
		request.setAttribute("resultFormat", resultFormat);
		request.setAttribute("writeImplied", writeImplied);
		request.setAttribute("writeOrdered", writeOrdered);
		request.setAttribute("writeInner", writeInner);
		request.setAttribute("writePretty", writePretty);
		request.setAttribute("from", from);
		request.setAttribute("input", input);
		request.setAttribute("output", output);
		request.setAttribute("stats", stats);
		request.setAttribute("error", error);

		request.getRequestDispatcher("/XDIConverter.jsp").forward(request, response);
	}   	  	    
}
