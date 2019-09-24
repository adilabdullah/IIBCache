import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;


public class ProcessPHXResponse_JavaCompute extends MbJavaComputeNode {
	public static int decode(byte[] msg_len) {
		 
		//byte msg_len[] = new byte[2];
		//msg_len = Encode(msg).getBytes();
		int msgSize = 0;

		msgSize = 256 * (msg_len[0] < 0 ? msg_len[0] + 256 : msg_len[0]);
		msgSize += msg_len[1] < 0 ? msg_len[1] + 256 : msg_len[1];
		return msgSize;

}

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		int msgSize;
		try {

            // create new message as a copy of the input
            MbMessage outMessage = new MbMessage(inMessage);
            outAssembly = new MbMessageAssembly(inAssembly, outMessage);
            // ----------------------------------------------------------
            // Add user code below

            // ----------------------------------------------------------
            // ----------------------------------------------------------
            // Add user code below

            MbElement inputRoot  = inAssembly.getMessage().getRootElement();
			
			MbElement inputBlob  = inputRoot.getLastChild();
			
			MbElement outputRoot = outAssembly.getMessage().getRootElement();
			
			MbElement outputBlob = outputRoot.getLastChild();
			
			
			Object obj = inputBlob.getLastChild().getValue();
			byte[] bbb = (byte[]) obj;
			           
			msgSize = decode(bbb);
			           
			MbElement LocalEnvironment = inAssembly.getLocalEnvironment().getRootElement();                                                                   
            MbElement TCPIP = LocalEnvironment.getFirstElementByPath("TCPIP");
            MbElement Receive = TCPIP.getFirstElementByPath("Receive");
            MbElement DynamicLength = Receive.getFirstElementByPath("DynamicLength");
            if(DynamicLength==null)
            Receive.createElementAsLastChild(MbElement.TYPE_NAME,"DynamicLength",msgSize);
            
            else DynamicLength.setValue(msgSize);
            //TCPIP.createElementAsLastChild(MbElement.TYPE_NAME,"Receive",null).createElementAsLastChild(MbElement.TYPE_NAME,"Length",msgSize);

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
		if(msgSize<17)
			alt.propagate(outAssembly);
		else 
			out.propagate(outAssembly);

	}

}
