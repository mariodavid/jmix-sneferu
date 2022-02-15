package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.InteractionWithOutcome;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.util.OperationResult;

public class CloseEditorInteraction implements InteractionWithOutcome<OperationResult, StandardEditorTestAPI> {

    @Override
    public OperationResult execute(StandardEditorTestAPI screenTestAPI) {
        return ((StandardEditor) screenTestAPI.screen()).closeWithCommit();
    }

}
