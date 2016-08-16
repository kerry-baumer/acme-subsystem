/**
 * 
 */
package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

/**
 * @author kbaumer
 *
 */
public class SubsystemDefinition extends SimpleResourceDefinition {

	public static final SubsystemDefinition INSTANCE = new SubsystemDefinition();
	
    private SubsystemDefinition() {
        super(TrackerExtension.SUBSYSTEM_PATH,
        		TrackerExtension.getResourceDescriptionResolver(TrackerExtension.TYPE),
                //We always need to add an 'add' operation
                SubsystemAdd.INSTANCE,
                //Every resource that is added, normally needs a remove operation
                SubsystemRemove.INSTANCE);
    }
 
    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        //you can register aditional operations here
    }
 
    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        //you can register attributes here
    }
}
