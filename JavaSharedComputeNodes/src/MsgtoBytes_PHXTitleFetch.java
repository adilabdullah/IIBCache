import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;


public class MsgtoBytes_PHXTitleFetch extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			String msg = outMessage.getRootElement().getLastChild().getLastChild().getLastChild().getValueAsString();
			byte[] msgarray = msg.getBytes(); 
			byte[] finalmsg = new byte [msgarray.length + 2];
			int x = msgarray.length / 256;
			int y = msgarray.length % 256;
			
			if(y <0)
			{
				y+=256;
			}
			
			finalmsg[0]= (byte) x;
			finalmsg[1]= (byte) y;
			
			for (int a=0; a<msgarray.length; a++){
				finalmsg[a+2] = msgarray[a];
			}
			
			outMessage.getRootElement().createElementAsLastChild(MbBLOB.PARSER_NAME);
				
			outMessage.getRootElement().getLastChild().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "BLOB", finalmsg);
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
