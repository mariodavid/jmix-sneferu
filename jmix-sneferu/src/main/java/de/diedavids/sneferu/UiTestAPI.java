package de.diedavids.sneferu;


import de.diedavids.sneferu.screen.*;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.StandardLookup;


/**
 * UITestAPI is the main entry point for interacting with Screens in a web integration test case.
 *
 * This API provides the ability to receive / work with ScreenTestAPI instances, that allow
 * to interact with a particular screen via its Test API.
 */
public interface UiTestAPI {

  /**
   * returns a Test API instance for a given Standard Editor Screen class if screen is opened.
   *
   * If screen type is not open, a ScreenNotOpenException will be thrown
   *
   * @param screenEditorClass the type of the Standard Editor Screen
   * @param <S> the type of the Screen
   * @param <E> type of the Entity
   *
   * @return an instance of the Test API
   */
  <E, S extends StandardEditor<E>> StandardEditorTestAPI<E, S> getOpenedEditorScreen(
      Class<S> screenEditorClass
  );


  /**
   * returns a Test API instance for a given Standard Editor Screen class.
   *
   * If screen type is not open, the Test API is still returned, but first time an interaction
   * with the screen happens and the screen is not open, a ScreenNotOpenException will be thrown.
   *
   * This can be used for getting a reference to a screen Test API even if the screen is not yet opened.
   *
   * @param screenEditorClass the type of the Standard Editor Screen
   * @param <S> the type of the Screen
   * @param <E> type of the Entity
   *
   * @return an instance of the Test API
   */
  <E, S extends StandardEditor<E>> StandardEditorTestAPI<E, S> getLazyOpenedEditorScreen(
      Class<S> screenEditorClass
  );


  /**
   * returns a Test API instance for a given Standard Lookup Screen class if screen is opened.
   *
   * If screen type is not open, a ScreenNotOpenException will be thrown
   *
   * @param screenLookupClass the type of the Standard Lookup Screen
   * @param <S> the type of the Screen
   * @param <E> type of the Entity
   *
   * @return an instance of the Test API
   */
  <E, S extends StandardLookup<E>> StandardLookupTestAPI<E, S> getOpenedLookupScreen(
      Class<S> screenLookupClass
  );


  /**
   * returns a Test API instance for a given Standard Lookup Screen class if screen is opened.
   *
   * If screen type is not open, the Test API is still returned, but first time an interaction
   * with the screen happens and the screen is not open, a ScreenNotOpenException will be thrown.
   *
   * This can be used for getting a reference to a screen Test API even if the screen is not yet opened.
   *
   * @param screenLookupClass the type of the Standard Lookup Screen
   * @param <S> the type of the Screen
   * @param <E> type of the Entity
   *
   * @return an instance of the Test API
   */
  <E, S extends StandardLookup<E>> StandardLookupTestAPI<E, S> getLazyOpenedLookupScreen(
          Class<S> screenLookupClass
  );


  /**
   * returns a Test API instance for a given Screen class if screen is opened.
   *
   * If screen type is not open, a ScreenNotOpenException will be thrown
   *
   * @param screenClass the type of the Screen
   * @param <S> the type of the Screen
   *
   * @return an instance of the StandardScreenTestAPI
   */
  <S extends Screen> StandardScreenTestAPI<S> getOpenedStandardScreen(
      Class<S> screenClass
  );


  /**
   * returns a Test API instance for a given Screen class if screen is opened.
   *
   * If screen type is not open, the Test API is still returned, but first time an interaction
   * with the screen happens and the screen is not open, a ScreenNotOpenException will be thrown.
   *
   * This can be used for getting a reference to a screen Test API even if the screen is not yet opened.
   *
   * @param screenClass the type of the Screen
   * @param <S> the type of the Screen
   *
   * @return an instance of the StandardScreenTestAPI
   */
  <S extends Screen> StandardScreenTestAPI<S> getLazyOpenedStandardScreen(
          Class<S> screenClass
  );



  /**
   * @deprecated use UiTestAPI::getOpenedInputDialog instead
   * retrieves the currently opened InputDialog (if any)
   *
   * @return instance of InputDialog (via InputDialogTestAPI)
   */
  @Deprecated
  InputDialogTestAPI openedInputDialog();


  /**
   * retrieves the currently opened InputDialog (if any)
   *
   * @return instance of InputDialog (via InputDialogTestAPI)
   */
  InputDialogTestAPI getOpenedInputDialog();




  /**
   * opens a Standard Editor Screen
   * @param entityClass the entity class of the Editor
   * @param standardEditorClass the class of the Editor
   * @param <E> type of the Entity
   * @param <S> type of the Standard Editor
   * @return an instance of this editor screen (via StandardEditorTestAPI)
   */
  <E, S extends StandardEditor<E>> StandardEditorTestAPI<E, S> openStandardEditor(
      Class<E> entityClass,
      Class<S> standardEditorClass
  );

  /**
   * opens a Standard Editor Screen for a given Entity
   * @param entityClass the entity class of the Editor
   * @param standardEditorClass the class of the Editor
   * @param entity the Entity instance to use
   * @param <E> type of the Entity
   * @param <S> type of the Standard Editor
   * @return an instance of this editor screen (via StandardEditorTestAPI)
   */
  <E, S extends StandardEditor<E>> StandardEditorTestAPI<E, S> openStandardEditor(
      Class<E> entityClass,
      Class<S> standardEditorClass,
      E entity
  );



  /**
   * opens a Standard Lookup Screen
   * @param entityClass the entity class of the StandardLookup
   * @param lookupScreenClass the class of the StandardLookup
   * @param <E> type of the Entity
   * @param <S> type of the StandardLookup
   * @return an instance of this lookup screen (via StandardLookupTestAPI)
   */
  <E, S extends StandardLookup<E>> StandardLookupTestAPI<E, S> openStandardLookup(
      Class<E> entityClass,
      Class<S> lookupScreenClass
  );


  /**
   * opens a Standard Screen
   * @param screenClass the class of the Screen
   * @param <S> type of the Screen
   * @return an instance of this screen via StandardScreenTestAPI
   */
  <S extends Screen> StandardScreenTestAPI<S> openStandardScreen(
      Class<S> screenClass
  );


  /**
   * checks if a given screen is active
   * @param screenTestAPI the screen (via its ScreenTestAPI)
   * @return true if active, otherwise false
   */
  boolean isActive(ScreenTestAPI screenTestAPI);

  /**
   * closes all screens in the UI
   */
  void closeAllScreens();
}
