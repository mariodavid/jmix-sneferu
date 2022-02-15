package de.diedavids.sneferu.screen;


import io.jmix.ui.screen.Screen;

import java.util.function.Supplier;

public class StandardScreenTestAPI<S extends Screen> extends ScreenTestAPI<S, StandardScreenTestAPI> {

    public StandardScreenTestAPI(Class<S> screenClass, S screen) {
        super(screenClass, screen);
    }

    public StandardScreenTestAPI(Class<S> screenClass, Supplier<S> screenSupplier) {
        super(screenClass, screenSupplier);
    }

}
