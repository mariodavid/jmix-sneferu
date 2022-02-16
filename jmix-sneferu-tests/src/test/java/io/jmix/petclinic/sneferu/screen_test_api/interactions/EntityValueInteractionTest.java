package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.components.descriptor.TabsheetComponentDescriptor;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import io.jmix.ui.component.TabSheet;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class EntityValueInteractionTest {

  @Test
  void when_interactionIsPerformed_then_valueIsReturnedFromTheEntity(UiTestAPI uiTestAPI) {

    // given:
    StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI
            .openStandardEditor(Visit.class, VisitEdit.class);

    // and:
    final LocalDateTime visitStart = LocalDateTime.now();
    visitEdit
            .interact(enter(dateField("visitStartField"), visitStart));

    // when:
    final LocalDateTime actualVisitStart = (LocalDateTime) visitEdit
            .andThenGet(entityValue("visitStart", LocalDateTime.class));

    // then:
    assertThat(actualVisitStart).isEqualTo(visitStart);
  }

}
