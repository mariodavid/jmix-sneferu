package io.jmix.petclinic.screen.pet.pet;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.petclinic.entity.owner.Owner;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.pet.PetType;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.Slider;
import io.jmix.ui.component.TextField;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.FrameOwner;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.MessageBundle;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;


@UiController("petclinic_Pet.browse")
@UiDescriptor("pet-browse.xml")
@LookupComponent("petsTable")
@Route(value = "pets")
public class PetBrowse extends StandardLookup<Pet> {

    @Autowired
    protected Slider birthdateFilterField;

    @Autowired
    protected TextField<String> idFilterField;

    @Autowired
    protected EntityComboBox<Owner> ownerFilterField;

    @Autowired
    protected EntityComboBox<PetType> typeFilterField;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;


    @Subscribe("petsTable.clearFilter")
    protected void onPetsTableClearFilter(Action.ActionPerformedEvent event) {
        typeFilterField.setValue(null);
        ownerFilterField.setValue(null);
        idFilterField.setValue(null);
        birthdateFilterField.setValue(null);
    }

    @Subscribe("petsTable.openPetById")
    public void onPetsTableOpenPetById(Action.ActionPerformedEvent event) {

        FrameOwner frameOwner = this;
        dialogs.createInputDialog(frameOwner)
                .withParameter(InputParameter.stringParameter("id").withRequired(true).withCaption(messageBundle.getMessage("id")))
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        final String id = inputDialogCloseEvent.getValue("id");

                        if (StringUtils.hasText(id)){
                            final Optional<Pet> pet = dataManager.load(Pet.class).query("e.identificationNumber = ?1", id).optional();

                            if (pet.isPresent()) {
                                screenBuilders.editor(Pet.class, frameOwner)
                                        .editEntity(pet.get())
                                        .withOpenMode(OpenMode.DIALOG)
                                        .show();
                            }
                            else {
                                noPetFoundForIdWarning(id);
                            }
                        }
                        else {
                            noPetFoundForIdWarning(id);
                        }
                    }
                })
                .show();
    }

    private void noPetFoundForIdWarning(String id) {
        notifications.create(Notifications.NotificationType.WARNING)
                .withCaption(messageBundle.formatMessage("noPetFound", id))
                .show();
    }

}