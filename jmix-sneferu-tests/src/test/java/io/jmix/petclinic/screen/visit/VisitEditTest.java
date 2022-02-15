package io.jmix.petclinic.screen.visit;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.environment.StartScreen;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.screen.main.MainScreen;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

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
class VisitEditTest {

    @Autowired
    DataManager dataManager;

    private Pet pet;

    @BeforeEach
    public void setupEnvironment(UiTestAPI uiTestAPI) {

        pet = createPet();

        StandardLookupTestAPI<Visit, VisitBrowse> visitBrowse = uiTestAPI
                .openStandardLookup(Visit.class, VisitBrowse.class);

        visitBrowse.interact(click(button("createBtn")));


    }

    private Pet createPet() {
        Pet newPet = dataManager.create(Pet.class);
        newPet.setName("Pikachu");
        newPet.setIdentificationNumber("092");
        return dataManager.save(newPet);
    }

    @Test
    void aVisitCanBeCreated_whenAllFieldsAreFilled(UiTestAPI uiTestAPI) {

        StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI
                .openStandardEditor(Visit.class, VisitEdit.class);
       // when:
        OperationResult outcome = (OperationResult) visitEdit
                .interact(enter(dateField("visitStartField"), LocalDateTime.now()))
                .interact(enter(textField("descriptionField"), "Regular Visit"))
                .interact(select(comboBox("typeField"), VisitType.RECHARGE))
                .interact(select(entityComboBox("petField"), pet))
                .andThenGet(closeEditor());

      // then:
        assertThat(outcome).isEqualTo(OperationResult.success());

    }

    @Test
    void aVisitCannotBeCreated_whenPetIsMissing(UiTestAPI uiTestAPI) {

        StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI
                .openStandardEditor(Visit.class, VisitEdit.class);
        // when:
        visitEdit
                .interact(enter(entityComboBox("petField"), null));

        // and:
        OperationResult outcome = (OperationResult) visitEdit
                .interact(enter(textField("descriptionField"), "Regular Visit"))
                .interact(select(comboBox("typeField"), VisitType.RECHARGE))
                .andThenGet(closeEditor());

        // then:
        assertThat(outcome).isEqualTo(OperationResult.fail());

        // and:
        assertThat(visitEdit.screen().getWindow().validateAll())
                .isFalse();
    }
}
