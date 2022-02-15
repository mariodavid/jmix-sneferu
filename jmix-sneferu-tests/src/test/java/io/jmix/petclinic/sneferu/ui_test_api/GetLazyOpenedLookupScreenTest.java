package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
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
        screenBasePackages = {
          "io.jmix.petclinic"
        }
)
@SpringBootTest
class GetLazyOpenedLookupScreenTest {

  @BeforeEach
  void setUp(Screens screens) {
    screens.removeAll();
  }

  @Test
  void given_screenIsOpenedAfterRetrievingTheLazyReference_when_accessingScreen_then_screenIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> lazyOpenedScreen = uiTestAPI.getLazyOpenedLookupScreen(VisitBrowse.class);

    // and: screen is opened through the application (or test in this case)
    final VisitBrowse screen = screens.create(VisitBrowse.class);
    screen.show();

    // expect: the previous retrieved lazy reference will return the correct reference to the screen
    assertThat(lazyOpenedScreen.screen())
            .isEqualTo(screen);
  }


  @Test
  void given_screenIsAlreadyOpened_when_getLazyOpenedLookupScreen_then_lazyTestApiIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final VisitBrowse screen = screens.create(VisitBrowse.class);

    // and:
    screen.show();

    // when:
    final StandardLookupTestAPI<Visit, VisitBrowse> lazyOpenedScreen = uiTestAPI.getLazyOpenedLookupScreen(VisitBrowse.class);

    // then:
    assertThat(lazyOpenedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_noScreenIsOpened_when_getLazyOpenedLookupScreen_then_lazyTestApiIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final StandardLookupTestAPI<Visit, VisitBrowse> lazyOpenedScreen = uiTestAPI.getLazyOpenedLookupScreen(VisitBrowse.class);

    // then:
    assertThat(lazyOpenedScreen)
            .isNotNull();
  }

  @Test
  void given_lazyTestApiIsReturned_when_accessingScreen_then_ScreenNotFoundExceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final StandardLookupTestAPI<Visit, VisitBrowse> lazyOpenedScreen = uiTestAPI.getLazyOpenedLookupScreen(VisitBrowse.class);

    // then:
    final ScreenNotOpenException exception = Assertions.assertThrows(ScreenNotOpenException.class, lazyOpenedScreen::screen);

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class VisitBrowse found");
  }

}
