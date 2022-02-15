package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.CheckBox;

public class CheckBoxComponentDescriptor
        extends GenericComponentDescriptor<CheckBox, GenericComponentTestAPI<CheckBox>> {

    public CheckBoxComponentDescriptor(String componentId) {
        super(CheckBox.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<CheckBox> createTestAPI(CheckBox component) {
        return new GenericComponentTestAPI<>(component);
    }
}
