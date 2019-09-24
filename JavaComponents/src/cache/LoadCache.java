package cache;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;


public class LoadCache extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		MbGlobalMap globalMap = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			
			// ----------------------------------------------------------
			// Add user code below
			MbElement jsonData = inMessage.getRootElement().getLastChild().getFirstChild();
			MbElement temp = jsonData.getFirstChild();
			
			globalMap = MbGlobalMap.getGlobalMap("BALGlobalCacheMap");
			
			if(globalMap.containsKey("CacheFlag")) {
		          globalMap.update("CacheFlag", "True");
		        } else {
		          globalMap.put("CacheFlag", "True");
		        }
			
			
			while(temp!=null){
				 if(globalMap.containsKey(temp.getName())) {
			            if (temp.getName().toString().equals("OFSCredentialsPassword")){
			            	
			            	byte[] decodedValue = DatatypeConverter.parseBase64Binary(temp.getValue().toString());
			           	 	String decoded = new String(decodedValue, StandardCharsets.UTF_8);
			           	 	outMessage.getRootElement().createElementAsLastChild(MbXMLNSC.PARSER_NAME);
			        	 	outMessage.getRootElement().getLastChild().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Decoded", decoded);
			           	 	globalMap.update(temp.getName(),decoded);
			            }
			            else{
			            	globalMap.update(temp.getName(),temp.getValue());
			            }
			        } else {
			        	if (temp.getName().toString().equals("OFSCredentialsPassword")){
			            	
			            	byte[] decodedValue = DatatypeConverter.parseBase64Binary(temp.getValue().toString());
			           	 	String decoded = new String(decodedValue, StandardCharsets.UTF_8);
			           	 	outMessage.getRootElement().createElementAsLastChild(MbXMLNSC.PARSER_NAME);
			        	 	outMessage.getRootElement().getLastChild().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Decoded", decoded);
			           	 	globalMap.put(temp.getName(),decoded);
			            }
			            else{
			            	globalMap.put(temp.getName(), temp.getValue());
			            }
			        	
			        }
				temp= temp.getNextSibling();
			}
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
