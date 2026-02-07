package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyaccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* Data driven test case
 * 
 * Data valid - login success - test pass - logout
   Data valid - login fail - test fail 
   
   Data invalid - login success - test fail - logout
   Data invalid - login fail - test pass
 
 */
public class TC003_LOginDDTe  extends BaseClass{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="datadriven")  //getting data provider from different class
	public void verify_login(String email,String pwd,String exp)
	{
		logger.info("TC003");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//<yAccount page
		MyaccountPage macc=new MyaccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExist();
		
		if(exp.equalsIgnoreCase("Valid")) {
			if(targetPage==true)
			{
				
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid")) 
		{
			if(targetPage==true)
			{
				
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		}catch(Exception e)
		{
			Assert.fail();
		}
	}
	

}
