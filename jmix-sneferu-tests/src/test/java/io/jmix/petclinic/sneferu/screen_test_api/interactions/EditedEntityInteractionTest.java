package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static de.diedavids.sneferu.ComponentDescriptors.dateField;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class EditedEntityInteractionTest {

  @Autowired
  DataManager dataManager;

  @Test
  void when_interactionIsPerformed_then_editedEntityIsReturned(UiTestAPI uiTestAPI) {

    // given:
    final Visit expectedVisit = dataManager.create(Visit.class);

    // and:
    StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI
            .openStandardEditor(Visit.class, VisitEdit.class, expectedVisit);

    // when:
    final Visit actualVisitEntity = visitEdit
            .andThenGet(editedEntity());

    // then:
    assertThat(actualVisitEntity).isEqualTo(expectedVisit);
  }

}
