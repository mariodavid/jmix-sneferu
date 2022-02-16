package io.jmix.petclinic.sneferu.screen_test_api.interactions;

import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.components.descriptor.DataGridComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TableComponentDescriptor;
import de.diedavids.sneferu.environment.SneferuUiTest;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.pet.PetType;
import io.jmix.petclinic.entity.veterinarian.Veterinarian;
import io.jmix.petclinic.screen.pet.pettype.PetTypeBrowse;
import io.jmix.petclinic.screen.veterinarian.veterinarian.VeterinarianBrowse;
import io.jmix.petclinic.sneferu.DatabaseCleanup;
import io.jmix.ui.Screens;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

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
class SelectInListInteractionTest {

  @Autowired
  DataManager dataManager;
  @Autowired
  DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp() {
    databaseCleanup.removeAllEntities(Veterinarian.class);
    databaseCleanup.removeAllEntities(PetType.class);
  }

  @Test
  void when_interactionIsPerformedWithSingleSelection_then_valueIsSet(UiTestAPI uiTestAPI) {

    // given:
    storeVet("Oak");
    storeVet("Elm");
    storeVet("Juniper");
    storeVet("Rowan");
    storeVet("Sycamore");

    // and:
    final Veterinarian birch = storeVet("Birch");

    // and:
    final StandardLookupTestAPI<Veterinarian, VeterinarianBrowse> openedScreen = uiTestAPI.openStandardLookup(Veterinarian.class, VeterinarianBrowse.class);

    final DataGridComponentDescriptor dataGrid = dataGrid("veterinariansTable");
    assertThat(openedScreen.rawComponent(dataGrid).isMultiSelect()).isFalse();

    // when:
    openedScreen.interact(selectInList(dataGrid, birch));

    // then:
    final Veterinarian selectedVetInTable = (Veterinarian) openedScreen.rawComponent(dataGrid).getSingleSelected();

    // and:
    assertThat(selectedVetInTable)
            .isEqualTo(birch);
  }

  @Test
  void when_multipleSelectionIsTriedWithSingleSelectionTable_then_exceptionIsThrown(UiTestAPI uiTestAPI) {

    // given:
    storeVet("Elm");
    storeVet("Rowan");
    storeVet("Sycamore");

    // and:
    final Veterinarian oak = storeVet("Oak");
    final Veterinarian juniper = storeVet("Juniper");
    final Veterinarian birch = storeVet("Birch");

    // and:
    final StandardLookupTestAPI<Veterinarian, VeterinarianBrowse> openedScreen = uiTestAPI.openStandardLookup(Veterinarian.class, VeterinarianBrowse.class);

    final DataGridComponentDescriptor dataGrid = dataGrid("veterinariansTable");
    assertThat(openedScreen.rawComponent(dataGrid).isMultiSelect()).isFalse();

    // when:
    final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> openedScreen.interact(selectInList(dataGrid, Lists.list(birch, oak, juniper))));

    // then:
    assertThat(exception.getMessage())
            .isEqualTo("Cannot select multiple values for component with multiselect='false'");
  }

  @Test
  void when_interactionIsPerformedWithMultiSelection_then_allValuesAreSet(UiTestAPI uiTestAPI) {

    // given:
    final PetType ice = storePetType("Ice");
    final PetType water = storePetType("Water");
    final PetType dragon = storePetType("Dragon");

    // and:
    final StandardLookupTestAPI<PetType, PetTypeBrowse> openedScreen = uiTestAPI.openStandardLookup(PetType.class, PetTypeBrowse.class);

    final TableComponentDescriptor table = table("table");
    assertThat(openedScreen.rawComponent(table).isMultiSelect()).isTrue();

    // when:
    openedScreen.interact(selectInList(table, Lists.list(water, dragon)));

    // then:
    final Set<PetType> selectedPetTypesInTable = openedScreen.rawComponent(table).getSelected();

    // and:
    assertThat(selectedPetTypesInTable)
            .contains(water, dragon);
  }

  private Veterinarian storeVet(String name) {
    Veterinarian entity = dataManager.create(Veterinarian.class);
    entity.setFirstName("Professor");
    entity.setLastName(name);
    return dataManager.save(entity);
  }

  private PetType storePetType(String name) {
    PetType entity = dataManager.create(PetType.class);
    entity.setName(name);
    return dataManager.save(entity);
  }

}
