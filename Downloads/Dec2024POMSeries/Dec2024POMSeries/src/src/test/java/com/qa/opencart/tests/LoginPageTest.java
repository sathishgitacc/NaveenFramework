package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import static com.qa.opencart.constants.AppConstants.*;


public class LoginPageTest extends BaseTest{
	
	
	@Test(description = "checking login title")
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
	}
	
	@Test(description = "checking login page url")
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
	}
	
	@Test(description = "forgotPwdLinkExistTest")
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	
	@Test(priority = Short.MAX_VALUE, description = "login with valid credentials")
	public void doLoginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
	}
	
	
	@Test(enabled = false, description = "WIP -- forgot pwd check")
	public void forgotPwd() {
		System.out.println("forgot pwd ");
	}
	
	

}
