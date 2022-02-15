package de.diedavids.sneferu.components.descriptor;

import de.diedavids.sneferu.components.testapi.ComboBoxTestAPI;
import io.jmix.ui.component.ComboBox;

public class ComboBoxComponentDescriptor
        extends GenericComponentDescriptor<ComboBox, ComboBoxTestAPI> {

    public ComboBoxComponentDescriptor(String componentId) {
        super(ComboBox.class, componentId);
    }

    @Override
    public ComboBoxTestAPI createTestAPI(ComboBox component) {
        return new ComboBoxTestAPI(component);
    }
}
