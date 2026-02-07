package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter; //UI Of the report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test; //creating test case entries in the report and update status of the test methods
	String repName;
	
	public void onStart(ITestContext context) {
		
		//Time generation 
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String curentdatetimestamp=df.format(dt); */
		
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //Time generation simple step
		
		repName="Test-Report"+timeStamp+ ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); //specific location 
		
		sparkReporter.config().setDocumentTitle("Automation Report"); //Title of report
		sparkReporter.config().setReportName("Functional testing"); //name of the report 
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "opencart app");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub module ", "Sustomers");
		extent.setSystemInfo("username", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		
		String os= context.getCurrentXmlTest().getParameter("os"); //parameter from xml file
		extent.setSystemInfo("Operating System", os);
		
		String browser= context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups = context.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
		
		
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName()); 
		test.assignCategory(result.getMethod().getGroups());//to display groups in report  
		test.log(Status.PASS, "Test case passed" +result.getName()); //update status p/f/s
	}
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());   //create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test case failed" +result.getName()); //update status p/f/s
		test.log(Status.FAIL, "Test failed cause" +result.getThrowable());
		
		try {
			//Attaching screenshot for failure
			String impPath= new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromBase64String(impPath);
			
		}catch(IOException e1)
		{
			e1.printStackTrace(); //return warning message
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());   //create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP, "Test case skipped" +result.getName()); //update status p/f/s
		test.log(Status.INFO, "Test case skipped" +result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext context)
	{
		extent.flush();
		
		//open report automatically 
		String pathOFExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOFExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(IOException e1)
		{
			e1.printStackTrace(); //return warning message
		}
		
	}
	

}

