package de.diedavids.sneferu.components.descriptor;

import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.mainwindow.SideMenu;

public class SideMenuComponentDescriptor extends GenericComponentDescriptor<SideMenu, GenericComponentTestAPI<SideMenu>> {

    public SideMenuComponentDescriptor(String componentId) {
        super(SideMenu.class, componentId);
    }

    @Override
    public GenericComponentTestAPI createTestAPI(SideMenu component) {
        return new GenericComponentTestAPI(component);
    }
}