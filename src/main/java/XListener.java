import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class XListener extends TestListenerAdapter{
	 private Map<Integer, Object[]> data = null;
	    private Integer iterator = 1;
	    @Override
	    public void onStart(ITestContext testContext) {
	        super.onStart(testContext);
	        data = new HashMap<Integer, Object[]>();
	        data.put(iterator, new Object[] {"TestName", "STATUS", "Throwable"});

	       
	    }

	    @Override
	    public void onFinish(ITestContext testContext) {
	        super.onFinish(testContext);
	        setDataXLSX(testContext);
	        
	    }

	    @Override
	    public void onTestSuccess(ITestResult testResult){
	        iterator++;
	        data.put(iterator,new Object[] {getName(testResult),"PASSED", "NULL"});
	       
	    }

	    @Override
	    public void onTestFailure(ITestResult testResult){
	        iterator++;
	        data.put(iterator,new Object[] {getName(testResult),"FAILED" , testResult.getThrowable().getMessage()});
	       
	    }

	    @Override
	    public void onTestSkipped(ITestResult testResult){
	        iterator++;
	        data.put(iterator,new Object[] {getName(testResult),"SKIPPED","NULL"});
	        
	    }
	   
	    private void setDataXLSX(ITestContext testContext) {
	        try {
	            FileInputStream file;
	            XSSFWorkbook workbook;
	            XSSFSheet sheet;
	            if(new File("test-output.xlsx").exists()){
	                file = new FileInputStream(new File("test-output.xlsx"));
	                workbook = new XSSFWorkbook(file);
	            }
	            else workbook = new XSSFWorkbook();
	            int sheetIndex = 1;
	            while (workbook.getSheet("TEST " + sheetIndex) != null) {
	                sheetIndex++;
	            }
	            sheet = workbook.createSheet("TEST " + sheetIndex);
	            sheet = workbook.getSheet("TEST " + sheetIndex);
	            Set<Integer> keyset = data.keySet();
	            int rowNum = 0;
	            for (Integer key : keyset) {
	                Row row = sheet.createRow(rowNum++);
	                Object [] objArr = data.get(key);
	                int cellNum = 0;
	                for (Object obj : objArr) {
	                    Cell cell = row.createCell(cellNum++);
	                    if(obj instanceof Date)
	                        cell.setCellValue((Date)obj);
	                    else if(obj instanceof Boolean)
	                        cell.setCellValue((Boolean)obj);
	                    else if(obj instanceof String)
	                        cell.setCellValue((String)obj);
	                    else if(obj instanceof Double)
	                        cell.setCellValue((Double)obj);
	                }
	            }
	            for(int colNum = 0; colNum < sheet.getLastRowNum(); colNum++)
	               sheet.autoSizeColumn(colNum);
	            FileOutputStream out = new FileOutputStream(new File("test-output.xlsx"));
	            workbook.write(out);
	            out.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    private String getName(ITestResult testResult) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(testResult.getName());
	        Object[] parameters = testResult.getParameters();
	        if (parameters != null && parameters.length > 0) {
	            sb.append("[");
	            for (Object parameter : parameters) {
	                sb.append(parameter).append(",");
	            }
	            sb.replace(sb.length() - 1, sb.length(), "]");
	        }
	        return sb.toString();
	    }

}
