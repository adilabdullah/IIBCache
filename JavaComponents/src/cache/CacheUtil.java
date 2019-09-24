package cache;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;

public class CacheUtil {
  /**
   * Method to get a value from Global Cache using map name and key
   */
  public static String getValue(String strKey) {
    
    String strValue = null;
    MbGlobalMap globalMap = null;
    
    try
    {
      globalMap = MbGlobalMap.getGlobalMap("BALGlobalCacheMap");
      strValue = (String) globalMap.get(strKey);
    }
    catch(MbException mbe)
    {
      System.out.println(mbe.getMessage());
      mbe.printStackTrace();
    }
    
    return strValue;
  }
  
  /**
   * Method to add all the key-value pairs for a map in Global Cache
   */
  public static Boolean addMap(MbElement elmMap) {
    
    String strValue = null;
    String strKey = null;
    
    MbGlobalMap globalMap = null;
    
    try
    {
      elmMap = elmMap.getFirstChild();
      
      
      globalMap = MbGlobalMap.getGlobalMap("BALGlobalCacheMap");
      
      MbElement elmEntry = elmMap.getNextSibling();
      
      while (elmEntry != null) {

        strKey = elmEntry.getFirstChild().getValueAsString();
        strValue = elmEntry.getValueAsString();
        
        if(globalMap.containsKey(strKey)) {
          globalMap.update(strKey,strValue);
        } else {
          globalMap.put(strKey, strValue);
        }
        
        elmEntry = elmEntry.getNextSibling();
      }
    }
    catch(MbException mbe) {
      System.out.println(mbe.getMessage());
      mbe.printStackTrace();
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
  
  /**
   * Method to add a key-value pair to a map in Global Cache
   */
  public static Boolean addUpdateKey(String strKey, String strValue) {
    
    MbGlobalMap globalMap = null;
    
    try
    {
      globalMap = MbGlobalMap.getGlobalMap("BALGlobalCacheMap");
      
      if(globalMap.containsKey(strKey)) {
        globalMap.update(strKey,strValue);
      } else {
        globalMap.put(strKey, strValue);
      }
    }
    catch(MbException mbe) {
      System.out.println(mbe.getMessage());
      mbe.printStackTrace();
      return Boolean.FALSE;
    }

    return Boolean.TRUE;
  }
}