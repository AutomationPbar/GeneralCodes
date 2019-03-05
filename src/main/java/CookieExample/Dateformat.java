package CookieExample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateformat {
	
	public static String getCurrentTimeStamp() {
	       SimpleDateFormat formDate = new SimpleDateFormat("ddMMMyyyy");

	       // String strDate = formDate.format(System.currentTimeMillis()); // option 1
	       String strDate = formDate.format(new Date()); // option 2
	       return strDate;
	  } 
	  public static void main (String args[]) {
	       System.out.println(getCurrentTimeStamp());
	  }
	}