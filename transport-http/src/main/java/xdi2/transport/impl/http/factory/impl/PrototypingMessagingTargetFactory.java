package xdi2.transport.impl.http.factory.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xdi2.core.ContextNode;
import xdi2.core.features.nodetypes.XdiPeerRoot;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.exceptions.Xdi2MessagingException;
import xdi2.messaging.target.MessagingTarget;
import xdi2.messaging.target.Prototype;
import xdi2.messaging.target.Prototype.PrototypingContext;
import xdi2.messaging.target.contributor.ContributorMap;
import xdi2.messaging.target.impl.AbstractMessagingTarget;
import xdi2.messaging.target.interceptor.InterceptorList;
import xdi2.transport.exceptions.Xdi2TransportException;
import xdi2.transport.impl.http.factory.AbstractMessagingTargetFactory;
import xdi2.transport.impl.http.registry.HttpMessagingTargetRegistry;

public abstract class PrototypingMessagingTargetFactory extends AbstractMessagingTargetFactory {

	private static final Logger log = LoggerFactory.getLogger(PrototypingMessagingTargetFactory.class);

	private MessagingTarget prototypeMessagingTarget;

	@SuppressWarnings("unchecked")
	public MessagingTarget mountMessagingTarget(HttpMessagingTargetRegistry httpMessagingTargetRegistry, String messagingTargetPath, XDI3Segment ownerXri, XdiPeerRoot ownerPeerRoot, ContextNode ownerContextNode) throws Xdi2MessagingException, Xdi2TransportException {

		if (log.isDebugEnabled()) log.debug("messagingTargetPath=" + messagingTargetPath + ", ownerXri=" + ownerXri + ", ownerPeerRoot=" + ownerPeerRoot + ", ownerContextNode=" + ownerContextNode);

		// create new messaging target

		if (! (this.getPrototypeMessagingTarget() instanceof Prototype<?>)) {

			throw new Xdi2MessagingException("Cannot use messaging target " + this.getPrototypeMessagingTarget().getClass().getSimpleName() + " as prototype.", null, null);
		}

		PrototypingContext prototypingContext = new PrototypingContext(ownerXri, ownerPeerRoot, ownerContextNode);

		Prototype<? extends MessagingTarget> messagingTargetPrototype; 
		MessagingTarget prototypedMessagingTarget;

		try {

			messagingTargetPrototype = (Prototype<? extends MessagingTarget>) this.getPrototypeMessagingTarget();
			prototypedMessagingTarget = prototypingContext.instanceFor(messagingTargetPrototype);
		} catch (Xdi2MessagingException ex) {

			throw new Xdi2MessagingException("Cannot instantiate messaging target for prototype " + this.getPrototypeMessagingTarget().getClass().getSimpleName() + ": " + ex.getMessage(), ex, null);
		}

		// set the interceptor list

		if (messagingTargetPrototype instanceof AbstractMessagingTarget && prototypedMessagingTarget instanceof AbstractMessagingTarget) {

			InterceptorList<MessagingTarget> interceptorListPrototype = ((AbstractMessagingTarget) messagingTargetPrototype).getInterceptors();
			InterceptorList<MessagingTarget> prototypedInterceptorList = prototypingContext.instanceFor(interceptorListPrototype);

			((AbstractMessagingTarget) prototypedMessagingTarget).setInterceptors(prototypedInterceptorList);
		}

		// set the contributor map

		if (messagingTargetPrototype instanceof AbstractMessagingTarget && prototypedMessagingTarget instanceof AbstractMessagingTarget) {

			ContributorMap contributorMapPrototype = ((AbstractMessagingTarget) messagingTargetPrototype).getContributors();
			ContributorMap prototypedContributorMap = prototypingContext.instanceFor(contributorMapPrototype);

			((AbstractMessagingTarget) prototypedMessagingTarget).setContributors(prototypedContributorMap);
		}

		// mount the new messaging target

		httpMessagingTargetRegistry.mountMessagingTarget(messagingTargetPath, prototypedMessagingTarget);

		// done

		return prototypedMessagingTarget;
	}

	public MessagingTarget getPrototypeMessagingTarget() {

		return this.prototypeMessagingTarget;
	}

	public void setPrototypeMessagingTarget(MessagingTarget prototypeMessagingTarget) {

		this.prototypeMessagingTarget = prototypeMessagingTarget;
	}
}
