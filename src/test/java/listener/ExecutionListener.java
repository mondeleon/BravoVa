package listener;

import com.superbravo.driver.DriverManager;
import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        DriverManager.setUpAppiumSever();
        DriverManager.setUpDriver("emulator-5554");
        DriverManager.startApplication();
    }

    @Override
    public void onExecutionFinish() {
        DriverManager.tearDownAppiumSever();
    }
}
