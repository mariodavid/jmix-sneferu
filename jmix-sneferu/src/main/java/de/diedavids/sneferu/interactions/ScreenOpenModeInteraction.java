package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.InteractionWithOutcome;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import io.jmix.ui.screen.OpenMode;

public class ScreenOpenModeInteraction implements InteractionWithOutcome<OpenMode, ScreenTestAPI> {
    @Override
    public OpenMode execute(ScreenTestAPI screenTestAPI) {
        return screenTestAPI.screen().getWindow().getContext().getOpenMode();
    }
}
