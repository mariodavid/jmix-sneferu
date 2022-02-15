package de.diedavids.sneferu;


import de.diedavids.sneferu.screen.ScreenTestAPI;

/**
 * A Screen Object represents an abstraction upon the screen Test API {@link ScreenTestAPI}
 *
 * A Class implementing a Screen Object represents an API abstraction for a given
 * Screen. It allows you to define use-case specific methods to interact with the
 * Screen via its Testing API abstraction.
 *
 * The concept of the Screen Object is an implementation of the Page Object Pattern
 * More information can be found here: https://martinfowler.com/bliki/PageObject.html
 *
 * @param <T> the Screen Test API object that it interacts upon
 */
public interface ScreenObject<T extends ScreenTestAPI> {


    /**
     * returns the Screen Test API instance that this Page Object encapsulates
     *
     * @return the Screen Test API instance
     */
    T delegate();
}
