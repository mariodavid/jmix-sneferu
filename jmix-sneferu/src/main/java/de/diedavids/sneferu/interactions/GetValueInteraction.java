package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.InteractionWithOutcome;
import de.diedavids.sneferu.components.ComponentDescriptor;
import de.diedavids.sneferu.components.testapi.ComponentTestAPI;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.HasValue;

public class GetValueInteraction<T> implements InteractionWithOutcome<T, ScreenTestAPI> {

  private final ComponentDescriptor<? extends Component, ? extends ComponentTestAPI> componentDescriptor;

  public <C extends Component, F extends ComponentTestAPI<C>> GetValueInteraction(
      ComponentDescriptor<C, F> componentDescriptor
  ) {
    this.componentDescriptor = componentDescriptor;
  }

  @Override
  public T execute(ScreenTestAPI screenTestAPI) {
    HasValue hasValue = (HasValue) screenTestAPI.component(componentDescriptor).rawComponent();
    return (T) hasValue.getValue();
  }
}