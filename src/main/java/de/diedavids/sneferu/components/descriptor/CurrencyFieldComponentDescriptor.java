package de.diedavids.sneferu.components.descriptor;

import de.diedavids.sneferu.components.testapi.CurrencyFieldTestAPI;
import io.jmix.ui.component.CurrencyField;

public class CurrencyFieldComponentDescriptor
        extends GenericComponentDescriptor<CurrencyField, CurrencyFieldTestAPI> {

    public CurrencyFieldComponentDescriptor(String componentId) {
        super(CurrencyField.class, componentId);
    }

    @Override
    public CurrencyFieldTestAPI createTestAPI(CurrencyField component) {
        return new CurrencyFieldTestAPI(component);
    }
}