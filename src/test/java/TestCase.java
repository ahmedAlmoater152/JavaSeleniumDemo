import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestCase {
    WebDriver driver;
    BatchInfo myBatch;
    Configuration suitConfig;
    EyesRunner testRunner;
    Eyes eyes;

    @BeforeClass
    public void beforeAll(){
        driver = new ChromeDriver();
        myBatch = new BatchInfo("My First Batch");
        suitConfig = new Configuration();
        suitConfig.setApiKey("MOz5NwvkTZxbY2NcHKXl8jOsenPHcKNIoq5chLKRbDI110");
        suitConfig.setBatch(myBatch);

        //desktop browsers
        suitConfig.addBrowser(800, 600, BrowserType.CHROME);
        suitConfig.addBrowser(1600, 1200, BrowserType.FIREFOX);
        suitConfig.addBrowser(1024, 768, BrowserType.SAFARI);

        //mobile devices
        suitConfig.addDeviceEmulation(DeviceName.Galaxy_S22, ScreenOrientation.PORTRAIT);
        suitConfig.addDeviceEmulation(DeviceName.iPhone_XS_Max, ScreenOrientation.PORTRAIT);

//      testRunner = new ClassicRunner();
        testRunner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
    }

    @BeforeMethod
    public void beforeEach(ITestResult result) {
        eyes = new Eyes();
        eyes.setConfiguration(suitConfig);
        eyes.open(driver,
                "my first tests",
                result.getMethod().getMethodName(),
                new RectangleSize(1000,600)
        );

    }
    @Test
    public void myTestCase() {
        driver.get("https://applitools.com/helloworld/?diff1");
        eyes.check(Target.window());
    }

    @Test
    public void exampleTestCase() {
        driver.get("https://example.com");
        eyes.check(Target.window());
    }

    @AfterMethod
    public void afterEach() {
        eyes.closeAsync();
    }

    @AfterClass
    public void afterAll(){
        driver.close();
        TestResultsSummary result = testRunner.getAllTestResults();
        System.out.println(result);
    }
}
