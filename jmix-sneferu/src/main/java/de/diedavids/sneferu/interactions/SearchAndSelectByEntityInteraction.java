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

public class SearchAndSelectByEntityInteraction implements Interaction {

    private final ComponentDescriptor<? extends SuggestionField, ? extends ComponentTestAPI> componentDescriptor;
    private final String value;
    private final Object entityToSelect;

    public <C extends SuggestionField<String>, F extends ComponentTestAPI<C>> SearchAndSelectByEntityInteraction(
            ComponentDescriptor<C, F> componentDescriptor,
            String searchTerm,
            Object entityToSelect
    ) {
        this.componentDescriptor = componentDescriptor;
        this.value = searchTerm;
        this.entityToSelect = entityToSelect;
    }

    @Override
    public void execute(ScreenTestAPI screenTestAPI) {
        final SuggestionField suggestionField = (SuggestionField) screenTestAPI.component(componentDescriptor).rawComponent();

        final List searchResults = performSearch(suggestionField);

        tryToSelectResult(suggestionField, searchResults, this.entityToSelect);
    }

    @NotNull
    private List performSearch(SuggestionField suggestionField) {
        return suggestionField.getSearchExecutor().search(value, new HashMap<>());
    }

    private void tryToSelectResult(SuggestionField suggestionField, List searchResults, Object entityToSelect) {

        final Object selectedEntity;
        try {
            selectedEntity = searchResults.stream()
                    .filter(result -> result.equals(entityToSelect))
                    .findFirst()
                    .orElseThrow(() ->
                            new SuggestionEntryNotFoundException(
                                    String.format("Suggestion results did not contain Entity %s", entityToSelect)
                    ));

            suggestionField.setValue(selectedEntity);
        } catch (Throwable e) {
            throw new SuggestionEntryNotFoundException(
                    String.format("Suggestion results did not contain Entity '%s'", entityToSelect),
                    e
            );
        }

    }
}
