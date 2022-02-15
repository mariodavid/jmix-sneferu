package de.diedavids.sneferu.screen;

import io.jmix.ui.screen.StandardEditor;

import java.util.function.Supplier;

public class StandardEditorTestAPI<E, S extends StandardEditor<E>> extends ScreenTestAPI<S, StandardEditorTestAPI> {

    public StandardEditorTestAPI(Class<S> editorScreenClass, S screen) {
        super(editorScreenClass, screen);
    }

    public StandardEditorTestAPI(Class<S> editorScreenClass, Supplier<S> screenSupplier) {
        super(editorScreenClass, screenSupplier);
    }

}
