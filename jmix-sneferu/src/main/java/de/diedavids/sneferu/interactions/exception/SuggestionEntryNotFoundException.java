package de.diedavids.sneferu.interactions.exception;


/**
 * Exception that is raised in case a suggestion field result list
 * does not contain the entry to select
 */
public class SuggestionEntryNotFoundException extends RuntimeException {
    public SuggestionEntryNotFoundException(String message, Throwable e) {
        super(message, e);
    }

    public SuggestionEntryNotFoundException(String message) {
        super(message);
    }
}
