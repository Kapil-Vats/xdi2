package xdi2.messaging.target.impl.graph;

import xdi2.core.Graph;
import xdi2.core.Relation;
import xdi2.core.Statement;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.xri3.impl.XRI3Segment;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.exceptions.Xdi2MessagingException;
import xdi2.messaging.target.ExecutionContext;
import xdi2.messaging.target.impl.AbstractMessagingTarget;
import xdi2.messaging.target.impl.AddressHandler;
import xdi2.messaging.target.impl.StatementHandler;

/**
 * An XDI messaging target backed by some implementation of the Graph interface.
 * 
 * @author markus
 */
public class GraphMessagingTarget extends AbstractMessagingTarget {

	private Graph graph;
	private GraphAddressHandler graphAddressHandler;
	private GraphStatementHandler graphStatementHandler;

	public GraphMessagingTarget() {

		super();

		this.graph = null;
		this.graphAddressHandler = null;
		this.graphStatementHandler = null;
	}

	@Override
	public void init() throws Exception {

		super.init();
	}

	@Override
	public void shutdown() throws Exception {

		super.shutdown();

		this.graph.close();
	}

	@Override
	public XRI3Segment getOwner() {

		Relation relation = this.getGraph().getRootContextNode().getRelation(XDIDictionaryConstants.XRI_S_IS_IS);
		if (relation == null) return null;

		return relation.getTargetContextNodeXri();
	}

	@Override
	public void before(MessageEnvelope messageEnvelope, ExecutionContext executionContext) throws Xdi2MessagingException {

		super.before(messageEnvelope, executionContext);

		this.graph.beginTransaction();
	}

	@Override
	public void after(MessageEnvelope messageEnvelope, ExecutionContext executionContext) throws Xdi2MessagingException {

		super.after(messageEnvelope, executionContext);

		this.graph.commitTransaction();
	}

	@Override
	public void exception(MessageEnvelope messageEnvelope, ExecutionContext executionContext, Exception ex) throws Xdi2MessagingException {

		super.exception(messageEnvelope, executionContext, ex);

		this.graph.rollbackTransaction();
	}

	@Override
	public AddressHandler getAddressHandler(XRI3Segment address) throws Xdi2MessagingException {

		return this.graphAddressHandler;
	}

	@Override
	public StatementHandler getStatementHandler(Statement statement) throws Xdi2MessagingException {

		return this.graphStatementHandler;
	}

	public Graph getGraph() {

		return this.graph;
	}

	public void setGraph(Graph graph) {

		this.graph = graph;
		this.graphAddressHandler = new GraphAddressHandler(graph);
		this.graphStatementHandler = new GraphStatementHandler(graph);
	}
}
