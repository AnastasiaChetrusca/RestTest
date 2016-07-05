import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class MyListener extends TestListenerAdapter {
StringBuilder resultStringBuilder = new StringBuilder();
	


	@Override
	public void onTestFailure(ITestResult tr) {
		writeResult(tr);
		super.onTestFailure(tr);
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {
		writeResult(itr);
		super.onConfigurationSuccess(itr);
	}
	@Override
	public void onConfigurationFailure(ITestResult itr) {
		writeResult(itr);
		super.onConfigurationFailure(itr);
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
		writeResult(tr);
		super.onTestFailedButWithinSuccessPercentage(tr);
	}
	@Override
	public void onTestSuccess(ITestResult tr) {
		writeResult(tr);
			super.onTestSuccess(tr);
	}
	 
	@Override
	public void onTestSkipped(ITestResult tr) {
		writeResult(tr);
		super.onTestSkipped(tr);
	}
	private void writeResult(ITestResult tr) {
			
			resultStringBuilder.append("<tr>");
			
		for (String line : Reporter.getOutput(tr) ){
				resultStringBuilder.append(String.format("<td>%s</td>",line));	
			} 
			
			resultStringBuilder.append(String.format("<td>%d</td>", 
					-(tr.getStartMillis()-tr.getEndMillis())));

		
			switch (tr.getStatus()) {
			case 1:
				resultStringBuilder.append("<td style = color:green>PASSED</td>");
				break;
			case 2:
				resultStringBuilder.append("<td style = color:red>FAILURE</td>");
				break;
			case 3:
				resultStringBuilder.append("<td>SKIP</td>");
				break;
			case 4:
				resultStringBuilder.append("<td>SUCCESS_PERCENTAGE_FAILURE</td>");
				break;

			default:
				break;
			}
			resultStringBuilder.append("</tr>");

	}
	
	@Override
	public void onFinish(ITestContext testContext) {
	try {
		FileOutputStream fos = new FileOutputStream(new File("log.html"));
		
	    BufferedReader reader = new BufferedReader( new FileReader ("html_template.txt"));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    String html_template = stringBuilder.toString();
		fos.write(String.format("%s%s</table></body></html>", html_template,
				resultStringBuilder.toString()).getBytes());
		reader.close();
		fos.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		super.onFinish(testContext);
	}


}
