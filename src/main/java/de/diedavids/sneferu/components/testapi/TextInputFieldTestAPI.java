package de.diedavids.sneferu.components.testapi;

import io.jmix.ui.component.TextInputField;

public class TextInputFieldTestAPI extends GenericComponentTestAPI<TextInputField> {

    public TextInputFieldTestAPI(TextInputField component) {
        super(component);
    }

    public TextInputFieldTestAPI enter(Object o) {
        return this;
    }
}
