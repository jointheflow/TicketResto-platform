package gr.ticketrestoserver.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilHelper {
	public static Date getExpirationInfinite() {
		String target = "24/04/2030";
	    DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
	    Date infiniteExpiration=null;
		try {
			infiniteExpiration = df.parse(target);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return infiniteExpiration;
	} 
	
	public static Date getExpiration30Minute() {
		long ONE_MINUTE_IN_MILLIS=60000;//millisecs
		long t=new Date().getTime();
		Date afterAdding30Mins=new Date(t + (30 * ONE_MINUTE_IN_MILLIS));
		return afterAdding30Mins;
	}

}
