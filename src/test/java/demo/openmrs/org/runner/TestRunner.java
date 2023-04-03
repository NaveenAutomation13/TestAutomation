package demo.openmrs.org.runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;
import com.mmed.logger.ApplicationLogger;
import com.mmed.utils.Utils;

import BaseCode.Base;
import BaseCode.ListenerTest;
import BaseCode.RetryTest;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;


	@CucumberOptions(
			features = "src/main/java/Features",
	      glue = {"com.OpenMRS.StepDefinition"},
	        plugin = { "pretty","com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
	        dryRun=false,
	        tags= {"@Login_to_application_and_do_Functions"}
	        )
	@Listeners (ListenerTest.class)
	public class TestRunner extends Base{
		
		
		private TestNGCucumberRunner testNGCucumberRunner;
		
		@BeforeMethod
		public void setUp() throws Exception{
			initialization();
		}
		
	    @BeforeClass(alwaysRun = true)
	    public void setUpClass() throws Exception {
	    	ApplicationLogger.info("In setUpClass");
	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	        ApplicationLogger.info("Out setUpClass");
	    }
	 
	    @Test(groups = "cucumber", description = "Runs Cucumber Features", dataProvider = "features", retryAnalyzer = RetryTest.class)
	    public void feature(CucumberFeatureWrapper cucumberFeature) {
	    	ApplicationLogger.info("In setUpClass");
	        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	        ApplicationLogger.info("Out setUpClass");
	    }
	    
	    @DataProvider
	    public Object[][] features() {
	        return testNGCucumberRunner.provideFeatures();
	    }
	 
	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() throws Exception {
	    	ApplicationLogger.info("In tearDownClass");
	    	Reporter.loadXMLConfig(Utils.getPropertyValue("reportConfigPath"));
	        testNGCucumberRunner.finish();
	        ApplicationLogger.info("Out tearDownClass");
	    }
	    
		 @AfterMethod
		 public void tearDown(){
		 driver.quit();
		 }
	}
