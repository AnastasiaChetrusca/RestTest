
import java.util.ArrayList;
import org.joda.time.DateTime;

public class dataGenerator {

	 public static ArrayList<DateTime> getDateRange(DateTime start, DateTime end) {

	        ArrayList<DateTime> ret = new ArrayList<DateTime>();
	        DateTime tmp = start;
	        while(tmp.isBefore(end) || tmp.equals(end)) {
	            ret.add(tmp);
	            tmp = tmp.plusDays(1);
	        }
	        return ret;
	    }
   
	 
	}


