import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class LogonMessageTransformation {

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
	
	public static byte[] calSomethingIBFTTransaction(String params) {
		
		params = "PHXGDCI200000200201609301222000200000009            6271009999863820              01720160930123519000000000007                    IBFT transaction                                                                                          00627100     00051003646781      00586589388     0034902010098464    00586000000000370058600000000";
		
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
		
		byte[] newParams=new byte[2];
		//public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
		System.arraycopy(params2, params2.length-2, newParams, 0, 2);
		//String params = "PHXGDCI200000800201604261044510000000009                                          80120160426104451000000104451                                                                                                                              00F40379AB9E0EC533 ";
		String result = new String(newParams);
		
		return result;
		
	}
	public static String GenerateUniqueRRN(String RRN) {
		
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
	public static String ChannelSpecificDataField(String message) {
		
		int actualLength = message.length();
		if(actualLength ==80 )
		{
			return message;
		}
		else
		{
			for(int i=actualLength;i<80;i++)
			{
				message+=" ";
			}
		}
		return message;
	}
	public static String CustomerId(String message) {
		
		int actualLength = message.length();
		if(actualLength ==30 )
		{
			return message;
		}
		else
		{
			for(int i=actualLength;i<30;i++)
			{
				message+=" ";
			}
		}
		return message;
	}
	public static String DeliveryChanneltype(String message) {
		
		int actualLength = message.length();
		if(actualLength ==2 )
		{
			return message;
		}
		return "02";
	}
}
