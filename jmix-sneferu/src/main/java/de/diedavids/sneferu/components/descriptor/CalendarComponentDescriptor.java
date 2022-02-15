package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.Calendar;

public class CalendarComponentDescriptor
            extends GenericComponentDescriptor<Calendar, GenericComponentTestAPI<Calendar>> {

    public CalendarComponentDescriptor(String componentId) {
        super(Calendar.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<Calendar> createTestAPI(Calendar component) {
        return new GenericComponentTestAPI<>(component);
    }
}
