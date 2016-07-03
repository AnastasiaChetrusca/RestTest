import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({XListener.class})

public class TestRest {
	private static final Logger LOG= Logger.getLogger(TestRest.class);
    private static final String excelPath = "src/main/resources/inputs.xlsx"; 
    private static final String sheetName = "Sheet1";
    private static int numRows;
    private static int numCols;
    private static int oneRow;  //first row (TestCase1) from excel for testing all dates
    private static int numColsForOneRow;

    
    public TestRest(){
        numRows = 12;
        numCols = 4;
        oneRow = 1;
        numColsForOneRow = 4;
    }
    
    
    // Connection Test
    @Test(testName = "Connection test")
    public void ConnectTest() throws IOException {
  		 
  	     GetRequest http =new  GetRequest();
  	     int statusCode = http.sendGet();
  	     System.out.println("Response code: "+ statusCode);
  	     Assert.assertEquals(statusCode, 200, "Unexpected status code");
       
  		 }
    
   // Test all TestCases from excel file
    @Test(dataProvider = "TestInputs")
    public void testInputs(String get_xml, String date, String name, String expectStr) throws IOException {
        Integer res,expectInt;
        Double temp = Double.parseDouble(expectStr);
        expectInt = temp.intValue();
        GetBnm getter = new GetBnm();       
        res = getter.getXml(get_xml, date, name);
        Assert.assertEquals(res,expectInt);
        LOG.info("Actual result: "+res+" Expected value: "+expectInt);

    }
    
    //Test first TestCase1 (test if the all dates match)
    @Test(dataProvider = "AllDates")
    public void testAllDates(String get_xml, String date, String name, String expectStr) throws IOException {
        Integer res = null,expectInt;
        String dateToString =date.toString();
        String data1;
   	    String data2 = null;
   	    
   	    java.util.Date utilDate = new java.util.Date();
   	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
     	String b=sqlDate.toString();
     	
   	    DateTime start = DateTime.parse(dateToString);
        DateTime end = DateTime.parse(b);
        
        Double temp = Double.parseDouble(expectStr);
        expectInt = temp.intValue();
        GetBnm getter = new GetBnm();
        
        ArrayList<DateTime> between =dataGenerator.getDateRange(start, end);
        for (DateTime d : between) {
        data1 = (String.format("%7s",(d.getMonthOfYear()+"." + d.getYear()).toString()).replace(' ', '0'));
        data2 = (String.format("%10s",(d.getDayOfMonth() +"." +data1).toString()).replace(' ', '0'));
        System.out.println(data2);
        res = getter.getXml(get_xml, data2, name);
        if (res != 3) {  break; }
        }
        
        Assert.assertEquals(res,expectInt);
        LOG.info("Actual result: "+res+" Expected value:"+expectStr); 

    }  
    
    
    //Get all inputs
    @DataProvider(name = "TestInputs")
    public static Object[][] primeNumbers() throws Exception {
        Object[][] excelInputTest = Inputs.getInputs(excelPath, sheetName, numRows, numCols);
        return (excelInputTest);
    }
    
    //Get inputs only from first row 
    @DataProvider(name = "AllDates")
    public static Object[][] primeNumbers2() throws Exception {
        Object[][] excelInputTest = Inputs.getInputs(excelPath, sheetName, oneRow, numColsForOneRow);
        return (excelInputTest);
    }
}
