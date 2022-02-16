package de.diedavids.sneferu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Annotation for a JUnit test case parameter.
 *
 * Using this Annotation, a ScreenTestAPI for a screen is injected into the test case.
 *
 * It will _not_ try to open the screen before the test case in the application.
 * Instead a reference will be returned, that on the first interaction will
 * try to fetch the screen if it was opened by the application
 *
 * For a StandardEditor the screen will be opened with `newEntity` mode (create case).
 *
 * Example usage:
 *
 *<pre>{@code
 * &#64;Test
 * public void myJunit5Test(
 *   &#64;StartScreen StandardLookupTestAPI&lt;Visit,VisitBrowse&gt; visitBrowse,
 *   &#64;SubsequentScreen StandardEditorTestAPI&lt;Visit, VisitEdit&gt; visitEdit
 *   ) {
 *
 *   // when: visitBrowse screen was opened through the test at the beginning of the test case
 *   visitBrowse.interact(click(button("createBtn")));
 *
 *   // and: through the click on "createBtn" the visitEdit screen is now opened in the application
 *   // therefore, it is not possible to interact with visitEdit.
 *   //
 *   // In case the screen has not been opened up until now, the interaction will fail
 *   // with a ScreenNotOpenException
 *
 *   visitEdit
 *         .interact(enter(dateField("visitStartField"), LocalDateTime.now()))
 * }
 *}</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SubsequentScreen {

}