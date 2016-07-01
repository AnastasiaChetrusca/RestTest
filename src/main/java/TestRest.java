import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestRest {
	private static final Logger LOG= Logger.getLogger(TestRest.class);
    private static final String excelPath = "src/main/resources/inputs.xlsx"; 
    private static final String sheetName = "Sheet1";
    private static int numRows;
    private static int numCols;
    
    public TestRest(){
        numRows = 8;
        numCols = 4;
    }
    
    
    // Connection Test
    @Test(testName = "Connection test")
    public void ConnectTest() throws IOException {
  		 
  	     GetRequest http =new  GetRequest();
  	     int statusCode = http.sendGet();
  	     System.out.println("Response code: "+ statusCode);
  	     Assert.assertEquals(statusCode, 200, "Unexpected status code");
       
  		 }
   //Excel inputs Test
    @Test(dataProvider = "Testinputs")
    public void testInputs(String get_xml, String date, String name, String expectStr) throws IOException {
        Integer res,expectInt;
        Double temp = Double.parseDouble(expectStr);
        expectInt = temp.intValue();
        GetBnm getter = new GetBnm();
        res = getter.getXml(get_xml, date, name);
        Assert.assertEquals(res,expectInt);
        LOG.info("Actual result: "+res+" Expected value: "+expectInt);

    }
    
    //Get inputs
    @DataProvider(name = "Testinputs")
    public static Object[][] primeNumbers() throws Exception {
        Object[][] excelInputTest = Inputs.getInputs(excelPath, sheetName, numRows, numCols);
        return (excelInputTest);
    }
}
