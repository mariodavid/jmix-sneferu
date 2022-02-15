package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.Interaction;
import de.diedavids.sneferu.components.ComponentDescriptor;
import de.diedavids.sneferu.components.testapi.ComponentTestAPI;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;

public class ClickInteraction implements Interaction {

    private final ComponentDescriptor<? extends Component, ? extends ComponentTestAPI> componentDescriptor;
    private ScreenTestAPI screenTestAPI;

    public <C extends Component, F extends ComponentTestAPI<C>> ClickInteraction(
        ComponentDescriptor<C, F> componentDescriptor) {
        this.componentDescriptor = componentDescriptor;
    }

    @Override
    public void execute(ScreenTestAPI screenTestAPI) {
        this.screenTestAPI = screenTestAPI;
        doClick(componentDescriptor.getId());
    }

    protected void doClick(String componentId) {
        Button button = (Button) screenTestAPI.screen().getWindow().getComponentNN(componentId);
        clickButton(button);
    }

    protected void clickButton(Button button) {
        button.unwrap(com.vaadin.ui.Button.class).click();
    }

}
