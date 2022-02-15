package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.Interaction;
import de.diedavids.sneferu.components.descriptor.TabsheetComponentDescriptor;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import io.jmix.ui.component.TabSheet;

public class OpenTabInteraction implements Interaction<ScreenTestAPI> {

    private String tabId;
    private final TabsheetComponentDescriptor componentDescriptor;
    private ScreenTestAPI screenTestAPI;

    public OpenTabInteraction(TabsheetComponentDescriptor tabSheet, String tabId) {
        this.componentDescriptor = tabSheet;
        this.tabId = tabId;
    }

    @Override
    public void execute(ScreenTestAPI screenTestAPI) {
        this.screenTestAPI = screenTestAPI;
        doExecute();
    }

    private void doExecute() {
        ((TabSheet)screenTestAPI.component(componentDescriptor)
                .rawComponent())
                .setSelectedTab(tabId);
    }
}
