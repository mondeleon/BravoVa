package listener;


import com.superbravo.driver.DriverManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CloseAndOpenListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        DriverManager.restartApplication();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        DriverManager.restartApplication();
    }

    @Override
    public void onFinish(ITestContext result) {
        DriverManager.restartApplication();
    }
}
