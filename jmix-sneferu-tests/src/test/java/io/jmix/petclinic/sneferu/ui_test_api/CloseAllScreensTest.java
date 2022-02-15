package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import io.jmix.petclinic.screen.visit.MyVisits;
import io.jmix.ui.Screens;
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
class CloseAllScreensTest {

  @BeforeEach
  void setUp(Screens screens) {
    screens.removeAll();
  }

  @Test
  void given_oneScreenIsOpen_when_closeAllScreens_then_allScreensAreClosed(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    assertThat(screens.getOpenedScreens().getAll())
            .isEmpty();

    // and:
    uiTestAPI.openStandardScreen(MyVisits.class);

    assertThat(screens.getOpenedScreens().getAll())
            .hasSize(1);

    // when:
    uiTestAPI.closeAllScreens();

    // then:
    assertThat(screens.getOpenedScreens().getAll())
            .isEmpty();

  }

}
