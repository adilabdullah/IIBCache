package dfdl;

public class BlobHandler {

	/**
	 * Sample method that can be called from a Mapping Custom Java transform.
	 * The content of this method provides the implementation for the Custom Java transform.
	 */
	public static java.lang.Object sampleTransform(java.lang.Object value) {
		return value;
	}
	
	public static byte[] RemoveSpecialChar(byte[] data) {
		String str = new String(data);
		str=str.replaceAll("ü", "");
		
		str=str.replaceAll("ý", ",");
		str=str.replaceAll("[^\\x00-\\x7F]", "");
		//str=str.replaceAll("", "");
				
		data=str.getBytes();
		return data;
	}

}
