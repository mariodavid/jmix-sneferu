package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.components.descriptor.DateFieldComponentDescriptor;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.screen.visit.VisitEdit;
import org.junit.jupiter.api.Test;
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
class GetValueInteractionTest {

  private final LocalDateTime VISIT_START_VALUE = LocalDateTime.now();

  private final DateFieldComponentDescriptor VISIT_START_FIELD = dateField("visitStartField");

  @Test
  void when_interactionIsPerformed_then_valueIsReturnedFromTheComponent(UiTestAPI uiTestAPI) {

    // given:
    StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI
            .openStandardEditor(Visit.class, VisitEdit.class);

    // and:

    visitEdit
            .interact(enter(VISIT_START_FIELD, VISIT_START_VALUE));

    // when:
    final LocalDateTime actualVisitStart = (LocalDateTime) visitEdit
            .andThenGet(getValue(VISIT_START_FIELD));

    // then:
    assertThat(actualVisitStart).isEqualTo(VISIT_START_VALUE);
  }

  @Test
  void componentValue_isAnAliasForGetValue(UiTestAPI uiTestAPI) {

    // given:
    StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI
            .openStandardEditor(Visit.class, VisitEdit.class);

    visitEdit
            .interact(enter(VISIT_START_FIELD, VISIT_START_VALUE));

    // when:
    final LocalDateTime actualVisitStart = (LocalDateTime) visitEdit
            .andThenGet(componentValue(VISIT_START_FIELD));

    // then:
    assertThat(actualVisitStart).isEqualTo(VISIT_START_VALUE);
  }

}
