import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class MessageTransform {

	/**
	 * Sample method that can be called from a Mapping Custom Java transform.
	 * The content of this method provides the implementation for the Custom Java transform.
	 */
	public static java.lang.Object sampleTransform(java.lang.Object message) {
		return message;
	}
	public static byte[] StringToByteStream(String params) {
		
		//String params = "PHXGDCI200000800201604261044510000000009                                          80120160426104451000000104451                                                                                                                              00F40379AB9E0EC533 ";
		
		byte[] arr = params.getBytes();
		byte[] result = new byte[arr.length + 2];
		
		result[0]= (byte) (params.length()/256);
		
		int mod = (params.length()%256);
		if(mod <0)
		{
			mod+=256;
		}
		
		result[1]=(byte) mod;
		
		
		for(int i=0;i<arr.length;i++){
			result[i+2]=arr[i];
		}
		
		//int length = arr.length;
		//byte var2 = arr[0];
		return result;
	}
	public static String byteToString(byte[] params2) {

		
		byte[] newParams=new byte[params2.length];
		System.arraycopy(params2, 0, newParams, 0, newParams.length);
//		
		String result = new String(newParams);
		return result;
	}
	public static String GenerateUniqueRRN(String RRN) {
		
		if(RRN.isEmpty() && RRN.length()<=0){
			DateFormat dateFormat = new SimpleDateFormat("ddHHmmssms");
	        Calendar cal = Calendar.getInstance();
	        String FirstTenDigit = dateFormat.format(cal.getTime());
	        FirstTenDigit=FirstTenDigit.substring(0, 10);
	        
	        
	        Random r = new Random();
	        int Low = 10;
	        int High = 99;
	        int Result = r.nextInt(High-Low) + Low;
	        
	        String LastTwoDigit = Integer.toString(Result);
	        
	        RRN = FirstTenDigit + LastTwoDigit;
	        //RRN = Integer.toString(RRN.length());
			
			return RRN.substring(0, 12);
		}
		else{
			while(RRN.length() < 12)
			{
				RRN +="0";
			}
			if(RRN.length() > 12)
			{
				RRN = RRN.substring(RRN.length()-12);
			}
			return RRN;
		}
		
		
	}
	public static String transmissionDateAndtimeTogether(String dateTime) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
        dateTime = dateFormat.format(cal.getTime());
       
        return dateTime;
	}
	public static String transactionnDate(String date) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		date = dateFormat.format(cal.getTime());
       
        return date;
	}
	public static String TransactionTime(String time) {
		
		DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		Calendar cal = Calendar.getInstance();
		time = dateFormat.format(cal.getTime());
       
        return time;
	}
	public static String TransactionAmount(String amount) {
		
		String valueAfterDecimal="",valueBeforeDecimal="";
		if(amount.contains("."))
		{
			int index = amount.indexOf(".");
			valueAfterDecimal = amount.substring(index+1);
			if(valueAfterDecimal.length() > 2)
				valueAfterDecimal = valueAfterDecimal.substring(0, 2);
			valueBeforeDecimal = amount.substring(0,index);
			while(valueAfterDecimal.length() < 2)
			{
				valueAfterDecimal+="0";
			}
			amount = valueBeforeDecimal +  valueAfterDecimal;
		}
		else
			amount +="00";
		return amount;
	}
}
