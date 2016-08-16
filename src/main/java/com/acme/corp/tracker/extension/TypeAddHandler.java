/**
 * 
 */
package com.acme.corp.tracker.extension;

import java.util.List;
import java.util.Locale;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.controller.descriptions.DescriptionProvider;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceName;

import com.acme.corp.tracker.service.TrackerService;

/**
 * @author kbaumer
 *
 */
public class TypeAddHandler extends AbstractAddStepHandler implements DescriptionProvider {
	
	public static final TypeAddHandler INSTANCE = new TypeAddHandler();

	 private TypeAddHandler() {
	 }
	 
	/* (non-Javadoc)
	 * @see org.jboss.as.controller.descriptions.DescriptionProvider#getModelDescription(java.util.Locale)
	 */
	@Override
	public ModelNode getModelDescription(Locale arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jboss.as.controller.AbstractAddStepHandler#populateModel(org.jboss.dmr.ModelNode, org.jboss.dmr.ModelNode)
	 */
	@Override
	protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
		TypeDefinition.TICK.validateAndSet(operation, model);

	}

	@Override
	protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model,
			ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers)
					throws OperationFailedException {
		String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
		long tick = TypeDefinition.TICK.resolveModelAttribute(context,model).asLong();
		TrackerService service = new TrackerService(suffix, tick);
		ServiceName name = TrackerService.createServiceName(suffix);
		ServiceController<TrackerService> controller = context.getServiceTarget()
				.addService(name, service)
				.addListener(verificationHandler)
				.setInitialMode(Mode.ACTIVE)
				.install();
		newControllers.add(controller);
	}
	
}
