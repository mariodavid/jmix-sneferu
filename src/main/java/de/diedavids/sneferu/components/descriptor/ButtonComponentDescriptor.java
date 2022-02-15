package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.ButtonTestAPI;
import io.jmix.ui.component.Button;

public class ButtonComponentDescriptor
        extends GenericComponentDescriptor<Button, ButtonTestAPI> {

    public ButtonComponentDescriptor(String componentId) {
        super(Button.class, componentId);
    }

    @Override
    public ButtonTestAPI createTestAPI(Button component) {
        return new ButtonTestAPI(component);
    }
}
