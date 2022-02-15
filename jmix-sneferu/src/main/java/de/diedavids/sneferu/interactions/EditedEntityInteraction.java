package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.InteractionWithOutcome;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.ui.screen.StandardEditor;

public class EditedEntityInteraction<E> implements InteractionWithOutcome<E, StandardEditorTestAPI<E, StandardEditor<E>>> {

    @Override
    public E execute(StandardEditorTestAPI screenTestAPI) {
        return (E) ((StandardEditor) screenTestAPI.screen()).getEditedEntity();
    }

}
