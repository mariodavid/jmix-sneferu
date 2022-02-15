package de.diedavids.sneferu.components.testapi;

import io.jmix.ui.component.SuggestionField;

public class SuggestionFieldTestAPI extends GenericComponentTestAPI<SuggestionField> {
    public SuggestionFieldTestAPI(SuggestionField component) {
        super(component);
    }

    public SuggestionFieldTestAPI search(Object searchTerm) {


        rawComponent().setValue(searchTerm);

        return this;
    }
}
