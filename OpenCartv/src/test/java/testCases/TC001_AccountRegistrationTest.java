package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{


	@Test(groups={"Regresion","Master"})
	public void verify_account_registraion()
	{
		logger.info("Starting TC001_AccountRegistrationTest ");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Cicked on my account link ");
		hp.clickRegister();
		logger.info("Cicked on my register link ");

		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);

		logger.info("Providing customer details");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");

		regpage.setTelephone(randomNumber());

		String password=randomAlphaNumeric();

		regpage.setPassword(password);
		regpage.setConfirmPassword(password);

		regpage.setPrivacyPolicy();
		regpage.clickContinue();

		logger.info("Validating expected message");
		String confmsg=regpage.getConfirmation();
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch (Exception e)
		{
			//Error log
			logger.error("Test failed");
			logger.debug("Debug logs");
			Assert.fail();
		}
		logger.info("Test TC001_AccountRegistrationTest finished");

	}

}
