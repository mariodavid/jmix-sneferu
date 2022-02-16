package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.veterinarian.Veterinarian;
import io.jmix.petclinic.screen.veterinarian.veterinarian.VeterinarianEdit;
import io.jmix.petclinic.sneferu.DatabaseCleanup;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static de.diedavids.sneferu.ComponentDescriptors.textInputField;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class CancelEditorInteractionTest {

  @Autowired
  DataManager dataManager;

  @Autowired
  DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp() {
    databaseCleanup.removeAllEntities(Veterinarian.class);
  }

  @Test
  void when_interactionIsPerformed_then_screenIsCanceledAndNothingIsCommitted(UiTestAPI uiTestAPI) {

    // given:
    StandardEditorTestAPI<Veterinarian, VeterinarianEdit> visitEdit = uiTestAPI
            .openStandardEditor(Veterinarian.class, VeterinarianEdit.class);

    // and:
    visitEdit
            .interact(enter(textInputField("firstNameField"), "Oak"));

    // when:
    final OperationResult operationResult = visitEdit.andThenGet(cancelEditor());

    // then:
    assertThat(operationResult).isEqualTo(OperationResult.success());

    // and:
    assertThat(dataManager.load(Veterinarian.class).all().list())
            .isEmpty();
  }

}
