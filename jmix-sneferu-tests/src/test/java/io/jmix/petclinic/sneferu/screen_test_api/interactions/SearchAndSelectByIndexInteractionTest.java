package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.interactions.exception.SuggestionEntryNotFoundException;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.petclinic.entity.owner.Owner;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.screen.pet.pet.PetEdit;
import io.jmix.petclinic.sneferu.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static de.diedavids.sneferu.ComponentDescriptors.suggestionField;
import static de.diedavids.sneferu.Interactions.searchAndSelect;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class SearchAndSelectByIndexInteractionTest {

  @Autowired
  DataManager dataManager;
  @Autowired
  DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp() {
    databaseCleanup.removeAllEntities(Owner.class);
  }


  @Test
  void given_twoOwnersWithFirstNameA_when_interactionIsPerformed_then_correctEntryIsSelected(UiTestAPI uiTestAPI) {

    // given:
    final Owner ash = storeOwner("Ash");
    final Owner allister = storeOwner("Allister");
    final Owner misty = storeOwner("Misty");

    final List<Owner> ownersWithFirstNameA = dataManager.load(Owner.class)
            .condition(PropertyCondition.startsWith("firstName", "A"))
            .list();

    assertThat(ownersWithFirstNameA)
            .hasSize(2);

    // and:
    final StandardEditorTestAPI<Pet, PetEdit> openedScreen = openVisitEditor(uiTestAPI);

    // when:
    openedScreen.interact(searchAndSelect(suggestionField("ownerSuggestionField"), "A", 1));

    // then:
    ownerHasValue(openedScreen, ash);
  }

  @Test
  void given_resultsContainsOneEntry_when_selectionEntryNotPresent_then_exceptionIsThrown(UiTestAPI uiTestAPI) {

    // given:
    final Owner ash = storeOwner("Ash");
    // and:
    final StandardEditorTestAPI<Pet, PetEdit> openedScreen = openVisitEditor(uiTestAPI);

    // when:
    final SuggestionEntryNotFoundException exception = assertThrows(SuggestionEntryNotFoundException.class, () ->
            openedScreen.interact(searchAndSelect(suggestionField("ownerSuggestionField"), "Ash", 5))
    );

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("Suggestion results contain only 1 results. Index 5 is not found. Note: parameter 'indexToSelect' is a zero-based index.");
  }


  private StandardEditorTestAPI<Pet, PetEdit> openVisitEditor(UiTestAPI uiTestAPI) {
    return uiTestAPI.openStandardEditor(Pet.class, PetEdit.class);
  }

  private void ownerHasValue(StandardEditorTestAPI<Pet, PetEdit> openedScreen, Owner owner) {
    assertThat(openedScreen.rawComponent(suggestionField("ownerSuggestionField")).getValue())
            .isEqualTo(owner);
  }

  private Owner storeOwner(String name) {
    final Owner owner = dataManager.create(Owner.class);
    owner.setFirstName(name);
    owner.setAddress(name + " address");
    owner.setCity(name + " city");
    owner.setEmail(name + "@company.com");
    return dataManager.save(owner);
  }

}
