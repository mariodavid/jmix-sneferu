package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static de.diedavids.sneferu.ComponentDescriptors.comboBox;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class SetValueInteractionTest {

  @Test
  void when_interactionIsPerformed_then_valueIsSet(UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = openVisitEditor(uiTestAPI);

    // and:
    final VisitType recharge = VisitType.RECHARGE;

    // when:
    openedScreen.interact(setValue(comboBox("typeField"), recharge));

    // then:
    typeHasValue(openedScreen, recharge);
  }


  @Test
  void enter_isAnAliasForSetValue(UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = openVisitEditor(uiTestAPI);

    // and:
    final VisitType recharge = VisitType.RECHARGE;

    // when:
    openedScreen.interact(enter(comboBox("typeField"), recharge));

    // then:
    typeHasValue(openedScreen, recharge);
  }

  @Test
  void select_isAnAliasForSetValue(UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = openVisitEditor(uiTestAPI);

    // and:
    final VisitType recharge = VisitType.RECHARGE;

    // when:
    openedScreen.interact(select(comboBox("typeField"), recharge));

    // then:
    typeHasValue(openedScreen, recharge);
  }

  @NotNull
  private StandardEditorTestAPI<Visit, VisitEdit> openVisitEditor(UiTestAPI uiTestAPI) {
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = uiTestAPI.openStandardEditor(Visit.class, VisitEdit.class);

    assertThat(openedScreen.component(comboBox("typeField"))).isNotNull();

    return openedScreen;
  }

  private void typeHasValue(StandardEditorTestAPI<Visit, VisitEdit> openedScreen, VisitType recharge) {
    assertThat(openedScreen.rawComponent(comboBox("typeField")).getValue())
            .isEqualTo(recharge);
  }

}
