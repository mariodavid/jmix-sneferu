package io.jmix.petclinic.sneferu.ui_test_api;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardScreenTestAPI;
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
class IsActiveTest {

  @BeforeEach
  void setUp(Screens screens) {
    screens.removeAll();
  }

  @Test
  void given_screenIsOpen_expect_isActiveIsTrue(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    assertThat(screens.getOpenedScreens().getAll())
            .isEmpty();

    // and:
    final StandardScreenTestAPI<MyVisits> openedScreen = uiTestAPI.openStandardScreen(MyVisits.class);

    // expect:
    assertThat(uiTestAPI.isActive(openedScreen))
            .isTrue();

  }

  @Test
  void given_screenIsNotOpen_expect_isActiveIsFalse(Screens screens, UiTestAPI uiTestAPI) {

    // given:
    assertThat(screens.getOpenedScreens().getAll())
            .isEmpty();

    // and:
    final StandardScreenTestAPI<MyVisits> notOpenedScreen = uiTestAPI.getLazyOpenedStandardScreen(MyVisits.class);

    // expect:
    assertThat(uiTestAPI.isActive(notOpenedScreen))
            .isFalse();
  }

}
