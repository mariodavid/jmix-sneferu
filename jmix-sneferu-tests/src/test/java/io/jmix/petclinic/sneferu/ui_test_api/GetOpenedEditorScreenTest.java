package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class GetOpenedEditorScreenTest {

  @Autowired
  DataManager dataManager;

  @Test
  void given_editorScreenIsOpen_when_getOpenedScreen_then_screenIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final VisitEdit screen = screens.create(VisitEdit.class);
    screen.setEntityToEdit(dataManager.create(Visit.class));

    // and:
    screen.show();

    // when:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = uiTestAPI.getOpenedEditorScreen(VisitEdit.class);

    // then:
    assertThat(openedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_noEditorScreenIsOpen_when_getOpenedScreen_then_exceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final ScreenNotOpenException exception = Assertions.assertThrows(ScreenNotOpenException.class, () -> uiTestAPI.getOpenedEditorScreen(VisitEdit.class));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class VisitEdit found");
  }

}
