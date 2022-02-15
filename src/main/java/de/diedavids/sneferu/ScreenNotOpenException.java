package de.diedavids.sneferu;


/**
 * Exception that is raised in case a screen is requested from the integration test, but is
 * currently not opened in the application.
 */
public class ScreenNotOpenException extends RuntimeException {
}
