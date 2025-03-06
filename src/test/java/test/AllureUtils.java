package test;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class AllureUtils {

    public static void failTestCase(){
        String testCaseId = Allure.getLifecycle().getCurrentTestCase().orElse(null);

        if (testCaseId != null) {
            Allure.getLifecycle().stopStep();
            Allure.getLifecycle().stopTestCase(testCaseId);

            Allure.getLifecycle().updateTestCase(testCaseId, testResult -> testResult.setStatus(Status.FAILED));
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }
    }
}
