package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.TreeDataGrid;

public class TreeDataGridComponentDescriptor
            extends GenericComponentDescriptor<TreeDataGrid, GenericComponentTestAPI<TreeDataGrid>> {

    public TreeDataGridComponentDescriptor(String componentId) {
        super(TreeDataGrid.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<TreeDataGrid> createTestAPI(TreeDataGrid component) {
        return new GenericComponentTestAPI<>(component);
    }
}
