package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardScreenTestAPI;
import io.jmix.petclinic.screen.visit.MyVisits;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class GetLazyOpenedStandardScreenTest {

  @Test
  void given_screenIsOpenedAfterRetrievingTheLazyReference_when_accessingScreen_then_screenIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final StandardScreenTestAPI<MyVisits> lazyOpenedScreen = uiTestAPI.getLazyOpenedStandardScreen(MyVisits.class);

    // and: screen is opened through the application (or test in this case)
    final MyVisits screen = screens.create(MyVisits.class);
    screen.show();

    // expect: the previous retrieved lazy reference will return the correct reference to the screen
    assertThat(lazyOpenedScreen.screen())
            .isEqualTo(screen);
  }


  @Test
  void given_screenIsAlreadyOpened_when_getLazyOpenedStandardScreen_then_lazyTestApiIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final MyVisits screen = screens.create(MyVisits.class);

    // and:
    screen.show();

    // when:
    final StandardScreenTestAPI<MyVisits> lazyOpenedScreen = uiTestAPI.getLazyOpenedStandardScreen(MyVisits.class);

    // then:
    assertThat(lazyOpenedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_noScreenIsOpened_when_getLazyOpenedStandardScreen_then_lazyTestApiIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final StandardScreenTestAPI<MyVisits> lazyOpenedScreen = uiTestAPI.getLazyOpenedStandardScreen(MyVisits.class);

    // then:
    assertThat(lazyOpenedScreen)
            .isNotNull();
  }

  @Test
  void given_lazyTestApiIsReturned_when_accessingScreen_then_ScreenNotFoundExceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final StandardScreenTestAPI<MyVisits> lazyOpenedScreen = uiTestAPI.getLazyOpenedStandardScreen(MyVisits.class);

    // then:
    final ScreenNotOpenException exception = Assertions.assertThrows(ScreenNotOpenException.class, lazyOpenedScreen::screen);

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class MyVisits found");
  }

}
