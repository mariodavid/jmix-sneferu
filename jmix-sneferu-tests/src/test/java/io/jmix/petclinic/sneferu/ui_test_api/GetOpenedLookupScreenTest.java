package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.pet.pet.PetBrowse;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class GetOpenedLookupScreenTest {

  @Test
  void given_lookupScreenIsOpen_when_getOpenedScreen_then_screenIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final VisitBrowse screen = screens.create(VisitBrowse.class);

    // and:
    screen.show();

    // when:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.getOpenedLookupScreen(VisitBrowse.class);

    // then:
    assertThat(openedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_noLookupScreenIsOpen_when_getOpenedScreen_then_exceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final ScreenNotOpenException exception = Assertions.assertThrows(ScreenNotOpenException.class, () -> uiTestAPI.getOpenedLookupScreen(VisitBrowse.class));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class VisitBrowse found");
  }

  @Test
  void given_differentLookupScreenIsOpen_when_getOpenedScreen_then_exceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    screens.create(PetBrowse.class)
            .show();

    // when:
    final ScreenNotOpenException exception = Assertions.assertThrows(ScreenNotOpenException.class, () -> uiTestAPI.getOpenedLookupScreen(VisitBrowse.class));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class VisitBrowse found");
  }
}
