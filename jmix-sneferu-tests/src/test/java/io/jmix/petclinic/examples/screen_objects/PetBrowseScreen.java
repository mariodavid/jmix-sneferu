package io.jmix.petclinic.examples.screen_objects;

import de.diedavids.sneferu.ScreenObject;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.pet.PetType;
import io.jmix.petclinic.screen.pet.pet.PetBrowse;
import io.jmix.ui.component.Table;

import java.util.Collection;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.*;

public class PetBrowseScreen implements ScreenObject<StandardLookupTestAPI<Pet, PetBrowse>> {

    private StandardLookupTestAPI<Pet, PetBrowse> delegate;
    private UiTestAPI uiTestAPI;

    @Override
    public StandardLookupTestAPI<Pet, PetBrowse> delegate() {
        return delegate;
    }

    private PetBrowseScreen(UiTestAPI uiTestAPI) {
        this.uiTestAPI = uiTestAPI;
        this.delegate = uiTestAPI.openStandardLookup(Pet.class, PetBrowse.class);
    }

    /**
     * opens the PetBrowse screen in order to interact with it
     * @param uiTestAPI the UITestAPI instance to open the screen from
     * @return an instance of the PetBrowseScreen
     */
    public static PetBrowseScreen open(UiTestAPI uiTestAPI) {
        return new PetBrowseScreen(uiTestAPI);
    }


    /**
     * filters the pets table by the given pet type
     * @param petType the pet type to filter for
     */
    public void filterByType(PetType petType) {
        delegate.interact(enter(comboBox("typeFilterField"), petType));
    }

    public int petCount() {
        return pets().size();
    }

    public Collection<Pet> pets() {
        return tableComponent().getItems().getItems();
    }

    public boolean isActive() {
        return uiTestAPI.isActive(delegate);
    }

    private Table<Pet> tableComponent() {
        return delegate.component(table("petsTable"))
                .rawComponent();
    }
}