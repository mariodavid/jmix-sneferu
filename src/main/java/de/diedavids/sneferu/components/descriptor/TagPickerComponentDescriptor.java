package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.TagPicker;

public class TagPickerComponentDescriptor
        extends GenericComponentDescriptor<TagPicker, GenericComponentTestAPI<TagPicker>> {

    public TagPickerComponentDescriptor(String componentId) {
        super(TagPicker.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<TagPicker> createTestAPI(TagPicker component) {
        return new GenericComponentTestAPI<>(component);
    }
}
