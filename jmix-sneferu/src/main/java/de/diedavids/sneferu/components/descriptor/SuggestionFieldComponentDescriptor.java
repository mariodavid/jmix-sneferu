package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.SuggestionFieldTestAPI;
import io.jmix.ui.component.SuggestionField;

public class SuggestionFieldComponentDescriptor
        extends GenericComponentDescriptor<SuggestionField, SuggestionFieldTestAPI> {

    public SuggestionFieldComponentDescriptor(String componentId) {
        super(SuggestionField.class, componentId);
    }

    @Override
    public SuggestionFieldTestAPI createTestAPI(SuggestionField component) {
        return new SuggestionFieldTestAPI(component);
    }
}
