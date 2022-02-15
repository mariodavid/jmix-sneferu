package de.diedavids.sneferu;


import de.diedavids.sneferu.components.descriptor.*;
import de.diedavids.sneferu.components.descriptor.AccordionComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.ButtonComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.CalendarComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.CheckBoxComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.ColorPickerComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.DataGridComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.DateFieldComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.GroupTableComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.ImageComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.SuggestionFieldComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TableComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TabsheetComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TextFieldComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TextInputFieldComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TreeComponentDescriptor;
import de.diedavids.sneferu.components.descriptor.TreeDataGridComponentDescriptor;

/**
 * Factory methods for all Component Descriptors
 *
 * Normally should be included via a static import like:
 *
 * &gt; import static de.diedavids.sneferu.ComponentDescriptors.*
 */
public class ComponentDescriptors {

    /**
     * creates a {@link GroupTableComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a GroupTableComponentDescriptor instance
     */
    public static GroupTableComponentDescriptor groupTable(String id) {
        return new GroupTableComponentDescriptor(id);
    }

    /**
     * creates a {@link TableComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TableComponentDescriptor instance
     */
    public static TableComponentDescriptor table(String id) {
        return new TableComponentDescriptor(id);
    }

    /**
     * creates a {@link DataGridComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a DataGridComponentDescriptor instance
     */
    public static DataGridComponentDescriptor dataGrid(String id) {
        return new DataGridComponentDescriptor(id);
    }


    /**
     * creates a {@link TreeDataGridComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TreeDataGridComponentDescriptor instance
     */
    public static TreeDataGridComponentDescriptor treeDataGrid(String id) {
        return new TreeDataGridComponentDescriptor(id);
    }

    /**
     * creates a {@link CalendarComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a CalendarComponentDescriptor instance
     */
    public static CalendarComponentDescriptor calendar(String id) {
        return new CalendarComponentDescriptor(id);
    }


    /**
     * creates a {@link TextFieldComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TextFieldComponentDescriptor instance
     */
    public static TextFieldComponentDescriptor textField(String id) {
        return new TextFieldComponentDescriptor(id);
    }

    /**
     * creates a {@link TextInputFieldComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TextInputFieldComponentDescriptor instance
     */
    public static TextInputFieldComponentDescriptor textInputField(String id) {
        return new TextInputFieldComponentDescriptor(id);
    }


    /**
     * creates a {@link ComboBoxComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a ComboBoxComponentDescriptor instance
     */
    public static ComboBoxComponentDescriptor comboBox(String id) {
        return new ComboBoxComponentDescriptor(id);
    }

    /**
     * creates a {@link EntityComboBoxComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a PickerFieldComponentDescriptor instance
     */
    public static EntityComboBoxComponentDescriptor entityComboBox(String id) {
        return new EntityComboBoxComponentDescriptor(id);
    }

    /**
     * creates a {@link TagFieldComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TagFieldComponentDescriptor instance
     */
    public static TagFieldComponentDescriptor tagField(String id) {
        return new TagFieldComponentDescriptor(id);
    }
    /**
     * creates a {@link TagPickerComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TagPickerComponentDescriptor instance
     */
    public static TagPickerComponentDescriptor tagPicker(String id) {
        return new TagPickerComponentDescriptor(id);
    }


    /**
     * creates a {@link DateFieldComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a DateFieldComponentDescriptor instance
     */
    public static DateFieldComponentDescriptor dateField(String id) {
        return new DateFieldComponentDescriptor(id);
    }


    /**
     * creates a {@link CheckBoxComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a CheckBoxComponentDescriptor instance
     */
    public static CheckBoxComponentDescriptor checkBox(String id) {
        return new CheckBoxComponentDescriptor(id);
    }


    /**
     * creates a {@link SuggestionFieldComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a SuggestionFieldComponentDescriptor instance
     */
    public static SuggestionFieldComponentDescriptor suggestionField(String id) {
        return new SuggestionFieldComponentDescriptor(id);
    }


    /**
     * creates a {@link ButtonComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a ButtonComponentDescriptor instance
     */
    public static ButtonComponentDescriptor button(String id) {
        return new ButtonComponentDescriptor(id);
    }


    /**
     * creates a {@link ImageComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a ImageComponentDescriptor instance
     */
    public static ImageComponentDescriptor image(String id) {
        return new ImageComponentDescriptor(id);
    }


    /**
     * creates a {@link TabsheetComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TabsheetComponentDescriptor instance
     */
    public static TabsheetComponentDescriptor tabSheet(String id) {
        return new TabsheetComponentDescriptor(id);
    }


    /**
     * creates a {@link AccordionComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a AccordionComponentDescriptor instance
     */
    public static AccordionComponentDescriptor accordion(String id) {
        return new AccordionComponentDescriptor(id);
    }


    /**
     * creates a {@link TreeComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a TreeComponentDescriptor instance
     */
    public static TreeComponentDescriptor tree(String id) {
        return new TreeComponentDescriptor(id);
    }


    /**
     * creates a {@link ColorPickerComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a ColorPickerComponentDescriptor instance
     */
    public static ColorPickerComponentDescriptor colorPicker(String id) {
        return new ColorPickerComponentDescriptor(id);
    }

    /**
     * creates a {@link SideMenuComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a SideMenuComponentDescriptor instance
     */
    public static SideMenuComponentDescriptor sideMenu(String id) {
        return new SideMenuComponentDescriptor(id);
    }


    /**
     * creates a {@link CurrencyFieldComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a CurrencyFieldComponentDescriptor instance
     */
    public static CurrencyFieldComponentDescriptor currencyField(String id) {
        return new CurrencyFieldComponentDescriptor(id);
    }

}
