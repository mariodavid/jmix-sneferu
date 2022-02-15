package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.ColorPicker;

public class ColorPickerComponentDescriptor
        extends GenericComponentDescriptor<ColorPicker, GenericComponentTestAPI<ColorPicker>> {

    public ColorPickerComponentDescriptor(String componentId) {
        super(ColorPicker.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<ColorPicker> createTestAPI(ColorPicker component) {
        return new GenericComponentTestAPI<>(component);
    }
}
