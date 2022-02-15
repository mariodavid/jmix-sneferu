package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.Image;

public class ImageComponentDescriptor
        extends GenericComponentDescriptor<Image, GenericComponentTestAPI<Image>> {

    public ImageComponentDescriptor(String componentId) {
        super(Image.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<Image> createTestAPI(Image component) {
        return new GenericComponentTestAPI<>(component);
    }
}
