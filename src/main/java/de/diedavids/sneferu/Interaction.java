package de.diedavids.sneferu;

import de.diedavids.sneferu.screen.ScreenTestAPI;

/**
 * An Interaction represents an action that is executed against
 * a particular screen instance
 *
 * @param <T> the type of the screen Test API that this interactions should be executed against
 */
public interface Interaction<T extends ScreenTestAPI> {


  /**
   * executes the interactions against the given screen Test API
   *
   * @param screenTestAPI the screen Test API instance the interactions should be executed against
   */
  void execute(T screenTestAPI);
}

