package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.Table;

public class TableComponentDescriptor
        extends GenericComponentDescriptor<Table, GenericComponentTestAPI<Table>> {

    public TableComponentDescriptor(String componentId) {
        super(Table.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<Table> createTestAPI(Table component) {
        return new GenericComponentTestAPI<>(component);
    }
}
