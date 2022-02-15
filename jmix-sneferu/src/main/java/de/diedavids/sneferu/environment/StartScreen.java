package de.diedavids.sneferu.environment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for a JUnit test case parameter.
 *
 * Using this Annotation, a ScreenTestAPI for a screen is injected into the test case.
 *
 * It will open the screen before the test case in the application.
 *
 * For a StandardEditor the screen will be opened with `newEntity` mode (create case).
 *
 * Example usage:
 *
 *<pre>{@code
 * &#64;Test
 * public void myJunit5Test(
 *   &#64;StartScreen StandardLookupTestAPI&lt;Visit,VisitBrowse&gt; visitBrowse
 *   ) {
 *
 *   visitBrowse.interact(click(button("createBtn")));
 *
 * }
 *}</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface StartScreen {

}