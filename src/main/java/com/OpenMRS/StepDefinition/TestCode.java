package com.OpenMRS.StepDefinition;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.OpenMRS.pages.ApplicationElements;
import com.mmed.logger.ApplicationLogger;
import com.mmed.utils.Utils;

import BaseCode.Base;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class TestCode extends Base {
	ApplicationElements AE = new ApplicationElements();
	@Given("^user launch the application and enter the application URL$")
	public void user_launch_the_application_and_enter_the_application_URL() throws Throwable {
		try {
			driver.get(Utils.getPropertyValue("applicationURL"));
		} catch (Exception ex) {
			onTestFailure();
			ApplicationLogger.info("Exception in launching the application");
			ApplicationLogger.info("Exception " + ex);
			Assert.fail("Failed to launch the application");
		}
	}

	@When("^user logs in with valid cred$")
	public void user_logs_in_with_valid_cred() throws Throwable {
		try {
			
			setText(AE.getUserName(), Utils.getPropertyValue("username"));
			setText(AE.getPassword(), Utils.getPropertyValue("password"));
			clicks(AE.getLaboratory());
			clickjs(AE.getLoginBtn());
		} catch (IOException e) {
			onTestFailure();
			ApplicationLogger.info("Exception in login function");
			ApplicationLogger.info("Exception " + e);
			Assert.fail("Failed to login to the application");
		}

	}

	@When("^verify user landed to next page$")
	public void verify_user_landed_to_next_page() throws Throwable {
		try {
			String CurrentURL = driver.getCurrentUrl();
			String ActualURL = "https://demo.openmrs.org/openmrs/referenceapplication/home.page";
			Assert.assertEquals(CurrentURL, ActualURL);
			ApplicationLogger.info("Expected Title of the page present so Assertion Passed");
		} catch (Exception e) {
			onTestFailure();
			e.printStackTrace();
			ApplicationLogger.info("Expected Title of the page not present so Assertion Failed");
			Assert.fail("Current URL and Expected URL not same");
		}
	}
	@Then("^user starts to enter details$")
	public void user_starts_to_enter_details() throws Throwable {
	    try {
			WebElement RegisterPatient = driver.findElement(By.xpath(
					"//a[@href='/openmrs/registrationapp/registerPatient.page?appId=referenceapplication.registrationapp.registerPatient' and contains(@id,'referenceapplication')]"));
			RegisterPatient.click();

			WebElement GivenName = driver.findElement(By.xpath("//input[@name='givenName']"));
			GivenName.sendKeys("Test");
			String FirstName = GivenName.getAttribute("value");
			ApplicationLogger.info("FN=" + FirstName);
			WebElement FamilyName = driver.findElement(By.xpath("//input[@name='familyName']"));
			FamilyName.sendKeys("calab");
			WebElement Gender = driver.findElement(By.xpath("//ul[@id='formBreadcrumb']//span[@id='genderLabel']"));
			Gender.click();
			WebElement GenederDd = driver.findElement(By.xpath("//select[@id='gender-field']"));
			Select s = new Select(GenederDd);
			s.selectByVisibleText("Male");
			WebElement selectedGender = s.getFirstSelectedOption();
			String GenderText = selectedGender.getText();
			ApplicationLogger.info(GenderText);
			WebElement BirtDayLabel = driver.findElement(By.xpath("//span[@id='birthdateLabel']"));
			BirtDayLabel.click();
			WebElement Day = driver.findElement(By.xpath("//input[@id='birthdateDay-field']"));
			Day.sendKeys("10");
			String BirthDay = Day.getAttribute("value");
			WebElement Month = driver.findElement(By.xpath("//select[@id='birthdateMonth-field']"));
			Select s1 = new Select(Month);
			s1.selectByVisibleText("February");
			WebElement year = driver.findElement(By.xpath("//input[@id='birthdateYear-field']"));
			year.sendKeys("2000");
			WebElement Address = driver.findElement(By.xpath("//span[@id='contactInfo_label']/..//span[text()='Address']"));
			Address.click();
			WebElement AddBox1 = driver.findElement(By.xpath("//input[@id='address1']"));
			AddBox1.sendKeys("Test");
			String AddBox1Value = AddBox1.getAttribute("value");
			WebElement AddBox2 = driver.findElement(By.xpath("//input[@id='address2']"));
			AddBox2.sendKeys("Main Street");
			WebElement City = driver.findElement(By.id("cityVillage"));
			City.sendKeys("NewYork");
			WebElement State = driver.findElement(By.id("stateProvince"));
			State.sendKeys("NY");
			WebElement Country = driver.findElement(By.id("country"));
			Country.sendKeys("USA");
			WebElement PostalCode = driver.findElement(By.id("postalCode"));
			PostalCode.sendKeys("10022");
			WebElement PhoneNumber = driver
					.findElement(By.xpath("//span[@id='contactInfo_label']/..//span[text()='Phone Number']"));
			PhoneNumber.click();
			WebElement PPhoneNumber = driver.findElement(By.name("phoneNumber"));
			PPhoneNumber.sendKeys("8877665544");
			String GivenPhNo = PPhoneNumber.getAttribute("value");
			ApplicationLogger.info(GivenPhNo);
			WebElement RelativesIcon = driver
					.findElement(By.xpath("//span[@id='relationships-info_label']/..//span[text()='Relatives']"));
			RelativesIcon.click();
			driver.findElement(By.id("next-button")).click();
			try {
				String Name = driver.findElement(By.xpath("//div[@id='dataCanvas']/div/p[1]")).getText();
				ApplicationLogger.info(Name);
				Assert.assertEquals(true, Name.contains(FirstName));
			} catch (Exception e) {
				ApplicationLogger.info("Assert failed Name not same");
				e.printStackTrace();
			}
			String Gender1;
			try {
				Gender1 = driver.findElement(By.xpath("//div[@id='dataCanvas']/div/p[2]")).getText();
				ApplicationLogger.info(Gender1);
				Assert.assertEquals(true, Gender1.contains(GenderText));
			} catch (Exception e) {
				ApplicationLogger.info("Assert failed Gender not same");
			}
			try {
				String Birthdate = driver.findElement(By.xpath("//div[@id='dataCanvas']/div/p[3]")).getText();
				ApplicationLogger.info(Birthdate);
				Assert.assertEquals(true, Birthdate.contains(BirthDay));
			} catch (Exception e) {
				ApplicationLogger.info("Assert failed Birtdate not same");
			}
			try {
				String Address1 = driver.findElement(By.xpath("//div[@id='dataCanvas']/div/p[4]")).getText();
				ApplicationLogger.info(Address1);
				Assert.assertEquals(true, Address1.contains(AddBox1Value));
			} catch (Exception e) {
				ApplicationLogger.info("Assert failed Address1 not same");
			}
			try {
				String PhoneNum = driver.findElement(By.xpath("//*[@id='dataCanvas']/div/p[5]")).getText();
				ApplicationLogger.info(PhoneNum);
				Assert.assertEquals(true, GivenPhNo.contains("8877"));
			} catch (Exception e) {
				ApplicationLogger.info("Assert failed PhoneNum not same");
				e.printStackTrace();
			}
			driver.findElement(By.id("submit")).click();
			WebDriverWait w = new WebDriverWait(driver, 5);
			pause(5000);
			WebElement StartVisit = driver.findElement(By.xpath("//a[@id='org.openmrs.module.coreapps.createVisit']"));
			w.until(ExpectedConditions.elementToBeClickable(StartVisit));
			StartVisit.click();
			WebElement ConfirmBtn = driver.findElement(By.xpath("//button[@id='start-visit-with-visittype-confirm']"));
			ConfirmBtn.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			try {
				WebElement Attachments = driver
						.findElement(By.xpath("//a[@id='attachments.attachments.visitActions.default']"));
				w.until(ExpectedConditions.elementToBeClickable(Attachments));
				Attachments.click();
				pause(5000);
				WebElement FileUpload = driver.findElement(By.xpath("//form[@id='visit-documents-dropzone']"));
				w.until(ExpectedConditions.elementToBeClickable(FileUpload));
				FileUpload.click();
				Robot robot = new Robot();
				robot.setAutoDelay(3000);
				StringSelection selection = new StringSelection(
						"C:\\Users\\snave\\eclipse-workspace\\SeleniumJava\\src\\test\\UploadFiles\\TestDoc1.docx");
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.setAutoDelay(3000);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				robot.setAutoDelay(3000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (Exception e) {
				onTestFailure();
				e.printStackTrace();
				ApplicationLogger.info("Expection in fileuploading");
			}
			WebElement TextField = driver.findElement(By.xpath("//textarea[@ng-model='typedText.fileCaption']"));
			TextField.sendKeys("Test Doc");
			WebElement UploadFile = driver.findElement(By.xpath("//button[text()='Upload file']"));
			UploadFile.click();
			pause(3000);
			String UploadedMsg = driver.findElement(By.xpath("/html/body/div[2]")).getText();
			ApplicationLogger.info("Uploaded Message=" + UploadedMsg);
			if (UploadedMsg.contains(UploadedMsg)) {
				ApplicationLogger.info("file uploaded and message shown");
			} else {
				ApplicationLogger.info("file not uploaded and message not shown");
			}
			WebElement TestCalab = driver
					.findElement(By.xpath("//a[contains(@href,'/openmrs/coreapps/clinicianfacing/patient.page')]"));
			TestCalab.click();
			String SuccessText = driver.findElement(By.xpath("//div[text()='Attachment Upload']")).getText();
			String text = "Attachment Upload";
			if (SuccessText.contains(text)) {
				Assert.assertEquals(true, SuccessText.contains(text));
				ApplicationLogger.info("File Uploaded and File present in UI");
			} else {
				ApplicationLogger.info("File Uploaded But File not shown in UI");
			}
			String cssValue = driver.findElement(By.xpath("//div[text()='Attachment Upload']")).getCssValue("color");
			String c = Color.fromString(cssValue).asHex();
			String color = "#ffffff";
			if (c.contains(color)) {
				ApplicationLogger.info("Recently uploded file present in UI checked using CSS");
			} else {
				ApplicationLogger.info("Recently uploded file Not present in UI checked using CSS");
			}
			WebElement RecentFile = driver
					.findElement(By.xpath("//*[contains(@id,'coreapps-fr')]/div[2]/visitbyencountertype/ul/li/a"));
			String RecentFileName = RecentFile.getText();
			ApplicationLogger.info("UploadedFileName= " + RecentFileName);
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yyyy");
			Date date = new Date();
			ApplicationLogger.info(formatter.format(date));
			if (RecentFileName.contains(formatter.format(date))) {
				ApplicationLogger.info("Recently uploaded file present with correct date");
			} else {
				ApplicationLogger.info("Recently uploaded file not present with correct date");
			}
			WebElement EndVisit = driver.findElement(By.xpath("(//a[@id='referenceapplication.realTime.endVisit'])[2]"));
			EndVisit.click();
			ApplicationLogger.info("End visit clicked");
			WebElement YesBtn = driver.findElement(By.xpath("//div[@id='end-visit-dialog']/div[2]/button[1]"));
			YesBtn.click();
			ApplicationLogger.info("End visit yes btn clicked");
			pause(5000);
			WebElement DeletePatient = driver.findElement(By.xpath("//a[@id='org.openmrs.module.coreapps.deletePatient']"));
			DeletePatient.click();
			ApplicationLogger.info("Delete btn clicked");
			WebElement DeleteReason = driver.findElement(By.xpath("//input[@id='delete-reason']"));
			DeleteReason.sendKeys("Testing");
			WebElement DltCnfrm = driver.findElement(By.xpath("//input[@id='delete-reason']/../button[text()='Confirm']"));
			DltCnfrm.click();
			WebElement FindName = driver.findElement(By.xpath("//input[@id='patient-search']"));
			setText(FindName, "calab");
			WebElement Text = driver.findElement(By.xpath("//td[text()='No matching records found']"));
			if (Text.isDisplayed()) {
				ApplicationLogger.info("Data Deleted");
			} else {
				ApplicationLogger.info("Data Not Deleted");
			}
		
		} catch (Exception e) {
			onTestFailure();
			e.printStackTrace();
			ApplicationLogger.info("Exception in entering details and workflow");
			Assert.fail("Failed to enter details and run application");
		}
	}


}
