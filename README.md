[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![CI Pipeline](https://github.com/mariodavid/jmix-sneferu/actions/workflows/test.yml/badge.svg)

[![GitHub release](https://img.shields.io/github/release/mariodavid/jmix-sneferu.svg)](https://github.com/mariodavid/jmix-sneferu/releases/)
[![Example](https://img.shields.io/badge/example-jmix--sneferu--example-brightgreen)](https://github.com/mariodavid/jmix-sneferu-example)
[![Jmix Marketplace](https://img.shields.io/badge/marketplace-jmix--sneferu-orange)](https://www.jmix.io/marketplace/sneferu)


## Sneferu

Sneferu is a testing library to simplify integration testing for a Jmix application. It contains APIs that allows you to express interactions and verifications with UI screens via a high-level testing language. 

### Overview

Instead of spending too much time and money maintaining a selenium test suite, Sneferu is the way to have very good test coverage and quality assurance at a fraction of the cost.

Via its easy-to-read language you can create integration tests that are optimized for readability, because this is what matters most for keeping a test suite maintainable & cheap to operate.

Sneferu enables you to:
 
* verify any business logic in a Screen Controller
* ensure the correct linking between a Screen XML and its Controller counterpart
* verify correct display of any programmatic creation of Screen Components / Dialogs
* verify the data loading from the database through declarative data loading

What Sneferu _does not cover_:

* perform client-side JS based Vaadin UI logic that is executed _only_ in the browser (like showing the date picker popup where it is possible to select a particular date)
* verify rendering issues in the browser

![Sneferu Landscape](img/sneferu-overview.png)


#### Motivation & Background

Testing of a Jmix application on the web layer mainly consists of two extremes:

1. write a unit test for the business logic in the screen controllers
2. write a functional UI test that executes the application through the browser

Both of those extremes have their downsides. The first one requires mocking out every programmatic interaction with the Jmix UI interfaces. Also, unit tests do not cover any of the screen layout definitions or the data binding of a Screen.

Selenium-based UI testing, on the other hand, is much more black-box, slower, more brittle and overall harder to maintain. It can achieve a higher degree of confidence though. Since the application is exercised almost as the user does. But this additional confidence level is hard to achieve and maintain.

An example test case in Sneferu looks like this:

```java
public class CreateVisitTest {
    
    @Test
    void aVisitCanBeCreated_whenAllFieldsAreFilled(UiTestAPI uiTestAPI) {

        // given:
        final Pet pikachu = dataManager.create(Pet.class);
        pikachu.setName("Pikachu");
        pikachu.setIdentificationNumber("025");
        final Pet savedPikachu = dataManager.save(pikachu);

        // and:
        final StandardLookupTestAPI<Visit, VisitBrowse> visitBrowse = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);
        visitBrowse.interact(click(button("createBtn")));

        // when:
        final StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI.getOpenedEditorScreen(VisitEdit.class);

        OperationResult outcome = (OperationResult) visitEdit
                .interact(enter(dateField("visitStartField"), LocalDateTime.now()))
                .interact(enter(textField("descriptionField"), "Regular Visit"))
                .interact(select(comboBox("typeField"), VisitType.REGULAR_CHECKUP))
                .interact(select(entityComboBox("petField"), savedPikachu))
                .andThenGet(closeEditor());

        // then:
        assertThat(outcome).isEqualTo(OperationResult.success());

        // and:
        assertThat(uiTestAPI.isActive(visitEdit))
                .isFalse();

    }
}
```

### Getting Started

To use Sneferu, it is required to add the dependency to the Jmix project. In the `build.gradle` the following dependency has to be added to the web-module:

```groovy
dependencies {
    testImplementation 'de.diedavids.jmix.sneferu:jmix-sneferu:**SNEFERU-VERSION**'
}
```

Afterward, you can create your first web integration test:

```java
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;

import static de.diedavids.sneferu.ComponentDescriptors.*;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SneferuUiTest(authenticatedUser = "admin", mainScreenId = "petclinic_MainScreen", screenBasePackages = "io.jmix.petclinic")
class FirstSneferuTest {

    @Test
    void openScreen_clickButton_verifyWhichScreenIsActive(UiTestAPI uiTestAPI) {

        // given:
        final StandardLookupTestAPI<Visit, VisitBrowse> visitBrowse = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

        // when:
        visitBrowse.interact(click(button("createBtn")));

        // then:
        assertThat(uiTestAPI.isActive(uiTestAPI.getOpenedEditorScreen(VisitEdit.class))).isTrue();
    }
}
```

### Example Usage

There are a lot of example tests on how to use Sneferu: [jmix-sneferu-tests](jmix-sneferu-tests/src/test/java/io/jmix/petclinic).


### Documentation

The different concepts when using Sneferu are described below. It mainly consists of the following:

1. `UiTestAPI`
2. `ScreenTestAPI` & `ScreenObject`
3. `ComponentTestAPI`
4. `Interactions`

Let's go through them one by one.

### UI Test API

It is possible to get a reference to the `UiTestAPI` by injecting the dependency by adding a parameter to the test case: `UiTestAPI uiTestAPI`.

```java
class SneferuUiTestAPIInjectionTest {

  @Test
  public void uiTestApi_can_be_injected_to_work_with_the_screens(UiTestAPI uiTestAPI) {

      final StandardLookupTestAPI<Visit, VisitBrowse> visitBrowse = uiTestAPI.openStandardLookup(Visit.class, VisitBrowse.class);

      visitBrowse
        .interact(click(button("createBtn")));
    
    visitEdit
        .interact(enter(dateField("visitStartField"), LocalDateTime.now()));
    
    // ...

  }

}
```

### Screen Test API

The next concept of Sneferu allows you to interact with a particular Screen and is called `ScreenTestAPI`. It consists of two main use-cases:

1. apply interactions (see [Interactions](#Interactions)) on a particular screen
2. retrieve components of a screen to interact with those through its [Component Test API](#Component-Test-API)

To interact with the `ScreenTestAPI` an instance of that must be retrieved via the `UiTestAPI` as described above. Once those instances are available (`visitBrowse` and `visitEdit` in the following example), the API can be used to interact with a Screen via its Test API directly in the test case.

A usage example looks like this:

```java
class InteractionsTest {

    @Test
    void when_interactionIsPerformed_then_resultOfTheInteractionIsVisible(UiTestAPI uiTestAPI) {

        // when:
        visitBrowse.interact(
                // the click interaction happens here
                click(
                        // each interaction requires a component to interact on
                        button("createBtn")
                )
        );

        // then:
        final StandardEditorTestAPI<Visit, VisitEdit> visitEdit = uiTestAPI.getOpenedEditorScreen(VisitEdit.class);

        assertThat(uiTestAPI.isActive(visitEdit))
                .isTrue();
    }
}
```

More information on what Interactions are and how they can be used can be found in the corresponding [Interactions](#Interactions) section.

The following classes exists for the `ScreenTestAPI`, that should be used based on the screen you interact with:

* `StandardScreenTestAPI` for all Screens that directly extend `Screen`
* `StandardLookupTestAPI` for all Screens that extend `StandardLookup`
* `StandardEditorTestAPI` for all Screens that extend `StandardEditor`

### Screen Objects

An extension of the ScreenTestAPI is the concept of a Screen Object.

Instead of using the API directly through the TestScreenAPI, it is also possible to create a [Screen Object](https://martinfowler.com/bliki/PageObject.html) that represents the API of a particular Screen of the UI. This allows creating a dedicated abstraction between the test case and the screen that is under test.

##### Definition of a Screen Object (CustomerBrowseScreenObject)

To create a ScreenObject, a class needs to be created representing one screen (in this case `CustomerBrowse`). It furthermore needs to implement the interface `ScreenObject<T extends ScreenTestAPI>`. 

```java
public class CustomerBrowseScreenObject implements 
    ScreenObject<StandardLookupTestAPI<Customer, CustomerBrowse>> {

    private StandardLookupTestAPI<Customer, CustomerBrowse> delegate;
    private final TestUiEnvironment testUiEnvironment;

    // ...

    public CustomerBrowseScreenObject searchForCustomer(Customer customer) {
        delegate
                .component(suggestionField("quickSearch"))
                .search(customer);

        return this;
    }

    public CustomerBrowseScreenObject searchForCustomer(String customerName) {

        Metadata metadata = testUiEnvironment.getContainer().getBean(Metadata.class);

        Customer customer = metadata.create(Customer.class);
        customer.setName(customerName);

        return searchForCustomer(customer);
    }

    public boolean isActive() {
        return testUiEnvironment.getUiTestAPI().isActive(delegate);
    }

}
```

With the above definition it is now possible to use this higher level abstraction in the different test cases:

##### Test Case with Screen Object

The shown test case is using the API of the ScreenObject, which consists of:

* `void searchForCustomer(String customerName)`
* `void searchForCustomer(Customer customer)`
* `boolean isActive()`

```groovy
def "screens can be used through its Screen Object Test API"() {

    given: "a screen object can be created using a factory method"
    def customerBrowseScreenObject = CustomerBrowseScreenObject.of(
            environment
    )

    and:
    customerBrowseScreenObject
            .searchForCustomer("Bob Ross")

    and: "a screen object can also be created via its constructor"
    def customerEditScreenObject = new CustomerEditScreenObject(
            uiTestAPI.getOpenedEditorScreen(CustomerEdit),
            environment
    )

    when:
    customerEditScreenObject
            .changeNameTo("Meggy Simpson")


    then:
    customerEditScreenObject
        .isClosed()

    and:
    customerBrowseScreenObject
        .isActive()
}
```

This variant allows having a higher abstraction in the test case. It also decouples the test cases from the API of the Screen itself.

### Component Test API

The next concept of Sneferu is the Component Test API. This API is the same thing for a `Component` as the `ScreenTestAPI` is for a Jmix Screen. It is an abstraction on top of the Jmix `Component` APIs that is designed in the context of testing.

To use an instance of a Component Test API, it has to be created in the test case (or the Screen Object) via its factory method:

````groovy
import static de.diedavids.sneferu.ComponentDescriptors.*

// ...

customerBrowse
             .component(suggestionField("customerSearchField"))
             .search("Bob Ross")
````

`suggestionField` is a factory method that is statically imported from the `ComponentDescriptors` class. The parameter `customerSearchField` is the ID of the field used in the Screen XML descriptor.

It returns an instance of a subclass of `ComponentDescriptor`. In this case, it returns an instance of `SuggestionFieldComponentDescriptor` which is aware of the specifics of this component (like searching the value via its `search` method).


#### Support custom components

Sneferu currently does not support all the components of Jmix. Furthermore, if you use application-specific components or composite components, Sneferu also can not support them out-of-the-box.

Therefore, it is possible to create custom Component Descriptors, that represent the component in the testing scenario.

To support a new component, first a subclass of `ComponentTestAPI` needs to be created:

```java
public class SliderTestAPI extends GenericComponentTestAPI<Slider> {
    public SliderTestAPI(Slider component) {
        super(component);
    }

    public SliderTestAPI slideTo(int value) {
        rawComponent().setValue(value);
        return this;
    }
}
```

With that component-specific Test API in place, a `ComponentDescriptor` can be created that is responsible for this `SliderTestAPI`:

```java
public class SliderComponentDescriptor
        extends GenericComponentDescriptor<Slider, SliderTestAPI> {

    public SliderComponentDescriptor(String componentId) {
        super(Slider.class, componentId);
    }

    @Override
    public SliderTestAPI createTestAPI(Slider component) {
        return new SliderTestAPI(component);
    }
}
```

The last step is to create a factory method that creates a new `SliderComponentDescriptor`:

```java
public class ApplicationComponentDescriptors {

    /**
     * creates a SliderComponentDescriptor instance
     * @param id the id of the component as defined in the screen XMl descriptor
     * @return a SliderComponentDescriptor instance
     */
    public static SliderComponentDescriptor slider(String id) {
        return new SliderComponentDescriptor(id);
    }
}
```

With those three steps, the custom component like the `Slider` in this example can be supported in the integration tests of the application.

### Interactions

The last remaining concept of Sneferu is the Interactions APIs. Interactions are what bring the screens to life. An interaction reflects any action a user would normally do manually to trigger some behavior or validate some result.

An interaction usage looks like this:

```groovy
import static de.diedavids.sneferu.ComponentDescriptors.button
import static de.diedavids.sneferu.Interactions.click

def "Interaction Usage"() {

    given:
    def customerBrowse = environment.uiTestAPI
                            .openStandardLookup(Customer, CustomerBrowse)

    when: 'using the click interaction'
        customerBrowse
                .interact(
                        /* the click interaction */
                        click( 
                            /* the target of the click interaction */
                            button("createBtn")
                        )
                )
}
```

An interaction invokes a `ScreenTestAPI` instance (like the `customerBrowse` instance in this case). Then it normally gets a target to act upon via a parameter (like the Component Descriptor `button("createBtn")` instance).

The Interaction then goes ahead and performs the desired action upon the target component.

All interactions can be created via its Factory: `de.diedavids.sneferu.Interactions`. Normally, from a readability point of view, it once again makes sense to use static imports for them:

`import static de.diedavids.sneferu.Interactions.*`

There are two types of Interactions:

#### Chainable Interactions 
Chainable interactions are interactions that can be combined via the `ScreenTestAPI` and with that represent a list of steps that should be executed against a Screen.

Those interactions do not have an outcome that can be retrieved programmatically in the test case. Examples of that are `click`, `select`, `openTab` etc.

The Screen Test API is a fluent API that allows you to chain interactions:

```groovy
def "Interactions can be chained"() {
    when:
    customerWithTabsEdit
             .interact(openTab(tabSheet("contentTabSheet"), "ordersTab"))
             .andThen(select(lookupField("orderType"), OrderType.BIG))
}
```
There are several alias methods, that can be used to underline the readability:

* `interact(Interaction interaction)`
* `andThen(Interaction interaction)`

#### Terminating Interactions

Terminating Interactions, on the other hand, return a value. By returning that value the chain of interactions is closed. An example of that is `closeEditor`. This interaction does two things:

1. closing the editor;
2. returning the `OperationResult` object to the test case that is received from the StandardEditor instance.

In the test case the result can be retrieved and used for verification purposes:

```groovy
when:
OperationResult result = customerEdit
        .interact(enter(textField("nameField"), "Bob Ross"))
        .andThenGet(closeEditor())

then:
result == OperationResult.success()
```

Terminating Interactions can be invoked via one of the following alias methods in the `ScreenTestAPI`:

* `verify(InteractionWithOutcome interaction)`
* `get(InteractionWithOutcome interaction)`
* `interactAndGet(InteractionWithOutcome interaction)`
* `andThenGet(InteractionWithOutcome interaction)`
* `andThenVerify(InteractionWithOutcome interaction)`

#### Using different Interactions

Here is an example of how to use the two different types of interactions in a test case:

```groovy
import static de.diedavids.sneferu.ComponentDescriptors.*
import static de.diedavids.sneferu.Interactions.*

def "Chainable Interactions can be combined to perform a series of steps"() {

    when: 'an order is placed from a customer editor screen'
    OperationResult operationResult = 
    
         customerWithTabsEdit

             /* start of an interaction chain */
             .interact(openTab(tabSheet("contentTabSheet"), "ordersTab"))
             /* next interaction: select */
             .andThen(select(lookupField("orderType"), OrderType.BIG))
             /* next interaction: click */
             .andThen(click(button("placeOrderBtn")))
        
             /* terminating interaction: closeEditor */
             .andThenGet(closeEditor())
}
```

#### Custom Interactions

It is possible to define a custom Interaction that is not included in the Sneferu library (yet).

Taking the Slider example from above: the first step is to create a class defining the Interaction:

```java
import de.diedavids.sneferu.Interaction;
import de.diedavids.sneferu.screen.ScreenTestAPI;

public class SlideInteraction implements Interaction<ScreenTestAPI> {

    private final int value;
    private final SliderComponentDescriptor componentDescriptor;

    public SlideInteraction(SliderComponentDescriptor sliderComponentDescriptor, int value) {
        this.componentDescriptor = sliderComponentDescriptor;
        this.value = value;
    }

    @Override
    public void execute(ScreenTestAPI screenTestAPI) {
        ((Slider)screenTestAPI.component(componentDescriptor)
                .rawComponent())
                .setValue(value);
    }
}
```

After that, the second optional step is to define a factory method that instantiates a new `SlideInteraction` so that it can be easily used in the test case.

```java
public class ApplicationInteractions {

    public static <C extends Component, F extends ComponentTestAPI<C>> SlideInteraction slide(
            ComponentDescriptor<C, F> componentDescriptor,
            Object value
    ) {
        return new SlideInteraction(componentDescriptor, value);
    }
}
```


With that it is possible to use it directly in a test case:

```groovy
import static ApplicationInteractions.slide
import static ApplicationComponentDescriptors.slider

def "a slider component can be used through its custom interaction"() {

    when: 'an order is placed from a customer editor screen'
         customerEditor
             .interact(slide(slider("ageSlider"), 24))
}
```


## Migration from CUBA Sneferu

The following things have changed compared to the [CUBA platform Sneferu version](https://github.com/mariodavid/sneferu):

* create UI test by using `@SneferuUiTest` instead of `SneferuTestUiEnvironment`
* data loading is now part of a UI integration test
* CUBA platform components have been replaced with Jmix equivalents (`LookupField` --> `EntityComboBox`, etc.)
* `@NewEntity` annotation has been removed


### @SneferuUiTest

For CUBA it was needed to create a test environment object like this:

```java
public class CubaSneferuTest {
    
    @RegisterExtension
    public SneferuTestUiEnvironment environment =
            new SneferuTestUiEnvironment(PetclinicWebTestContainer.Common.INSTANCE)
                    .withScreenPackages(
                            "com.haulmont.cuba.web.app.main",
                            "com.haulmont.sample.petclinic.web"
                    )
                    .withUserLogin("admin")
                    .withMainScreen(MainScreen.class);
}
```

In Jmix, this has to be replaced with the `@SneferuUiTest` annotation:

```java
import de.diedavids.sneferu.SneferuUiTest;

@SpringBootTest
@SneferuUiTest(authenticatedUser = "admin", mainScreenId = "petclinic_MainScreen", screenBasePackages = "io.jmix.petclinic")
class JmixSneferuTest {
    // ...
}
```

Additionally `@SpringBootTest` has to be in place as well.

### Data Loading

In Jmix UI integration tests, the database is part of the test case. This is fundamentally different compared to how in CUBA web integration tests were executed. The main difference for the test cases is that test data needs to be put to the database instead of mocking the data.

It also means that the declarative data loading is part of the coverage of a test.

All of this is unrelated to Sneferu, it is just a change in how UI integration tests in Jmix are performed.

In the tests the DataManager dependency can be injected to interact with the database:

```java
class InteractWithDataManagerTest {

    @Autowired
    DataManager dataManager;
    
    @Test
    public void useDataManager(UiTestAPI uiTestAPI) {
        Pet newPet = dataManager.create(Pet.class);
        newPet.setName("Pikachu");
        newPet.setIdentificationNumber("025");
        return dataManager.save(newPet);
    }
    
    // ...
}
```

### CUBA Component replacement

In CUBA there were a couple of components, that are not present in Jmix anymore. These components are:

* TokenList
* PickerField
* LookupField

In Jmix the following new components are supported:

* ComboBox
* EntityComboBox
* TagField
* TagPicker