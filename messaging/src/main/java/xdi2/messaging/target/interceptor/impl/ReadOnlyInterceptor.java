package xdi2.messaging.target.interceptor.impl;

import xdi2.core.util.XDI3Util;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.MessageResult;
import xdi2.messaging.Operation;
import xdi2.messaging.context.ExecutionContext;
import xdi2.messaging.exceptions.Xdi2MessagingException;
import xdi2.messaging.target.MessagingTarget;
import xdi2.messaging.target.Prototype;
import xdi2.messaging.target.interceptor.AbstractInterceptor;
import xdi2.messaging.target.interceptor.TargetInterceptor;

/**
 * This interceptor throws an exception when $add, $mod or $del operations are attempted on given contexts of the target graph.
 * 
 * @author markus
 */
public class ReadOnlyInterceptor extends AbstractInterceptor<MessagingTarget> implements TargetInterceptor, Prototype<ReadOnlyInterceptor> {

	private XDI3Segment[] readOnlyAddresses;

	public ReadOnlyInterceptor() {

		this.readOnlyAddresses = new XDI3Segment[0];
	}

	/*
	 * Prototype
	 */

	@Override
	public ReadOnlyInterceptor instanceFor(PrototypingContext prototypingContext) {

		// done

		return this;
	}

	/*
	 * TargetInterceptor
	 */

	@Override
	public XDI3Statement targetStatement(XDI3Statement targetStatement, Operation operation, MessageResult messageResult, ExecutionContext executionContext) throws Xdi2MessagingException {

		XDI3Segment contextNodeXri;

		if (targetStatement.isContextNodeStatement()) 
			contextNodeXri = targetStatement.getTargetContextNodeXri();
		else
			contextNodeXri = targetStatement.getContextNodeXri();

		this.checkReadOnly(operation, contextNodeXri, executionContext);

		return targetStatement;
	}

	@Override
	public XDI3Segment targetAddress(XDI3Segment targetAddress, Operation operation, MessageResult messageResult, ExecutionContext executionContext) throws Xdi2MessagingException {

		XDI3Segment contextNodeXri = targetAddress;
		
		this.checkReadOnly(operation, contextNodeXri, executionContext);

		return targetAddress;
	}

	private void checkReadOnly(Operation operation, XDI3Segment contextNodeXri, ExecutionContext executionContext) throws Xdi2MessagingException {

		if (operation.isReadOnlyOperation()) return;

		for (XDI3Segment readOnlyAddress : this.readOnlyAddresses) {

			if (readOnlyAddress == null || XDI3Util.startsWith(contextNodeXri, readOnlyAddress) != null) {

				throw new Xdi2MessagingException("This address is read-only: " + contextNodeXri, null, executionContext);
			}
		}
	}

	public XDI3Segment[] getReadOnlyAddresses() {

		return this.readOnlyAddresses;
	}

	public void setReadOnlyAddresses(XDI3Segment[] readOnlyAddresses) {

		this.readOnlyAddresses = readOnlyAddresses;
	}

	public void setReadOnlyAddresses(String[] readOnlyAddresses) {

		this.readOnlyAddresses = new XDI3Segment[readOnlyAddresses.length];
		for (int i=0; i<this.readOnlyAddresses.length; i++) this.readOnlyAddresses[i] = XDI3Segment.create(readOnlyAddresses[i]);
	}
}
