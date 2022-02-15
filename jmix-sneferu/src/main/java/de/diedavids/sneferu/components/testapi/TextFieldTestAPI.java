package de.diedavids.sneferu.components.testapi;

import io.jmix.ui.component.TextInputField;

public class TextFieldTestAPI extends GenericComponentTestAPI<TextInputField> {

    public TextFieldTestAPI(TextInputField component) {
        super(component);
    }

    public TextFieldTestAPI enter(Object o) {
        return this;
    }
}
