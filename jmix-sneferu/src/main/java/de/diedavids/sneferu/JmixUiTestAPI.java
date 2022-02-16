package de.diedavids.sneferu;

import de.diedavids.sneferu.screen.InputDialogTestAPI;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import de.diedavids.sneferu.screen.StandardScreenTestAPI;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.StandardLookup;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

public class JmixUiTestAPI implements UiTestAPI {

    private final ScreenBuilders screenBuilders;
    private final Screens screens;

    public JmixUiTestAPI(
            ScreenBuilders screenBuilders,
            Screens screens
    ) {
        this.screenBuilders = screenBuilders;
        this.screens = screens;
    }

    @Override
    public <E, S extends StandardEditor<E>> StandardEditorTestAPI<E, S> getOpenedEditorScreen(
        Class<S> screenEditorClass
    ) {
        Screen screen = getLastOpenedScreen();

        if (screenTypesMatch(screen, screenEditorClass)) {
            S castedScreen = (S) screen;
            return new StandardEditorTestAPI(screenEditorClass, castedScreen);
        }
        else {
            throw screenNotFoundException(screenEditorClass);
        }
    }

    @Override
    public <E, S extends StandardEditor<E>> StandardEditorTestAPI<E,S> getLazyOpenedEditorScreen(
        Class<S> screenEditorClass
    ) {
        final Optional<StandardEditorTestAPI<E, S>> optionalScreen = tryToOpenStandardEditor(
            screenEditorClass
        );

        return optionalScreen.orElseGet(() -> new StandardEditorTestAPI<E, S>(
            screenEditorClass, () -> {
            final Optional<StandardEditorTestAPI<E, S>> lazyOptionalScreen = tryToOpenStandardEditor(
                screenEditorClass
            );

            return lazyOptionalScreen
                    .map(ScreenTestAPI::screen)
                    .orElseThrow(() -> screenNotFoundException(screenEditorClass));
        }));
    }

    @Override
    public <E, S extends StandardLookup<E>> StandardLookupTestAPI<E, S> getLazyOpenedLookupScreen(
        Class<S> screenLookupClass
    ) {
        final Optional<StandardLookupTestAPI<E, S>> optionalScreen = tryToOpenStandardLookup(
            screenLookupClass
        );

        return optionalScreen.orElseGet(() -> new StandardLookupTestAPI<E, S>(
            screenLookupClass, () -> {
            final Optional<StandardLookupTestAPI<E, S>> lazyOptionalScreen = tryToOpenStandardLookup(
                screenLookupClass
            );

            return lazyOptionalScreen
                .map(ScreenTestAPI::screen)
                .orElseThrow(() -> screenNotFoundException(screenLookupClass));
        }));
    }

    @Override
    public <S extends Screen> StandardScreenTestAPI<S> getLazyOpenedStandardScreen(Class<S> screenClass) {
        final Optional<StandardScreenTestAPI<S>> optionalScreen = tryToOpenStandardScreen(
            screenClass
        );

        return optionalScreen.orElseGet(() -> new StandardScreenTestAPI<S>(
            screenClass, () -> {
            final Optional<StandardScreenTestAPI<S>> lazyOptionalScreen = tryToOpenStandardScreen(
                screenClass
            );

            return lazyOptionalScreen
                .map(ScreenTestAPI::screen)
                .orElseThrow(() -> screenNotFoundException(screenClass));
        }));
    }

    private <E, S extends StandardEditor<E>> Optional<StandardEditorTestAPI<E,S>> tryToOpenStandardEditor(
        Class<S> screenEditorClass
    ) {
        Screen screen = getLastOpenedScreen();

        if (screenTypesMatch(screen, screenEditorClass)) {
            S castedScreen = (S) screen;
            return Optional.of(new StandardEditorTestAPI(screenEditorClass, castedScreen));
        }
        else {
            return Optional.empty();
        }
    }


    private <E, S extends StandardLookup<E>> Optional<StandardLookupTestAPI<E,S>> tryToOpenStandardLookup(
        Class<S> screenLookupClass
    ) {
        Screen screen = getLastOpenedScreen();

        if (screenTypesMatch(screen, screenLookupClass)) {
            S castedScreen = (S) screen;
            return Optional.of(new StandardLookupTestAPI(screenLookupClass, castedScreen));
        }
        else {
            return Optional.empty();
        }
    }


    private <S extends Screen> Optional<StandardScreenTestAPI<S>> tryToOpenStandardScreen(
        Class<S> screenClass
    ) {
        Screen screen = getLastOpenedScreen();

        if (screenTypesMatch(screen, screenClass)) {
            S castedScreen = (S) screen;
            return Optional.of(new StandardScreenTestAPI(screenClass, castedScreen));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public <E, S extends StandardLookup<E>> StandardLookupTestAPI<E, S> getOpenedLookupScreen(
        Class<S> screenLookupClass
    ) {

        Screen screen = getLastOpenedScreen();

        if (screenTypesMatch(screen, screenLookupClass)) {
            S castedScreen = (S) screen;
            return new StandardLookupTestAPI<>(screenLookupClass, castedScreen);
        }
        else {
            throw screenNotFoundException(screenLookupClass);
        }
    }

    @NotNull
    private ScreenNotOpenException screenNotFoundException(Class screenClass) {
        return new ScreenNotOpenException(String.format("No Screen of class %s found", screenClass.getSimpleName()));
    }

    @Override
    public <S extends Screen> StandardScreenTestAPI<S> getOpenedStandardScreen(
        Class<S> screenClass
    ) {
        Screen screen = getLastOpenedScreen();

        if (screenTypesMatch(screen, screenClass)) {
            S castedScreen = (S) screen;
            return new StandardScreenTestAPI<>(screenClass, castedScreen);
        }
        else {
            throw screenNotFoundException(screenClass);
        }
    }

    @Override
    public <E, S extends StandardEditor<E>> StandardEditorTestAPI<E,S> openStandardEditor(
        Class<E> entityClass,
        Class<S> standardEditorClass
    ) {
        S screen = screenBuilders.editor(entityClass, rootScreen())
                .newEntity()
                .withScreenClass(standardEditorClass)
                .show();
        return new StandardEditorTestAPI<>(standardEditorClass, screen);
    }

    private Screen rootScreen() {
        return screens.getOpenedScreens().getRootScreen();
    }

    @Override
    public <E, S extends StandardEditor<E>> StandardEditorTestAPI<E,S> openStandardEditor(
        Class<E> entityClass,
        Class<S> standardEditorClass,
        E entity
    ) {
        S screen = screenBuilders.editor(entityClass, rootScreen())
            .editEntity(entity)
            .withScreenClass(standardEditorClass)
            .show();
        return new StandardEditorTestAPI<>(standardEditorClass, screen);
    }

    @Override
    public <E, S extends StandardLookup<E>> StandardLookupTestAPI<E, S> openStandardLookup(
        Class<E> entityClass,
        Class<S> lookupScreenClass
    ) {
        S screen = screenBuilders.lookup(entityClass, rootScreen())
                .withScreenClass(lookupScreenClass)
                .show();
        return new StandardLookupTestAPI<>(lookupScreenClass, screen);
    }

    @Override
    public <S extends Screen> StandardScreenTestAPI<S> openStandardScreen(Class<S> screenClass) {

        final S screen = screenBuilders.screen(rootScreen())
            .withScreenClass(screenClass)
            .show();

        return new StandardScreenTestAPI<>(screenClass, screen);
    }


    @Override
    public boolean isActive(ScreenTestAPI screenTestAPI) {

        return new ArrayList<>(getOpenedScreens()
                .getActiveScreens()).stream()
                .anyMatch(screen -> screenTypeMatches(screenTestAPI, screen));
    }

    private boolean screenTypeMatches(ScreenTestAPI screenTestAPI, Screen screen) {
        return (boolean) screenTestAPI.optionalScreen()
                .map(it -> it.getClass().isAssignableFrom(screen.getClass()))
                .orElse(false);
    }

    private Screens.OpenedScreens getOpenedScreens() {
        return screens.getOpenedScreens();
    }

    private Screen getLastOpenedScreen() {
        ArrayList<Screen> activeScreens = new ArrayList<>(
            getOpenedScreens().getAll()
        );

        if (!activeScreens.isEmpty()) {
            return activeScreens.get(activeScreens.size() - 1);
        }
        else {
            return null;
        }
    }

    <S extends Screen> boolean screenTypesMatch(Screen screen, Class<S> screenClass) {
        return screen != null && screenClass.isAssignableFrom(screen.getClass());
    }

    @Override
    public void closeAllScreens() {
        screens.removeAll();
    }


    @Override
    public InputDialogTestAPI openedInputDialog() {
        return getOpenedInputDialog();
    }

    @Override
    public InputDialogTestAPI getOpenedInputDialog() {
        return new ArrayList<>(getOpenedScreens()
                .getDialogScreens()).stream()
                .filter(screen -> InputDialog.class.isAssignableFrom(screen.getClass()))
                .findFirst()
                .map(screen -> new InputDialogTestAPI(InputDialog.class, (InputDialog) screen))
                .orElseThrow(() -> screenNotFoundException(InputDialog.class));
    }
}
