package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class GetLazyOpenedEditorScreenTest {

  @Autowired
  private DataManager dataManager;

  @Test
  void given_screenIsOpenedAfterRetrievingTheLazyReference_when_accessingScreen_then_screenIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> lazyOpenedScreen = uiTestAPI.getLazyOpenedEditorScreen(VisitEdit.class);

    // and: screen is opened through the application (or test in this case)
    final VisitEdit screen = showVisitEditor(screens);

    // expect: the previous retrieved lazy reference will return the correct reference to the screen
    assertThat(lazyOpenedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_screenIsAlreadyOpened_when_getLazyOpenedEditorScreen_then_lazyTestApiIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final VisitEdit screen = showVisitEditor(screens);

    // when:
    final StandardEditorTestAPI<Visit, VisitEdit> lazyOpenedScreen = uiTestAPI.getLazyOpenedEditorScreen(VisitEdit.class);

    // then:
    assertThat(lazyOpenedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_noScreenIsOpened_when_getLazyOpenedEditorScreen_then_lazyTestApiIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final StandardEditorTestAPI<Visit, VisitEdit> lazyOpenedScreen = uiTestAPI.getLazyOpenedEditorScreen(VisitEdit.class);

    // then:
    assertThat(lazyOpenedScreen)
            .isNotNull();
  }

  @Test
  void given_lazyTestApiIsReturned_when_accessingScreen_then_ScreenNotFoundExceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final StandardEditorTestAPI<Visit, VisitEdit> lazyOpenedScreen = uiTestAPI.getLazyOpenedEditorScreen(VisitEdit.class);

    // then:
    final ScreenNotOpenException exception = Assertions.assertThrows(ScreenNotOpenException.class, lazyOpenedScreen::screen);

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class VisitEdit found");
  }

  @NotNull
  private VisitEdit showVisitEditor(Screens screens) {
    final VisitEdit screen = screens.create(VisitEdit.class);
    screen.setEntityToEdit(dataManager.create(Visit.class));
    screen.show();
    return screen;
  }

}
