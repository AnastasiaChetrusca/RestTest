import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class GetRequest {
	public int sendGet() throws ClientProtocolException, IOException {
		 String url = "https://www.bnm.md/en/official_exchange_rates?get_xml=1&date=29.06.2016";
		 HttpClient client = HttpClientBuilder.create().build();
		 HttpGet request = new HttpGet(url);
		 
		 request.addHeader("User-Agent", "Google Chrome/51.0.2704.103");
		 HttpResponse response = client.execute(request);
		 
		 int statusCode = response.getStatusLine().getStatusCode();
	 
		 BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		 StringBuffer result = new StringBuffer();
		 String inputline;
		 
		 while((inputline = in.readLine()) != null){
			 result.append(inputline);
		 }
		 
		 in.close(); 
		  
        return statusCode;
		 }
}
