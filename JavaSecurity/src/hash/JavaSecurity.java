package hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class JavaSecurity {

	public static String getMD5(String s){
        if(s != null && !s.isEmpty()) {
        MessageDigest m;
        try {
              m = MessageDigest.getInstance("MD5");
              m.update(s.getBytes(),0,s.length());
              
              return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
        }
        return "";
        }
        else return "";
        
  }
  
  public static String decode64(String s){
        String decoded = null;
        try {
              byte[] decodedValue = DatatypeConverter.parseBase64Binary(s);
        decoded = new String(decodedValue, StandardCharsets.UTF_8);
        return decoded;
              
        } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
        }
        return decoded;
  }
	

}
