package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.TabSheet;

public class TabsheetComponentDescriptor
        extends GenericComponentDescriptor<TabSheet, GenericComponentTestAPI<TabSheet>> {

    public TabsheetComponentDescriptor(String componentId) {
        super(TabSheet.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<TabSheet> createTestAPI(TabSheet component) {
        return new GenericComponentTestAPI<>(component);
    }
}
