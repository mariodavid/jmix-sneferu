package de.diedavids.sneferu.components.testapi;

import io.jmix.ui.component.DateField;

public class DateFieldTestAPI extends GenericComponentTestAPI<DateField> {

  public DateFieldTestAPI(DateField component) {
    super(component);
  }

  public DateFieldTestAPI enter(Object o) {
    rawComponent()
        .setValue(o);
    return this;
  }
}
