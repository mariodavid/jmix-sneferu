package de.diedavids.sneferu.components.testapi;

import io.jmix.ui.component.Component;

public abstract class ComponentTestAPI<C extends Component> {

    public abstract C rawComponent();
}
