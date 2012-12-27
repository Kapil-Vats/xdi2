package xdi2.core.features.linkcontracts.policystatement;

import xdi2.core.Relation;
import xdi2.core.constants.XDIConstants;
import xdi2.core.exceptions.Xdi2RuntimeException;
import xdi2.core.features.linkcontracts.condition.Condition;
import xdi2.core.util.GraphUtil;
import xdi2.core.util.locator.ContextNodeLocator;

/**
 * An XDI $true policy statement, represented as a relation.
 * 
 * @author markus
 */
public class OperationPolicyStatement extends PolicyStatement {

	private static final long serialVersionUID = 4296419491079293469L;

	protected OperationPolicyStatement(Relation relation) {

		super(relation);
	}

	/*
	 * Static methods
	 */

	/**
	 * Checks if a relation is a valid XDI $true policy statement.
	 * @param relation The relation to check.
	 * @return True if the relation is a valid XDI $true policy statement.
	 */
	public static boolean isValid(Relation relation) {

		if (! XDIConstants.XRI_S_TRUE.equals(relation.getArcXri())) return false;

		return true;
	}

	/**
	 * Factory method that creates an XDI $true policy statement bound to a given relation.
	 * @param relation The relation that is an XDI $true policy statement.
	 * @return The XDI $true policy statement.
	 */
	public static OperationPolicyStatement fromRelation(Relation relation) {

		if (! isValid(relation)) return null;

		return new OperationPolicyStatement(relation);
	}

	public static OperationPolicyStatement fromCondition(Condition condition) {

		return fromRelation(GraphUtil.relationFromComponents(XDIConstants.XRI_S_ROOT, XDIConstants.XRI_S_TRUE, condition.getStatement().toXriSegment()));
	}

	/*
	 * Instance methods
	 */

	@Override
	public boolean evaluateInternal(ContextNodeLocator contextNodeLocator) {

		Condition condition = this.getCondition();
		if (condition == null) throw new Xdi2RuntimeException("Missing or invalid condition in $true policy statement.");

		return true == condition.evaluate(contextNodeLocator);
	}
}