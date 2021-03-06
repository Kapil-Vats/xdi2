package xdi2.messaging.tests.target.contributor;

import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.GetOperation;
import xdi2.messaging.MessageResult;
import xdi2.messaging.context.ExecutionContext;
import xdi2.messaging.exceptions.Xdi2MessagingException;
import xdi2.messaging.target.contributor.AbstractContributor;
import xdi2.messaging.target.contributor.ContributorMount;
import xdi2.messaging.target.contributor.ContributorResult;

@ContributorMount(contributorXris={"<+email>"})
public class TestContributor2 extends AbstractContributor {

	private String value = "val";

	@Override
	public ContributorResult executeGetOnAddress(
			XDI3Segment[] contributorXris,
			XDI3Segment contributorsXri,
			XDI3Segment relativeTargetAddress,
			GetOperation operation,
			MessageResult messageResult,
			ExecutionContext executionContext) throws Xdi2MessagingException {

		messageResult.getGraph().setStatement(XDI3Statement.fromLiteralComponents(
				XDI3Segment.create("" + contributorsXri + "&"),
				this.value));

		return ContributorResult.DEFAULT;
	}
}
