/**
 * 
 */
package com.acme.corp.tracker;

import org.jboss.as.controller.AbstractWriteAttributeHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

import com.acme.corp.tracker.extension.TrackerExtension;
import com.acme.corp.tracker.extension.TypeDefinition;
import com.acme.corp.tracker.service.TrackerService;

/**
 * @author kbaumer
 *
 */
public class TrackerTickHandler extends AbstractWriteAttributeHandler<Void> {

	public static final TrackerTickHandler INSTANCE = new TrackerTickHandler();
	 
	private TrackerTickHandler()
	{ super(TypeDefinition.TICK); }
	
	@Override
	protected boolean applyUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, 
			ModelNode resolvedValue, ModelNode currentValue, HandbackHolder<Void> handbackHolder)
			throws OperationFailedException {
		if (attributeName.equals(TrackerExtension.TICK)) {
			final String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
			TrackerService service = (TrackerService) context.getServiceRegistry(true).getRequiredService(TrackerService.createServiceName(suffix)).getValue();
			service.setTick(resolvedValue.asLong()); 
			context.completeStep(); 
		}
		return false;
	}

	@Override
	protected void revertUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, 
			ModelNode valueToRestore, ModelNode valueToRevert, Void handback) {
		// no-op
	}
}
