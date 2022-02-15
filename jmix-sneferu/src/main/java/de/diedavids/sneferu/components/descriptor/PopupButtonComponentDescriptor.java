package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.PopupButton;

public class PopupButtonComponentDescriptor
        extends GenericComponentDescriptor<PopupButton, GenericComponentTestAPI<PopupButton>> {

    public PopupButtonComponentDescriptor(String componentId) {
        super(PopupButton.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<PopupButton> createTestAPI(PopupButton component) {
        return new GenericComponentTestAPI<>(component);
    }
}