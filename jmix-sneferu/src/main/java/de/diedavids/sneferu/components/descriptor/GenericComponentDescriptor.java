package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.ComponentDescriptor;
import de.diedavids.sneferu.components.testapi.ComponentTestAPI;
import io.jmix.ui.component.Component;

public abstract class GenericComponentDescriptor<C extends Component, F extends ComponentTestAPI<C>>
        implements ComponentDescriptor<C, F> {

    private final String componentId;

    public GenericComponentDescriptor(Class<C> componentType, String componentId)  {
        this.componentId = componentId;
    }

    @Override
    public String getId() {
        return componentId;
    }

}
