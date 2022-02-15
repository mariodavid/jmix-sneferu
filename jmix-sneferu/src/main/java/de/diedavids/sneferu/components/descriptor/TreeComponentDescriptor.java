package de.diedavids.sneferu.components.descriptor;


import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;
import io.jmix.ui.component.Tree;

public class TreeComponentDescriptor
        extends GenericComponentDescriptor<Tree, GenericComponentTestAPI<Tree>> {

    public TreeComponentDescriptor(String componentId) {
        super(Tree.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<Tree> createTestAPI(Tree component) {
        return new GenericComponentTestAPI<>(component);
    }
}
