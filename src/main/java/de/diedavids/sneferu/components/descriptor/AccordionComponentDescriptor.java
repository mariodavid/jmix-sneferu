package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.Accordion;

public class AccordionComponentDescriptor
        extends GenericComponentDescriptor<Accordion, GenericComponentTestAPI<Accordion>> {

    public AccordionComponentDescriptor(String componentId) {
        super(Accordion.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<Accordion> createTestAPI(Accordion component) {
        return new GenericComponentTestAPI<>(component);
    }
}
