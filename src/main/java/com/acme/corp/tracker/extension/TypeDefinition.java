/**
 * 
 */
package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.acme.corp.tracker.TrackerTickHandler;

/**
 * @author kbaumer
 *
 */
public class TypeDefinition extends SimpleResourceDefinition {

	public static final TypeDefinition INSTANCE = new TypeDefinition();

	// we define attribute named tick
	public static final SimpleAttributeDefinition TICK = new SimpleAttributeDefinitionBuilder(TrackerExtension.TICK,
			ModelType.LONG).setAllowExpression(true).setXmlName(TrackerExtension.TICK)
					.setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES).setDefaultValue(new ModelNode(1000))
					.setAllowNull(false).build();

	private TypeDefinition() {
		super(TrackerExtension.TYPE_PATH, TrackerExtension.getResourceDescriptionResolver(TrackerExtension.TYPE), 
				TypeAddHandler.INSTANCE, TypeRemoveHandler.INSTANCE);
	}

	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		resourceRegistration.registerReadWriteAttribute(TICK, null, TrackerTickHandler.INSTANCE);
	}
}
