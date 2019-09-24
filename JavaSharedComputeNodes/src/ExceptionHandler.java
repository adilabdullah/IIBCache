/* 
 * Sample program for use with Product          
 *  ProgIds: 5724-J06 5724-J05 5724-J04 5697-J09 5655-M74 5655-M75 5648-C63 
 *  (C) Copyright IBM Corporation 2005.                      
  All Rights Reserved  Licensed Materials - Property of IBM 
 * 
 * This sample program is provided AS IS and may be used, executed, 
 * copied and modified without royalty payment by customer 
 * 
 * (a) for its own instruction and study, 
 * (b) in order to develop applications designed to run with an IBM 
 *     WebSphere product, either for customer's own internal use or for 
 *     redistribution by customer, as part of such an application, in 
 *     customer's own products. 
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ibm.broker.plugin.*;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ExceptionHandler extends MbJavaComputeNode
{
 private String curency;
  @SuppressWarnings("deprecation")
public void evaluate(MbMessageAssembly inAssembly)
    throws MbException
  {
    MbMessage inMessage = inAssembly.getMessage();
    MbMessage outMessage = new MbMessage();  // create an empty output message
    MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);

    copyMessageHeaders(inMessage, outMessage); // copy headers from the input message

    // Add user code below

    MbElement outRoot = outMessage.getRootElement();
    MbElement outBody = outRoot.createElementAsLastChild("XMLNSC");  // create the 'Body' XMLNSC element


    MbElement ExceptionMainTag = outBody.createElementAsFirstChild(MbXMLNSC.FOLDER, "Exception", null);

    // End of user code
    // The following should only be changed
    // if not propagating message to the 'out' terminal
  
   
    MbElement tag2 = ExceptionMainTag.createElementAsFirstChild(MbElement.TYPE_NAME,"ExceptionDetails","An error occured while processing a message in a MessageFlow.");

    // add title attribute
    MbElement root=inAssembly.getExceptionList().getRootElement();
    MbElement recoverableEception =inAssembly.getExceptionList().getRootElement().getFirstChild();
    //recoverableEception.
    MbElement[] mArr= recoverableEception.getAllElementsByPath("//*");
    
    String details="";
    for(int i=0;i<mArr.length;i++){
     if(mArr[i].getName().equals("RecoverableException")){details+="\t\t----------------------RecoverableException \n";}
     else details+=mArr[i].getName()+" : "+mArr[i].getValueAsString()+"\n\t\t";
    }
    
    MbElement tag3 = ExceptionMainTag.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, 
      "StackTrace", 
      details
      );
    getOutputTerminal("out").propagate(outAssembly);
  }

  public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage) throws MbException
  {
    MbElement outRoot = outMessage.getRootElement();
    MbElement header = inMessage.getRootElement().getFirstChild();

    while(header != null && header.getNextSibling() != null)
      {
        outRoot.addAsLastChild(header.copy());
        header = header.getNextSibling();
      }
  }
  public String getMD5(String s) throws NoSuchAlgorithmException{
		
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(s.getBytes(),0,s.length());
		
		return new BigInteger(1,m.digest()).toString(16);
	}

 
}