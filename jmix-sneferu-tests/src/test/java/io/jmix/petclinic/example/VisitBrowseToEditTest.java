package io.jmix.petclinic.example;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.click;
import static de.diedavids.sneferu.Interactions.closeEditor;
import static de.diedavids.sneferu.Interactions.enter;
import static de.diedavids.sneferu.Interactions.select;
import static org.assertj.core.api.Assertions.assertThat;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;

import java.time.LocalDateTime;

import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class VisitBrowseToEditTest {

  @Autowired
  DataManager dataManager;

  @Test
  void aVisitCanBeCreated_whenAllFieldsAreFilled(UiTestAPI uiTestAPI) {

    // given:
    final Pet pikachu = dataManager.create(Pet.class);
    pikachu.setName("Pikachu");
    pikachu.setIdentificationNumber("025");
    final Pet savedPikachu = dataManager.save(pikachu);

    // and:
    final StandardLookupTestAPI<Visit, VisitBrowse> visitBrowse = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);
    visitBrowse.interact(click(button("createBtn")));

    // when:
    final StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI.getOpenedEditorScreen(VisitEdit.class);

    OperationResult outcome = (OperationResult) visitEdit
        .interact(enter(dateField("visitStartField"), LocalDateTime.now()))
        .interact(enter(textField("descriptionField"), "Regular Visit"))
        .interact(select(comboBox("typeField"), VisitType.REGULAR_CHECKUP))
        .interact(select(entityComboBox("petField"), savedPikachu))
        .andThenGet(closeEditor());

    // then:
    assertThat(outcome).isEqualTo(OperationResult.success());

    // and:
    assertThat(uiTestAPI.isActive(visitEdit))
      .isFalse();

  }

}
