package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.InteractionWithOutcome;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.core.entity.EntityValues;
import io.jmix.ui.screen.StandardEditor;

public class EntityValueInteraction<T> implements InteractionWithOutcome<T, StandardEditorTestAPI> {

    private final String attribute;

    public EntityValueInteraction(String attribute, Class<T> type) {
        this.attribute = attribute;
    }

    @Override
    public T execute(StandardEditorTestAPI screenTestAPI) {
        return EntityValues.getValue(((StandardEditor) screenTestAPI.screen()).getEditedEntity(), attribute);
    }

}
