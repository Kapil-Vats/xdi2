package xdi2.core.features.dictionary;

import java.util.Iterator;

import xdi2.core.ContextNode;
import xdi2.core.constants.XDIConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.util.iterators.MappingContextNodeXriIterator;
import xdi2.core.util.iterators.MappingRelationTargetContextNodeIterator;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3SubSegment;

public class Dictionary {

	private Dictionary() { }

	/*
	 * Methods for dictionary XRIs
	 */

	public static XDI3SubSegment instanceXriToDictionaryXri(XDI3SubSegment instanceXri) {

		return XDI3SubSegment.create("" + XDIConstants.CS_PLUS + "(" + instanceXri + ")");
	}

	public static XDI3SubSegment dictionaryXriToInstanceXri(XDI3SubSegment dictionaryXri) {

		if (! XDIConstants.CS_PLUS.equals(dictionaryXri.getCs())) return null;
		if (! dictionaryXri.hasXRef()) return null;

		return XDI3SubSegment.create(dictionaryXri.getXRef().getValue());
	}

	public static XDI3SubSegment nativeIdentifierToInstanceXri(String nativeIdentifier) {

		return XDI3SubSegment.create("" + XDIConstants.CS_PLUS + "(" + nativeIdentifier + ")");
	}

	public static String instanceXriToNativeIdentifier(XDI3SubSegment instanceXri) {

		if (! instanceXri.hasXRef()) return null;

		return instanceXri.getXRef().getValue();
	}

	/*
	 * Methods for types of context nodes.
	 */

	public static Iterator<XDI3Segment> getContextNodeTypes(ContextNode contextNode) {

		return new MappingContextNodeXriIterator(new MappingRelationTargetContextNodeIterator(contextNode.getRelations(XDIDictionaryConstants.XRI_S_IS_TYPE)));
	}

	public static XDI3Segment getContextNodeType(ContextNode contextNode) {

		return contextNode.getRelation(XDIDictionaryConstants.XRI_S_IS_TYPE).getTargetContextNodeXri();
	}

	public static boolean isContextNodeType(ContextNode contextNode, XDI3Segment type) {

		return contextNode.containsRelation(XDIDictionaryConstants.XRI_S_IS_TYPE, type);
	}

	public static void setContextNodeType(ContextNode contextNode, XDI3Segment type) {

		contextNode.setRelation(XDIDictionaryConstants.XRI_S_IS_TYPE, type);
	}

	public static void delContextNodeType(ContextNode contextNode, XDI3Segment type) {

		contextNode.delRelation(XDIDictionaryConstants.XRI_S_IS_TYPE, type);
	}

	public static void delContextNodeTypes(ContextNode contextNode) {

		contextNode.delRelations(XDIDictionaryConstants.XRI_S_IS_TYPE);
	}

	public static void replaceContextNodeType(ContextNode contextNode, XDI3Segment type) {

		delContextNodeTypes(contextNode);
		setContextNodeType(contextNode, type);
	}
}
