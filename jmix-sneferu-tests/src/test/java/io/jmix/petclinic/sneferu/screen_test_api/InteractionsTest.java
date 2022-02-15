package io.jmix.petclinic.sneferu.screen_test_api;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = {
          "io.jmix.petclinic"
        }
)
@SpringBootTest
class InteractionsTest {

  @BeforeEach
  void setUp(Screens screens) {
    screens.removeAll();
  }

  @Test
  void when_interactionIsPerformed_then_resultOfTheInteractionIsVisible(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // and:
    assertThat(openedScreen.component(button("createBtn"))).isNotNull();

    // when:
    openedScreen.interact(click(button("createBtn")));

    // then:
    final StandardEditorTestAPI<Visit, VisitEdit> screenAfterCreateButton = uiTestAPI.getLazyOpenedEditorScreen(VisitEdit.class);

    assertThat(uiTestAPI.isActive(screenAfterCreateButton))
            .isTrue();
  }

  @Test
  void when_interactIsPerformedMultipleTimes_then_allInteractionsArePerformed(UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = uiTestAPI.openStandardEditor(Visit.class, VisitEdit.class);

    // and:
    assertThat(openedScreen.component(dateField("visitStartField"))).isNotNull();
    assertThat(openedScreen.component(comboBox("typeField"))).isNotNull();

    // and:
    final LocalDateTime startDate = LocalDateTime.of(2022,1,15,6,0);
    final VisitType recharge = VisitType.RECHARGE;

    // when:
    openedScreen.interact(enter(dateField("visitStartField"), startDate))
            .andThen(select(comboBox("typeField"), recharge));

    // then:
    assertThat(openedScreen.verify(entityValue("visitStart", LocalDateTime.class)))
            .isEqualTo(startDate);
    // and:
    assertThat(openedScreen.verify(entityValue("type", VisitType.class)))
            .isEqualTo(recharge);
  }

  @Test
  void when_verifyIsPerformed_then_resultOfTheInteractionIsReturned(UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = uiTestAPI.openStandardEditor(Visit.class, VisitEdit.class);

    // and:
    assertThat(openedScreen.component(dateField("visitStartField"))).isNotNull();

    // and:
    final LocalDateTime startDate = LocalDateTime.of(2022,1,15,6,0);

    // and:
    openedScreen.interact(enter(dateField("visitStartField"), startDate));

    // when:
    final LocalDateTime enteredVisitStart = openedScreen.verify(entityValue("visitStart", LocalDateTime.class));

    // then:
    assertThat(enteredVisitStart)
            .isEqualTo(startDate);
  }

}
