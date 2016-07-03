import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Input class if for loading the inpust from Inputs.xlsx
public class Inputs {

	    private static XSSFSheet ExcelWSheet;
        private static XSSFWorkbook ExcelWBook;
        private static XSSFCell Cell;
        
	    public static Object[][] getInputs(String FilePath, String SheetName, Integer numOfRows, Integer numOfCols) throws Exception {

	        String[][] excelInputs = null;

	        try {
            
	        	FileInputStream ExcelFile = new FileInputStream(FilePath);
                ExcelWBook = new XSSFWorkbook(ExcelFile);
                ExcelWSheet = ExcelWBook.getSheet(SheetName);
                
                int firstCol= 1, firstRow=1;
                int n, m;
                int Rows = numOfRows;
                int Cols = numOfCols;
                
                excelInputs= new String[Rows][Cols];

	            n = 0;
                for (int i = firstRow; i <= Rows; i++, n++) {
                	
                	m = 0;
                	for (int j = firstCol; j <= Cols; j++, m++) {
	                    excelInputs[n][m] = getData(i, j);
	                 
	                }

	            }

	        } catch (FileNotFoundException e) {

	            System.out.println("Error!");
	            e.printStackTrace();

	        } catch (IOException e) {

	            System.out.println("Error!");
                e.printStackTrace();

	        }

	        return (excelInputs);

	    }

	    public static String getData(int row, int col) throws Exception {
	        String dataFromCell;
	        try {
	            Cell = ExcelWSheet.getRow(row).getCell(col);
	            int dataType = Cell.getCellType();

	            if (dataType == 4) {
	                return "";
	            } else if (dataType == 0) {
	                double res = Cell.getNumericCellValue();
	                dataFromCell = Double.toString(res);
	                return dataFromCell;
	            }else {
	            	dataFromCell = Cell.getStringCellValue();
	                return dataFromCell;
	                }
	            }catch(Exception e){
	                System.out.println(e.getMessage());
	                throw (e);

	            }



	    }
}
