package io.jmix.petclinic.screen.visit;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.visit.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SneferuUiTest(authenticatedUser = "admin", mainScreenId = "petclinic_MainScreen", screenBasePackages = "io.jmix.petclinic")
class FirstSneferuTest {

  @Test
  void openScreen_clickButton_verifyWhichScreenIsActive(UiTestAPI uiTestAPI) {

    // given:
    final StandardLookupTestAPI<Visit, VisitBrowse> visitBrowse = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

    // when:
    visitBrowse.interact(click(button("createBtn")));

    // then:
    assertThat(uiTestAPI.isActive(uiTestAPI.getOpenedEditorScreen(VisitEdit.class))).isTrue();
  }
}
