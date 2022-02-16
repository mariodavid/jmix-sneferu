package io.jmix.petclinic.sneferu;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.StartScreen;
import de.diedavids.sneferu.SubsequentScreen;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SneferuUiTest(authenticatedUser = "admin", mainScreenId = "petclinic_MainScreen", screenBasePackages = "io.jmix.petclinic")
class SneferuJunitParametersTest {

  @Test
  void uiTestApi_sneferuParameter_canBeInjectedIntoTheTest(Screens screens, UiTestAPI uiTestAPI) {
    assertThat(screens).isNotNull();
    assertThat(uiTestAPI).isNotNull();
  }

  @Test
  void startScreen_testParameter_willOpenTheScreenImmediately(
          UiTestAPI uiTestAPI,
          @StartScreen StandardLookupTestAPI<Visit,VisitBrowse> visitBrowse
  ) {
    assertThat(uiTestAPI.isActive(visitBrowse)).isTrue();
  }

  @Test
  void subsequentScreen_testParameter_willReturnATestAPI_withoutOpeningTheScreenImmediately(
          UiTestAPI uiTestAPI,
          @SubsequentScreen StandardEditorTestAPI<Visit, VisitEdit> visitEdit
  ) {
    assertThat(uiTestAPI.isActive(visitEdit)).isFalse();

    // normally a test would trigger some interaction that causes the screen to be opened.
    // In this case it will directly opened via the test
    uiTestAPI.openStandardEditor(Visit.class, VisitEdit.class);

    assertThat(uiTestAPI.isActive(visitEdit)).isTrue();
  }

}
