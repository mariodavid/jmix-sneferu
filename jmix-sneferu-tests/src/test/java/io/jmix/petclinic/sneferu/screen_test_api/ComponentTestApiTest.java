package io.jmix.petclinic.sneferu.screen_test_api;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.components.testapi.ButtonTestAPI;
import de.diedavids.sneferu.components.testapi.ComboBoxTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.screen.visit.VisitBrowse;
import io.jmix.petclinic.screen.visit.VisitEdit;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.*;
import static de.diedavids.sneferu.Interactions.entityValue;
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
class ComponentTestApiTest {

  @BeforeEach
  void setUp(Screens screens) {
    screens.removeAll();
  }

  @Test
  void given_componentIsPresentOnScreen_when_retrieveComponentViaTestAPI_then_componentTestApiIsReturned(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // and:
    assertThat(openedScreen.screen().getWindow().getComponent("createBtn"))
            .isNotNull();

    // when:
    final ButtonTestAPI createBtnTestAPI = openedScreen.component(button("createBtn"));

    // then:
    assertThat(createBtnTestAPI)
            .isNotNull();
  }


  @Test
  void given_componentIsNotPresentOnScreen_when_retrieveComponentViaTestAPI_then_exceptionIsThrown(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // and:
    assertThat(openedScreen.screen().getWindow().getComponent("notExistingBtn"))
            .isNull();

    // when:
    final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> openedScreen.component(button("notExistingBtn")));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("Not found component with id 'notExistingBtn'");
  }


  @Test
  void given_componentIsPresentOnScreen_when_retrieveRawComponentViaTestAPI_then_actualJmixComponentIsReturned(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // and:
    assertThat(openedScreen.screen().getWindow().getComponent("createBtn"))
            .isNotNull();

    // when:
    final Button createBtn = openedScreen.rawComponent(button("createBtn"));

    // then:
    assertThat(createBtn)
            .isNotNull();
  }

  @Test
  void given_componentIsNotPresentOnScreen_when_retrieveRawComponentViaTestAPI_then_exceptionIsThrown(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> openedScreen = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // and:
    assertThat(openedScreen.screen().getWindow().getComponent("notExistingBtn"))
            .isNull();

    // when:
    final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> openedScreen.rawComponent(button("notExistingBtn")));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("Not found component with id 'notExistingBtn'");
  }

  @Test
  void given_componentIsNotMatchingRequestedType_when_retrieveComponent_then_exceptionIsThrown(UiTestAPI uiTestAPI) {

    // given:
    final StandardEditorTestAPI<Visit, VisitEdit> openedScreen = uiTestAPI.openStandardEditor(Visit.class, VisitEdit.class);

    // and:
    assertThat(openedScreen.screen().getWindow().getComponent("visitStartField"))
            .isInstanceOf(DateField.class);

    // expect:
    assertThrows(ClassCastException.class, () ->
            // visitStartField is not a combo box, but a date field
            openedScreen.component(comboBox("visitStartField"))
    );
  }

}
