package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.GroupTable;

public class GroupTableComponentDescriptor
        extends GenericComponentDescriptor<GroupTable, GenericComponentTestAPI<GroupTable>> {

    public GroupTableComponentDescriptor(String componentId) {
        super(GroupTable.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<GroupTable> createTestAPI(GroupTable component) {
        return new GenericComponentTestAPI<>(component);
    }
}
