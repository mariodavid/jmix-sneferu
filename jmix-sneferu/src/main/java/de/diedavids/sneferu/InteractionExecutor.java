package de.diedavids.sneferu;

import de.diedavids.sneferu.screen.ScreenTestAPI;

/**
 * executes a given Interaction in a fluent API fashion.
 *
 * The type THIS refers to the implementation type itself.
 * It is used to return the correct type in the fluent API.
 *
 * @param <THIS> the type of the class implementing this interface
 */
public interface InteractionExecutor<THIS> {


  /**
   * starts an interaction
   *
   * @param interaction the interaction to execute
   *
   * @return THIS the interactions executor for further method chaining
   */
  THIS interact(Interaction interaction);

  /**
   * continues the interactions {@link InteractionExecutor#interact(Interaction)}
   *
   * @param interaction the interaction to execute
   * @return THIS the interactions executor for further method chaining
   */
  THIS andThen(Interaction interaction);


  /**
   * interacts with the screen and returns the interaction outcome
   * @param interaction the interaction with outcome to verify
   * @param <O> the type of verification
   * @param <T> the type of the ScreenTestAPI
   *
   * @return the outcome of the verification of type O
   */
  <O, T extends ScreenTestAPI> O verify(InteractionWithOutcome<O, T> interaction);

  /**
   * alias for {@link InteractionExecutor#verify(InteractionWithOutcome)}
   *
   * @param interaction the interaction with outcome to verify
   * @param <O> the type of verification
   * @param <T> the type of the ScreenTestAPI
   *
   * @return the outcome of the verification of type O
   */
  <O, T extends ScreenTestAPI> O get(InteractionWithOutcome<O, T> interaction);

  /**
   * alias for {@link InteractionExecutor#verify(InteractionWithOutcome)}
   *
   * @param interaction the interaction with outcome to verify
   * @param <O> the type of verification
   * @param <T> the type of the ScreenTestAPI
   *
   * @return the outcome of the verification of type O
   */
  <O, T extends ScreenTestAPI> O interactAndGet(InteractionWithOutcome<O, T> interaction);

  /**
   * alias for {@link InteractionExecutor#verify(InteractionWithOutcome)}
   *
   * @param interaction the interaction with outcome to verify
   * @param <O> the type of verification
   * @param <T> the type of the ScreenTestAPI
   *
   * @return the outcome of the verification of type O
   */
  <O, T extends ScreenTestAPI> O andThenGet(InteractionWithOutcome<O, T> interaction);

  /**
   * alias for {@link InteractionExecutor#verify(InteractionWithOutcome)}
   *
   * @param interaction the interaction with outcome to verify
   * @param <O> the type of verification
   * @param <T> the type of the ScreenTestAPI
   *
   * @return the outcome of the verification of type O
   */
  <O, T extends ScreenTestAPI> O andThenVerify(InteractionWithOutcome<O, T> interaction);

}
