package io.jmix.petclinic.screen.owner;

import io.jmix.petclinic.entity.owner.Owner;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.MessageBundle;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("petclinic_Owner.browse")
@UiDescriptor("owner-browse.xml")
@LookupComponent("ownersTable")
@Route(value = "owners")
public class OwnerBrowse extends StandardLookup<Owner> {
    @Autowired
    private GroupTable<Owner> ownersTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe("ownersTable.showPets")
    public void onOwnersTableShowPets(Action.ActionPerformedEvent event) {
        final Owner owner = ownersTable.getSingleSelected();

        if (owner.getPets().size() == 0) {
            notifications.create(Notifications.NotificationType.WARNING).withCaption(messageBundle.getMessage("noPetsPresent")).show();
        }
    }

}