import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.thoughtworks.xstream.XStream;

//Get the object from XML

public class GetBnm {
	 private final static String PAGE  = "https://www.bnm.md/en/official_exchange_rates";
	    private String array;
	    private BufferedReader bufferRead;
	    private StringReader stringRead;
	    private XStream xstream;
	    private ValCurs valcurs;
	    public GetBnm(){
	        array="";
	    }

	    public String streamConverter(InputStream in) throws IOException {
	        StringBuilder sb = new StringBuilder();
	        bufferRead = new BufferedReader(new InputStreamReader(in));
	        while((array = bufferRead.readLine())!=null){
	            sb.append(array);
	        }
	        return sb.toString();
	    }
	    public Integer getXml(String get_xml, String date, String name){
	        URL obj = null;
	        String result="";
	        try {
	            obj = new URL(PAGE+"?get_xml="+get_xml+"&date="+date);
	            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	            con.setRequestMethod("GET");
	            result = streamConverter(con.getInputStream());
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        if(!result.contains("<?xml"))
	        	
	            return 2;  //PASSED
	        if(result.contains("Request in bad format"))
	            return 1; //FAILD

	        stringRead = new StringReader(result);
	        xstream = new XStream();
	        xstream.autodetectAnnotations(true);
	        valcurs = (ValCurs)xstream.fromXML(stringRead);
	        
	        if(valcurs.getDate().equals(date))
	            return 3; // PASSED
	        
	        if(valcurs.getName().equals(name))
	            return 3;
	        
	        
	    return 0;

	    }

}
