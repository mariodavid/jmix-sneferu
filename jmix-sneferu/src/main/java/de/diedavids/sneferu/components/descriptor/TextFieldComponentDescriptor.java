package de.diedavids.sneferu.components.descriptor;

import de.diedavids.sneferu.components.testapi.TextFieldTestAPI;
import io.jmix.ui.component.TextInputField;

public class TextFieldComponentDescriptor
        extends GenericComponentDescriptor<TextInputField, TextFieldTestAPI> {

    public TextFieldComponentDescriptor(String componentId) {
        super(TextInputField.class, componentId);
    }

    @Override
    public TextFieldTestAPI createTestAPI(TextInputField component) {
        return new TextFieldTestAPI(component);
    }
}
