package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\OpenCart_LoginData.xslx";  //takig xl file from test data current location folder
		ExcelUtility xlutil=new ExcelUtility(path); //creating an object for utlity
		
		int totalrows=xlutil.getRowCount(path,"Sheet1");
		int totalcols=xlutil.getCellCount(path,"Sheet1", 1);
		
		String logindata[][]=new String[totalrows][totalcols]; //created for 2 dimensional array which can store
		
		for(int i=1;i<=totalrows;i++)  //1 //read the data from xl storing in 2 dimensional array
		{
			for(int j=0;i<=totalcols;j++)  //0 i is rows j is col
			{
				logindata[i-1][j]=xlutil.getCellData(path,"Sheet1", i, j);
			}
		}
		return logindata; //2 dimensional array 
		
	}
}
