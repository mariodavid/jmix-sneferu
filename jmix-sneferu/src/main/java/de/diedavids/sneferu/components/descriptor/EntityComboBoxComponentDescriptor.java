package de.diedavids.sneferu.components.descriptor;

import de.diedavids.sneferu.components.testapi.EntityComboBoxTestAPI;
import io.jmix.ui.component.EntityComboBox;

public class EntityComboBoxComponentDescriptor
        extends GenericComponentDescriptor<EntityComboBox, EntityComboBoxTestAPI> {

    public EntityComboBoxComponentDescriptor(String componentId) {
        super(EntityComboBox.class, componentId);
    }

    @Override
    public EntityComboBoxTestAPI createTestAPI(EntityComboBox component) {
        return new EntityComboBoxTestAPI(component);
    }
}
