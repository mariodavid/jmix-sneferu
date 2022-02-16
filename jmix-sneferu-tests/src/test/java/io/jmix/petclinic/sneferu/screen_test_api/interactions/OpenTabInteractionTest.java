package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.components.descriptor.DataGridComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TableComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TabsheetComponentDescriptor;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.pet.PetType;
import io.jmix.petclinic.entity.veterinarian.Veterinarian;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.pet.pettype.PetTypeBrowse;
import io.jmix.petclinic.screen.veterinarian.veterinarian.VeterinarianBrowse;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.sneferu.DatabaseCleanup;
import io.jmix.ui.Screens;
import io.jmix.ui.component.TabSheet;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.openTab;
import static de.diedavids.sneferu.Interactions.selectInList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class OpenTabInteractionTest {

  @Test
  void when_interactionIsPerformed_then_tabIsSelected(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    final TabsheetComponentDescriptor tabSheet = tabSheet("contentTabSheet");
    assertThat(openedScreen.rawComponent(tabSheet)).isNotNull();
    assertThat(openedScreen.rawComponent(tabSheet).getTab("tableTab")).isNotNull();

    // when:
    openedScreen.interact(openTab(tabSheet, "tableTab"));

    // then:
    final TabSheet.Tab selectedTab = openedScreen.rawComponent(tabSheet).getSelectedTab();

    // and:
    assertThat(selectedTab.getName())
            .isEqualTo("tableTab");
  }

  @Test
  void when_interactionIsPerformedWithInvalidTab_then_exceptionIsThrown(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    final TabsheetComponentDescriptor tabSheet = tabSheet("contentTabSheet");
    assertThat(openedScreen.rawComponent(tabSheet)).isNotNull();

    // expect:
    assertThrows(IllegalStateException.class, () ->
            openedScreen.interact(openTab(tabSheet, "notExistingTab"))
    );
  }

}
