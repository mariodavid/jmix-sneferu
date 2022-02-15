package de.diedavids.sneferu.components;

import de.diedavids.sneferu.components.testapi.ComponentTestAPI;
import io.jmix.ui.component.Component;

public interface ComponentDescriptor<C extends Component, F extends ComponentTestAPI<C>> {

    String getId();

    F createTestAPI(C component);
}
