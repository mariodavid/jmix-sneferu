package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.petclinic.entity.veterinarian.Veterinarian;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.veterinarian.veterinarian.VeterinarianEdit;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static de.diedavids.sneferu.ComponentDescriptors.dateField;
import static de.diedavids.sneferu.ComponentDescriptors.textInputField;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class CloseEditorInteractionTest {

  @Test
  void given_validForm_when_interactionIsPerformed_then_screenIsClosedAndSuccessIsReturned(UiTestAPI uiTestAPI) {

    // given:
    StandardEditorTestAPI<Veterinarian, VeterinarianEdit> visitEdit = uiTestAPI
            .openStandardEditor(Veterinarian.class, VeterinarianEdit.class);

    // and:
    visitEdit
            .interact(enter(textInputField("firstNameField"), "Oak"));

    // when:
    final OperationResult operationResult = visitEdit.andThenGet(closeEditor());

    // then:
    assertThat(operationResult).isEqualTo(OperationResult.success());
  }

  @Test
  void given_invalidForm_when_interactionIsPerformedWith_then_screenIsClosedAndFailIsReturned(UiTestAPI uiTestAPI) {

    // given:
    StandardEditorTestAPI<Veterinarian, VeterinarianEdit> visitEdit = uiTestAPI
            .openStandardEditor(Veterinarian.class, VeterinarianEdit.class);

    // and:
    visitEdit
            .interact(enter(textInputField("firstNameField"), null));

    // when:
    final OperationResult operationResult = visitEdit.andThenGet(closeEditor());

    // then:
    assertThat(operationResult).isEqualTo(OperationResult.fail());
  }

}
