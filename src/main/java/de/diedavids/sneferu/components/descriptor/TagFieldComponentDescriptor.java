package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.TagField;

public class TagFieldComponentDescriptor
        extends GenericComponentDescriptor<TagField, GenericComponentTestAPI<TagField>> {

    public TagFieldComponentDescriptor(String componentId) {
        super(TagField.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<TagField> createTestAPI(TagField component) {
        return new GenericComponentTestAPI<>(component);
    }
}
