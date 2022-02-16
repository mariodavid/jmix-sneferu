package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardScreenTestAPI;
import io.jmix.petclinic.screen.pet.pet.PetBrowse;
import io.jmix.petclinic.screen.visit.MyVisits;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class GetOpenedStandardScreenTest {

  @Test
  void given_lookupScreenIsOpen_when_getOpenedScreen_then_screenIsReturned(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    final MyVisits screen = screens.create(MyVisits.class);

    // and:
    screen.show();

    // when:
    final StandardScreenTestAPI<MyVisits> openedScreen = uiTestAPI.getOpenedStandardScreen(MyVisits.class);

    // then:
    assertThat(openedScreen.screen())
            .isEqualTo(screen);
  }

  @Test
  void given_noStandardScreenIsOpen_when_getOpenedScreen_then_exceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    // when:
    final ScreenNotOpenException exception = assertThrows(ScreenNotOpenException.class, () -> uiTestAPI.getOpenedStandardScreen(MyVisits.class));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class MyVisits found");
  }

  @Test
  void given_differentStandardScreenIsOpen_when_getOpenedScreen_then_exceptionIsThrown(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    screens.removeAll();

    screens.create(PetBrowse.class)
            .show();

    // when:
    final ScreenNotOpenException exception = assertThrows(ScreenNotOpenException.class, () -> uiTestAPI.getOpenedStandardScreen(MyVisits.class));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("No Screen of class MyVisits found");
  }

}
