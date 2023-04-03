
	package BaseCode;

	import java.awt.AWTException;
	import java.io.File;
	import java.io.IOException;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Random;
	import java.util.concurrent.TimeUnit;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.Alert;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.NoSuchElementException;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.edge.EdgeOptions;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.firefox.FirefoxOptions;
	import org.openqa.selenium.ie.InternetExplorerDriver;
	import org.openqa.selenium.ie.InternetExplorerOptions;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.remote.CapabilityType;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.ITestContext;
	import org.testng.ITestResult;

	import com.cucumber.listener.Reporter;
	import com.mmed.logger.ApplicationLogger;
	import com.mmed.utils.Utils;

	import io.github.bonigarcia.wdm.WebDriverManager;

	public class Base {

		public static WebDriver driver;
		public static WebDriverWait wait;
		public static final long pageLoad = 60;
		public static final long PAGE_LOAD_TIMEOUT = 30;
		public static final long IMPLICIT_WAIT = 5;
		private static String homeWindow = null;
		public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

		public static void initialization() throws IOException {
			String browserName = Utils.getPropertyValue("browserName");
			String browserVersion = Utils.getPropertyValue("browserVersion");

			if (browserName.equals("chrome")) {

				WebDriverManager.chromedriver().browserVersion(browserVersion).setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.setAcceptInsecureCerts(true);
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.media_stream_camera", 2);
				options.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();

			} else if (browserName.equals("firefox")) {
				WebDriverManager.firefoxdriver().browserVersion(browserVersion).setup();
				FirefoxOptions options = new FirefoxOptions();
				driver = new FirefoxDriver(options);
			} else if (browserName.equals("edge")) {

				WebDriverManager.edgedriver().browserVersion(browserVersion).setup();
				EdgeOptions options = new EdgeOptions();
				options.setCapability("--disable-notifications", true);
				options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.media_stream_camera", 2);
				options.setCapability("prefs", prefs);
				driver = new EdgeDriver(options);
				driver.manage().window().maximize();

			} else if (browserName.equals("ie")) {
				WebDriverManager.iedriver().browserVersion(browserVersion).setup();
				InternetExplorerOptions options = new InternetExplorerOptions();
				driver = new InternetExplorerDriver(options);
			}
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		}

		public static void takeScreenshot() throws Exception {
			String filePath = CURRENT_DIRECTORY + "\\" + Utils.getPropertyValue("outputData") + "\\" + "testing1.png";
			File s = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(s, new File(filePath));
		}

		public static void takeScreenshotOfWebElement(WebElement element) throws Exception {
			String filePath = CURRENT_DIRECTORY + "\\" + Utils.getPropertyValue("outputData") + "\\" + "testing2.png";
			File f = element.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File(filePath));
		}

		public static String setText(WebElement element, String value) {
			element.clear();
			element.sendKeys(value);
			return value;
		}

		/**
		 * Verifying the visibility of element only for assert conditions
		 */

		public static boolean isElementPresent(WebElement element) {
			boolean elementPresent = false;
			waitForElementVisibility(element);
			if (element.isDisplayed()) {
				elementPresent = true;
			}
			return elementPresent;
		}

		public static boolean elementToBevisible(WebDriver driver, int time, WebElement element) {
			boolean flag = false;
			try {
				wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOf(element));
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}

		/**
		 * Verifying the visibility of element only for assert conditions
		 */

		public static boolean isElementNotPresent(WebElement element) {
			boolean elementNotPresent = true;
			if (element.isDisplayed()) {
				elementNotPresent = false;
			}
			return elementNotPresent;
		}

		/**
		 * Method to click the element
		 * 
		 * @param object
		 * @throws InterruptedException
		 */
		public static void clicks(WebElement ele) throws InterruptedException {
			Thread.sleep(1000);
			ele.click();
		}

		/******************
		 * getting the text from non editable field
		 *********************/

		public static String getText(WebElement element) {
			String text = null;
			waitForElementVisibility(element);
			if (element.getText() != null) {
				text = element.getText();
			}
			return text;
		}

		/**
		 * Method to get the value from textbox
		 * 
		 * @param element
		 * @return
		 */
		public static String getValue(WebElement element) {
			String value = null;
			waitForElementVisibility(element);
			if (element.getAttribute("value") != null) {
				value = element.getAttribute("value");
			}
			return value;
		}

		/**
		 * Method to select the option from dropdown by value
		 */
		public static void selectByValue(WebElement element, String value) {
			Select obj_select = new Select(element);
			obj_select.selectByValue(value);
		}

		/**
		 * Method to select the option from drop down by visible text
		 */
		public static void selectByText(WebElement element, String Text) {
			Select obj_select = new Select(element);
			obj_select.selectByVisibleText(Text);
		}

		/**
		 * Method to select the option from dropdown by index
		 */
		public static void selectByIndex(WebElement class1, int index) {
			Select obj_select = new Select(class1);
			obj_select.selectByIndex(index);
		}

		/**
		 * Method to print all the selected options from dropdown
		 * @return 
		 */
		public static String getAllSelectedOptions(WebElement element) {
			Select obj_select = new Select(element);
			List<WebElement> web = obj_select.getAllSelectedOptions();
			for (WebElement x : web) {
				ApplicationLogger.info(x.getText());
				// System.out.println(x.getText());
			}
			return homeWindow;

		}

		/**
		 * To pause execution until get expected elements visibility
		 * 
		 * @param element
		 */
		public static void waitForElementVisibility(WebElement element) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
		}

		public static void waitForElementClickable(WebElement element) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}

		public static void waitForTitle(String title) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.titleContains(title));
		}

		public static void titleIs(String title) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.titleIs(title));
		}

		public static void presenceOfElements(By element) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
		}

		/**
		 * To pause the execution @throws
		 */
		public static void pause(int milliSeconds) throws InterruptedException {
			Thread.sleep(milliSeconds);
		}

		/**
		 * Method to get the available option from dropdown
		 * 
		 * @return
		 */
		public static List<String> getOptionFromDropDown(WebElement element) {
			List<String> AvailableOptions = new ArrayList<String>();
			Select obj_select = new Select(element);
			List<WebElement> optionElements = obj_select.getOptions();
			for (int i = 0; i < optionElements.size(); i++) {
				AvailableOptions.add(optionElements.get(i).getText());
			}
			return AvailableOptions;
		}

		/**
		 * Method to perform mouseover action on required element
		 * 
		 * @param element
		 */

		public static void mouseOver(WebElement element) {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();

		}

		/**
		 * Method to wait for page load using javascript
		 */
		public static void jsWaitForPageLoad() {
			driver.manage().timeouts().pageLoadTimeout(pageLoad, TimeUnit.SECONDS);
			String pageReadyState = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
			while (!pageReadyState.equals("complete")) {
				pageReadyState = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
			}
		}

		public String dropDownSelectedValue(WebElement element) {
			Select select = new Select(element);
			String selectedOption = select.getFirstSelectedOption().getText();
			return selectedOption;
		}

		/**
		 * To get default selected value from drop down
		 * 
		 * @param element
		 * @return String
		 */

		public static String getDefaultDropDownValue(WebElement element) {

			Select obj_select = new Select(element);
			return obj_select.getFirstSelectedOption().getText();

		}

		/**
		 * To get checkbox is selected or not from list of checkboxes
		 * 
		 * @param List
		 *            <WebElement>
		 * @return
		 */
		public static boolean isCheckBoxSelectedInDropdown(List<WebElement> elements) {
			boolean flag = true;
			int noOfCheckBox = elements.size();
			for (int i = 0; i < noOfCheckBox; i++) {
				flag = elements.get(i).isSelected();
				if (flag == true)
					break;
			}
			return flag;
		}

		public static void clickjs(WebElement element) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		}

		/**
		 * Method to scroll page up for element visibility using java script
		 * 
		 * @param element
		 */
		public void jsScrollPageUp(WebElement element) {
			int yScrollPosition = element.getLocation().getY();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, " + -yScrollPosition + ")", "");

		}

		/**
		 * Method to get size of list
		 * 
		 * @param List
		 * @return size of list
		 */
		public static int getListSize(List<WebElement> element) {
			int size = 0;
			size = element.size();
			return size;
		}

		/**
		 * quit Browser
		 * 
		 */
		public static void quitBrowser() {
			driver.quit();
		}

		/**
		 * verify Option is available In DropDown
		 * 
		 * @param Dropdown
		 *            and option
		 * @return boolean
		 */

		public boolean verifyOptionIsAvailableInDropDown(WebElement dropDown, String option) {
			boolean flag = false;
			List<String> TaxSetupOption = getOptionFromDropDown(dropDown);
			for (String string : TaxSetupOption) {
				if (string.contains(option)) {
					flag = true;
					break;
				}
			}
			return flag;
		}

		/**
		 * verify if list of integer in Ascending Order
		 * 
		 * @param list
		 * @return boolean
		 */
		public boolean checkAscendingOrderForInteger(List<Integer> list) {
			int previous = list.get(0); // empty string
			boolean flag = true;
			for (Integer current : list) {

				if (current < previous) {
					flag = false;
					return flag;
				}

				previous = current;
			}
			return flag;
		}

		/**
		 * get random special characters
		 * 
		 * @param list
		 * @return boolean
		 */
		public static String getRandomSpecialCharacters(int length) {
			char[] chars = "!#$%&'()*+|\\,-./:;<=>?@[]^_`{|}~".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}
			return sb.toString();
		}

		/**
		 * Method to clear the text box
		 * 
		 * @param WebElement
		 */

		public static void clearTextBox(WebElement element) {
			waitForElementVisibility(element);
			element.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		}

		public static void waitForElementTodisappear(By ByElement) {
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(ByElement));

		}

		/**
		 * Modify drop down value if something is already selected
		 * 
		 * @param Dropdown
		 * @return
		 */
		public void modifyDDValue(WebElement element) {
			List<String> option = getOptionFromDropDown(element);
			String DDSelectedValue = dropDownSelectedValue(element);
			System.out.println("DDSelectedValue " + DDSelectedValue);
			for (String text : option) {
				System.out.println("option in list " + text);
				if (text.isEmpty()) {
					System.out.println("continue...");
					continue;
				} else if (!text.equals(DDSelectedValue)) {
					selectByText(element, text);
					break;
				}
			}
		}

		/**
		 * click on specific co-ordinates for a Webelement
		 * 
		 * @param String
		 *            number
		 * @return
		 * @throws InterruptedException
		 * @throws AWTException
		 */
		public void clickOnSpecificCoordinate(WebElement element, int x, int y) {
			Actions clicker = new Actions(driver);
			clicker.moveToElement(element).moveByOffset(x, y).click().perform();
		}

		/**
		 * Method to scroll page down for element visibility using java script
		 * 
		 * @param element
		 */
		public static void jsScrollPageDown(WebElement element) {
			int yScrollPosition = element.getLocation().getY();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, " + yScrollPosition + ")", "");
		}

		/**
		 * Method to accept alert
		 */
		public static void accept_Alert() {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}

		/**
		 * verify specific string is available in a list of String
		 * 
		 * @param List
		 * @return flag
		 */
		public boolean verifyTextInList(List<String> list, String text) {
			boolean flag = false;
			for (String string : list) {
				if (string.contains(text)) {
					flag = true;
					break;
				}
			}
			return flag;
		}

		/**
		 * Method to verify a field is disabled
		 * 
		 * @param WebElement
		 */

		public static boolean elementDisabled(WebElement element) {
			boolean flag = false;
			waitForElementVisibility(element);
			String disabled = element.getAttribute("class");
			if (disabled.contains("disabled")) {
				flag = true;
			}
			return flag;
		}

		public String getTitles() {
			String s = driver.getTitle();
			System.out.println(s);
			return s;

		}

		/**
		 * Method to get currentUrl
		 * 
		 * @return
		 * 
		 */
		public static String getUrl() {
			String s = driver.getCurrentUrl();
			System.out.println(s);
			return s;
		}

		/**
		 * Method to switch to the newly opened window
		 */
		public static void switchToWindow() {
			homeWindow = driver.getWindowHandle();
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
			}
		}

		/**
		 * To navigate to the main window from child window
		 */
		public static void switchToMainWindow() {
			for (String window : driver.getWindowHandles()) {
				if (!window.equals(homeWindow)) {
					driver.switchTo().window(window);
					driver.close();
				}
				driver.switchTo().window(homeWindow);
			}
		}

		/**
		 * This method returns the no.of windows present
		 * 
		 * @return
		 */
		public static int getWindowCount() {
			return driver.getWindowHandles().size();
		}

		/****************** frames *********************/

		public static void frames(WebElement frameElement) {
			driver.switchTo().frame(frameElement);
		}

		public static void switchToDefaultcontent() {
			try {
				driver.switchTo().defaultContent();
			} catch (NoSuchElementException e) {
			}
		}

		public static void jsScrollDown(WebElement element) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
		}

		public static String FSCcapture() throws IOException {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy HH-mm-ss");
			Date date = new Date();
			String actualDate = format.format(date);
			String srcFile = System.getProperty("user.dir") + "\\target\\ScreenShots\\" + actualDate + ".jpeg";
			String srcFilePath = "..\\ScreenShots\\" + actualDate + ".jpeg";
			File destination = new File(srcFile);
			FileUtils.copyFile(source, destination);
			return srcFilePath;
		}
		public static void onTestFailure() throws IOException {
			String sshotPath = Base.FSCcapture();    		
			Reporter.loadXMLConfig(Utils.getPropertyValue("reportConfigPath"));
			Reporter.addScreenCaptureFromPath(sshotPath);

	}

		public static void jsScrollUp(WebElement element) {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(false);", element);
		}

		public static void inputField(WebElement element, String data) {
			element.sendKeys(data);
		}

	}

