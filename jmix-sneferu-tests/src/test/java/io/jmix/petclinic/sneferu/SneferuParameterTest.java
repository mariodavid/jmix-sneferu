package io.jmix.petclinic.sneferu;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class SneferuParameterTest {

  @Autowired
  DataManager dataManager;

  @Test
  void sneferuParametersCanBeInjectedIntoTheTest(Screens screens, UiTestAPI uiTestAPI) {
    assertThat(screens).isNotNull();
    assertThat(uiTestAPI).isNotNull();
  }

}
