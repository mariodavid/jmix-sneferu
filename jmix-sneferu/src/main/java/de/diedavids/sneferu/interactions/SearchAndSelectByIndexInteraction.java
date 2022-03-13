package de.diedavids.sneferu.interactions;

import de.diedavids.sneferu.Interaction;
import de.diedavids.sneferu.interactions.exception.SuggestionEntryNotFoundException;
import de.diedavids.sneferu.components.ComponentDescriptor;
import de.diedavids.sneferu.components.testapi.ComponentTestAPI;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import io.jmix.ui.component.SuggestionField;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class SearchAndSelectByIndexInteraction implements Interaction {

    private final ComponentDescriptor<? extends SuggestionField, ? extends ComponentTestAPI> componentDescriptor;
    private final String value;
    private final int entryToSelect;

    public <C extends SuggestionField<String>, F extends ComponentTestAPI<C>> SearchAndSelectByIndexInteraction(
            ComponentDescriptor<C, F> componentDescriptor,
            String searchTerm,
            int indexToSelect
    ) {
        this.componentDescriptor = componentDescriptor;
        this.value = searchTerm;
        this.entryToSelect = indexToSelect;
    }

    @Override
    public void execute(ScreenTestAPI screenTestAPI) {
        final SuggestionField suggestionField = (SuggestionField) screenTestAPI.component(componentDescriptor).rawComponent();

        tryToSelectResult(suggestionField, performSearch(suggestionField), entryToSelect);
    }

    @NotNull
    private List performSearch(SuggestionField suggestionField) {
        return suggestionField.getSearchExecutor().search(value, new HashMap<>());
    }

    private void tryToSelectResult(SuggestionField suggestionField, List searchResults, int indexToSelect) {
        try {
            final Object selectedEntry = searchResults.get(indexToSelect);
            suggestionField.setValue(selectedEntry);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new SuggestionEntryNotFoundException(
                    String.format("Suggestion results contain only %s results. Index %s is not found. Note: parameter 'indexToSelect' is a zero-based index.", searchResults.size(), indexToSelect),
                    e
            );
        }
    }
}
