package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.Interaction;
import de.diedavids.sneferu.components.ComponentDescriptor;
import de.diedavids.sneferu.components.testapi.ComponentTestAPI;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.ListComponent;

import java.util.Collection;
import java.util.Collections;

public class SelectInListInteraction<E> implements Interaction {

    private final ComponentDescriptor<? extends Component, ? extends ComponentTestAPI> componentDescriptor;
    private final Collection<E> values;

    public <C extends Component, F extends ComponentTestAPI<C>> SelectInListInteraction(
        ComponentDescriptor<C, F> componentDescriptor,
        E value
    ) {
        this.componentDescriptor = componentDescriptor;
        this.values = Collections.singleton(value);
    }

    public <C extends Component, F extends ComponentTestAPI<C>> SelectInListInteraction(
        ComponentDescriptor<C, F> componentDescriptor,
        Collection<E> values
    ) {
        this.componentDescriptor = componentDescriptor;
        this.values = values;
    }


    @Override
    public void execute(ScreenTestAPI screenTestAPI) {
        ListComponent listComponent = (ListComponent) screenTestAPI.component(componentDescriptor).rawComponent();
        listComponent.setSelected(values);
    }


}

