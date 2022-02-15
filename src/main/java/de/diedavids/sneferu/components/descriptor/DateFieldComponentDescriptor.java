package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.DateFieldTestAPI;
import io.jmix.ui.component.DateField;

public class DateFieldComponentDescriptor
        extends GenericComponentDescriptor<DateField, DateFieldTestAPI> {

    public DateFieldComponentDescriptor(String componentId) {
        super(DateField.class, componentId);
    }

    @Override
    public DateFieldTestAPI createTestAPI(DateField component) {
        return new DateFieldTestAPI(component);
    }
}
