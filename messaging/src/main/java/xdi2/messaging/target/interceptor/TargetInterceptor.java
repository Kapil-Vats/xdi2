package xdi2.messaging.target.interceptor;

import xdi2.core.Statement;
import xdi2.core.xri3.impl.XRI3Segment;
import xdi2.messaging.Operation;
import xdi2.messaging.exceptions.Xdi2MessagingException;
import xdi2.messaging.target.ExecutionContext;

/**
 * Interceptor that is executed when an operation is executed on an XDI statement or an XDI address.
 * 
 * @author markus
 */
public interface TargetInterceptor extends Interceptor {

	/**
	 * Replaces or skips the target statement before it is executed.
	 * @param operation The operation to process.
	 * @param targetStatement The target statement.
	 * @param executionContext The current execution context.
	 * @return The new target statement, or the original target statement, or null to skip.
	 */
	public Statement targetStatement(Operation operation, Statement targetStatement, ExecutionContext executionContext) throws Xdi2MessagingException;

	/**
	 * Replaces or skips the target address before it is executed.
	 * @param operation The operation to process.
	 * @param targetAddress The target address.
	 * @param executionContext The current execution context.
	 * @return The new target address, or the original target address, or null to skip.
	 */
	public XRI3Segment targetAddress(Operation operation, XRI3Segment targetAddress, ExecutionContext executionContext) throws Xdi2MessagingException;
}