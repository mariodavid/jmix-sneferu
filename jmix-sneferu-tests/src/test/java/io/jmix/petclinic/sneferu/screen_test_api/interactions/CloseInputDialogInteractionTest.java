package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.ScreenNotOpenException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.InputDialogTestAPI;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.screen.pet.pet.PetBrowse;
import io.jmix.petclinic.screen.pet.pet.PetEdit;
import io.jmix.petclinic.sneferu.DatabaseCleanup;
import io.jmix.ui.Screens;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static de.diedavids.sneferu.ComponentDescriptors.button;
import static de.diedavids.sneferu.ComponentDescriptors.textInputField;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = {
          "io.jmix.petclinic"
        }
)
@SpringBootTest
class CloseInputDialogInteractionTest {

  @Autowired
  DataManager dataManager;
  @Autowired
  DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp(Screens screens) {
    screens.removeAll();
    databaseCleanup.removeAllEntities(Pet.class);
  }

  @Test
  void when_interactionIsPerformed_then_inputDialogIsClosedAndSuccessIsReturned(UiTestAPI uiTestAPI) {

    // given:
    final String pikachuIdentifier = "025";
    final Pet pikachu = storePikachu(pikachuIdentifier);

    // and:
    openPetByIdInputDialog(uiTestAPI);

    // when:
    final InputDialogTestAPI openedInputDialog = uiTestAPI.getOpenedInputDialog();

    // and:
    final OperationResult operationResult = openedInputDialog
            .interact(enter(textInputField("id"), pikachuIdentifier))
            .andThenGet(closeInputDialogWith(InputDialog.INPUT_DIALOG_OK_ACTION));

    // then:
    assertThat(operationResult)
            .isEqualTo(OperationResult.success());


    // and: after the input dialog, the Pet editor is opened
    final StandardEditorTestAPI<Pet, PetEdit> openedPetEditor = uiTestAPI.getOpenedEditorScreen(PetEdit.class);

    final Pet pet = openedPetEditor.get(editedEntity());

    assertThat(pet)
            .isEqualTo(pikachu);
  }

  @Test
  void when_interactionIsPerformedWithCloseAction_then_inputDialogIsClosedAndSuccessIsReturned(UiTestAPI uiTestAPI) {

    // given:
    storePikachu("025");

    // and:
    openPetByIdInputDialog(uiTestAPI);

    // when:
    final InputDialogTestAPI openedInputDialog = uiTestAPI.getOpenedInputDialog();

    // and:
    openedInputDialog
            .interact(enter(textInputField("id"), null))
            .andThenGet(closeInputDialogWith(InputDialog.INPUT_DIALOG_OK_ACTION));

    // then:
    assertThrows(ScreenNotOpenException.class, () -> uiTestAPI.getOpenedEditorScreen(PetEdit.class));
  }

  private void openPetByIdInputDialog(UiTestAPI uiTestAPI) {
    uiTestAPI.openStandardLookup(Pet.class, PetBrowse.class)
            .interact(click(button("openPetByIdBtn")));
  }

  private Pet storePikachu(String identificationNumber) {
    Pet newPet = dataManager.create(Pet.class);
    newPet.setName("Pikachu");
    newPet.setIdentificationNumber(identificationNumber);
    return dataManager.save(newPet);
  }

}
