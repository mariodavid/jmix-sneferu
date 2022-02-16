package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.screen.visit.VisitEdit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static de.diedavids.sneferu.ComponentDescriptors.button;
import static de.diedavids.sneferu.Interactions.click;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class ClickInteractionTest {

  @Test
  void when_interactionIsPerformed_then_clickInteractionIsPerformedAndScreenIsOpened(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // and:
    assertThat(openedScreen.screen().getWindow().getComponent("createBtn"))
            .isNotNull();

    // when:
    openedScreen.interact(click(button("createBtn")));

    // then:
    assertThat(uiTestAPI.getOpenedEditorScreen(VisitEdit.class))
            .isNotNull();
  }

}
