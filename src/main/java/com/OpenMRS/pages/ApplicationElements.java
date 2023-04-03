package com.OpenMRS.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BaseCode.Base;

public class ApplicationElements extends Base{
	public ApplicationElements() {
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//input[@id='username']")
	private WebElement UserName;
	
	@FindBy(xpath = "//input[@id='password']")
	private WebElement Password;
	
	@FindBy(xpath = "//li[@id='Laboratory']")
	private WebElement Laboratory;
	
	@FindBy(xpath = "//input[@id='loginButton']")
	private WebElement LoginBtn;

	public WebElement getUserName() {
		return UserName;
	}

	public void setUserName(WebElement userName) {
		UserName = userName;
	}

	public WebElement getPassword() {
		return Password;
	}

	public void setPassword(WebElement password) {
		Password = password;
	}

	public WebElement getLaboratory() {
		return Laboratory;
	}

	public void setLaboratory(WebElement laboratory) {
		Laboratory = laboratory;
	}

	public WebElement getLoginBtn() {
		return LoginBtn;
	}

	public void setLoginBtn(WebElement loginBtn) {
		LoginBtn = loginBtn;
	}
}
