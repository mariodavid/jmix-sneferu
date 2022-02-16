package de.diedavids.sneferu.extension;

import de.diedavids.sneferu.JmixUiTestAPI;
import de.diedavids.sneferu.SneferuUiTest;
import de.diedavids.sneferu.StartScreen;
import de.diedavids.sneferu.SubsequentScreen;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.screen.StandardEditorTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import de.diedavids.sneferu.screen.StandardScreenTestAPI;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.testassist.junit.JmixUiTestExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.platform.commons.support.AnnotationSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class SneferuUiTestExtension extends JmixUiTestExtension {

    private UiTestAPI uiTestAPI;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        boolean closeAllScreensBeforeRun = false;

        Optional<SneferuUiTest> jmixUiTestAnnotationOpt = AnnotationSupport.findAnnotation(context.getTestClass(), SneferuUiTest.class);
        if (jmixUiTestAnnotationOpt.isPresent()) {
            authenticatedUser = jmixUiTestAnnotationOpt.get().authenticatedUser();
            screenBasePackages = jmixUiTestAnnotationOpt.get().screenBasePackages();
            mainScreenId = jmixUiTestAnnotationOpt.get().mainScreenId();

            closeAllScreensBeforeRun = jmixUiTestAnnotationOpt.get().closeAllScreensBeforeRun();
        }

        super.beforeEach(context);

        ApplicationContext springContext = SpringExtension.getApplicationContext(context);

        uiTestAPI = new JmixUiTestAPI(
                springContext.getBean(ScreenBuilders.class),
                getAppUI(context).getScreens()
        );

        if (closeAllScreensBeforeRun) {
            uiTestAPI.closeAllScreens();
        }
    }

    /**
     * returns an instance of the UiTestAPI
     *
     * @return the UiTestAPI instance
     */
    public UiTestAPI getUiTestAPI() {
        return uiTestAPI;
    }

    @Override
    public boolean supportsParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return isScreenParameter(parameterContext) || isUiTestApiParameter(parameterContext) || super.supportsParameter(parameterContext, extensionContext);
    }

    private boolean isUiTestApiParameter(ParameterContext parameterContext) {
        return UiTestAPI.class.equals(parameterContext.getParameter().getType());
    }

    private boolean isScreenParameter(ParameterContext parameterContext) {
        return isStartScreenParameter(parameterContext) ||
                isSubsequentScreenParameter(parameterContext);
    }

    private boolean isSubsequentScreenParameter(ParameterContext parameterContext) {
        return parameterContext.isAnnotated(SubsequentScreen.class);
    }

    private boolean isStartScreenParameter(ParameterContext parameterContext) {
        return parameterContext.isAnnotated(StartScreen.class);
    }

    @Override
    public Object resolveParameter(
        ParameterContext parameterContext,
        ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        try {
            return super.resolveParameter(parameterContext, extensionContext);
        }
        catch (ParameterResolutionException e) {
            if (isScreenParameter(parameterContext)) {
                return getScreenValue(parameterContext);
            }
            else if (isUiTestApiParameter(parameterContext)) {
                return getUiTestAPI();
            }

            else {
                return cannotHandle();
            }
        }
    }

    private Object cannotHandle() {
        throw new ParameterResolutionException("Unsupported Parameter to resolve");
    }


    private Object getScreenValue(
        ParameterContext parameterContext
    ) {

        if (isStartScreenParameter(parameterContext)) {
            return getStartScreenParameter(parameterContext);
        }
        else if (isSubsequentScreenParameter(parameterContext)) {
            return getSubsequentScreenParameter(parameterContext);
        }
        else {
            return cannotHandle();
        }
    }

    private Object getSubsequentScreenParameter(
        ParameterContext parameterContext
    ) {

        final Parameter parameter = parameterContext.getParameter();
        final Class<?> parameterType = parameter.getType();
        Type type = parameter.getParameterizedType();
        if (!(type instanceof ParameterizedType)) {
            return cannotHandle();
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();

        if (StandardEditorTestAPI.class.equals(parameterType)) {
            if (actualTypeArguments.length != 2) {
                return cannotHandle();
            }

            return getUiTestAPI().getLazyOpenedEditorScreen((Class) actualTypeArguments[1]);
        }

        else {
            return cannotHandle();
        }
    }

    private Object getStartScreenParameter(
        ParameterContext parameterContext
    ) {

        final Parameter parameter = parameterContext.getParameter();
        final Class<?> parameterType = parameter.getType();
        Type type = parameter.getParameterizedType();
        if (!(type instanceof ParameterizedType)) {
            return cannotHandle();
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();


        if (StandardLookupTestAPI.class.equals(parameterType)) {
            if (actualTypeArguments.length != 2) {
                return cannotHandle();
            }
            return getUiTestAPI().openStandardLookup(
                (Class) actualTypeArguments[0],
                (Class) actualTypeArguments[1]
            );
        }
        else if (StandardEditorTestAPI.class.equals(parameterType)) {
            if (actualTypeArguments.length != 2) {
                return cannotHandle();
            }
            return getUiTestAPI().openStandardEditor(
                (Class) actualTypeArguments[0],
                (Class) actualTypeArguments[1]
            );
        }
        else if (StandardScreenTestAPI.class.equals(parameterType)) {
            return getUiTestAPI().openStandardScreen((Class)actualTypeArguments[0]);
        }
        else {
            return cannotHandle();
        }
    }
}