package io.jmix.petclinic.examples.screen_objects;

import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.UiTestAPI;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.pet.PetType;
import io.jmix.petclinic.sneferu.DatabaseCleanup;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SneferuUiTest(
        authenticatedUser = "admin",
        mainScreenId = "petclinic_MainScreen",
        screenBasePackages = "io.jmix.petclinic"
)
@SpringBootTest
class PetBrowseTest {

  @Autowired
  DataManager dataManager;
  @Autowired
  DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp() {
    databaseCleanup.removeAllEntities(Pet.class);
    databaseCleanup.removeAllEntities(PetType.class);
  }

  @Test
  void interactWithPetBrowse_throughPetBrowseScreenObject(UiTestAPI uiTestAPI) {

    // given:
    final PetType water = storePetType("Water");
    final PetType dragon = storePetType("Dragon");

    final Pet waterPet1 = storePetForType(water);
    final Pet waterPet2 = storePetForType(water);

    final Pet dragonPet1 = storePetForType(dragon);
    final Pet dragonPet2 = storePetForType(dragon);

    // and:
    final PetBrowseScreen petBrowse = PetBrowseScreen.open(uiTestAPI);

    assertThat(petBrowse.isActive())
            .isTrue();

    // and:
    assertThat(petBrowse.petCount())
            .isEqualTo(4);

    // when:
    petBrowse.filterByType(dragon);

    // then:
    assertThat(petBrowse.petCount())
            .isEqualTo(2);

    // and:
    assertThat(petBrowse.pets())
            .contains(dragonPet1, dragonPet2)
            .doesNotContain(waterPet1, waterPet2);
  }

  @NotNull
  private PetType storePetType(String name) {
    final PetType petType = dataManager.create(PetType.class);
    petType.setName(name);
    return dataManager.save(petType);
  }

  @NotNull
  private Pet storePetForType(PetType petType) {
    final Pet pikachu = dataManager.create(Pet.class);
    pikachu.setName("pet-" + UUID.randomUUID());
    pikachu.setIdentificationNumber(UUID.randomUUID().toString());
    pikachu.setType(petType);
    return dataManager.save(pikachu);
  }

}
