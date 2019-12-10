package listeners;

import listeners.core.APIException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import utilites.TestData;

import java.io.IOException;
import java.lang.reflect.Method;

public class TestRailListeners implements ITestListener {

    // send results in TestRail if set to true
    private static boolean postResultsToTestRail = true;
    private static int testRailTestRunId;

    //runs any time a test starts
    public void onTestStart(ITestResult result) {
        //
    }

    // runs any time a test passes
    public void onTestSuccess(ITestResult result) {
        if (postResultsToTestRail) {
            ITestNGMethod m = result.getMethod();
            String testRailComment = "Automated TestNG Test - PASS; \n\nTest method name = " + m.getMethodName() + "\nBrowser name = " + m.getXmlTest().getParameter("browserName");
            try {
                for (int testCaseId : returnTestCaseId(result)) {
                    TestRailAPI.addResult(1, testRailComment, testRailTestRunId, testCaseId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // runs any time a test fails
    public void onTestFailure(ITestResult result) {
        String errorMessage = result.getThrowable().toString();
        if (postResultsToTestRail) {
            ITestNGMethod m = result.getMethod();

            String testRailComment = "Automated TestNG Test - FAIL\n\nTest method name = " + m.getMethodName() + "\nBrowser name = " +
                    m.getXmlTest().getParameter("browserName") + "\n\nFailure Exception = " + errorMessage;

            try {
                for (int testCaseId : returnTestCaseId(result)) {
                    TestRailAPI.addResult(5, testRailComment, testRailTestRunId, testCaseId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onTestSkipped(ITestResult result) {
        //
    }

    public void onFinish(ITestContext result) {
        //
    }

    public void onStart(ITestContext result) {
        //
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //
    }

    private int[] returnTestCaseId(ITestResult result) {
        ITestNGMethod testNGMethod = result.getMethod();
        Method method = testNGMethod.getConstructorOrMethod().getMethod();
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);
        int[] testCaseIds;
        try {
            testCaseIds = testRailAnnotation.testCaseId();
        } catch (Exception ex) {
            return new int[]{};
        }
        return testCaseIds;
    }

    protected static void createTestRun() {
        try {
            testRailTestRunId = Integer.parseInt(TestRailAPI.addRun(TestData.PROJECT_ID.getIntegerProperties()).get("id").toString());
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    protected static void createTestRun(int[] testIDs) {
        try {
            testRailTestRunId = Integer.parseInt(TestRailAPI.addRun(TestData.PROJECT_ID.getIntegerProperties(), testIDs).get("id").toString());
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }
}
