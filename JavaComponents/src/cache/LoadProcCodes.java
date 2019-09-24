package cache;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class LoadProcCodes extends MbJavaComputeNode {

	@Override
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
			//globalMap.put("CacheFlag", "True");
			
			while(temp!=null){
				 if( globalMap.containsKey((temp.getName()+"Type")) || globalMap.containsKey((temp.getName()+"Retries")) ) {
					 globalMap.update(temp.getName()+"Type",temp.getFirstChild().getValue());
					 globalMap.update(temp.getName()+"Retries",temp.getLastChild().getValue());
			          
			        } else {
			          globalMap.put(temp.getName()+"Type", temp.getFirstChild().getValue());
			          globalMap.put(temp.getName()+"Retries", temp.getLastChild().getValue());
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
