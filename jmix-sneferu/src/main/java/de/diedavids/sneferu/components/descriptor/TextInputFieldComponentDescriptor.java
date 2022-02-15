package de.diedavids.sneferu.components.descriptor;

import de.diedavids.sneferu.components.testapi.TextInputFieldTestAPI;
import io.jmix.ui.component.TextInputField;

public class TextInputFieldComponentDescriptor
        extends GenericComponentDescriptor<TextInputField, TextInputFieldTestAPI> {

    public TextInputFieldComponentDescriptor(String componentId) {
        super(TextInputField.class, componentId);
    }

    @Override
    public TextInputFieldTestAPI createTestAPI(TextInputField component) {
        return new TextInputFieldTestAPI(component);
    }
}
