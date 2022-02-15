package de.diedavids.sneferu.screen;


import io.jmix.ui.screen.StandardLookup;

import java.util.function.Supplier;

public class StandardLookupTestAPI<E, S extends StandardLookup<E>> extends ScreenTestAPI<S, StandardLookupTestAPI> {

    public StandardLookupTestAPI(Class<S> lookupScreenClass, S screen) {
        super(lookupScreenClass, screen);
    }

    public StandardLookupTestAPI(Class<S> lookupScreenClass, Supplier<S> screenSuppier) {
        super(lookupScreenClass, screenSuppier);
    }



}
