/**
 * 
 */
package security.sasl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;

/**
 * @author cango
 *
 */
public class SaslMain {
    
    
    static class ServerCallBack implements javax.security.auth.callback.CallbackHandler{

        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            // TODO Auto-generated method stub
            
        }
        
    }
    
    public static void main(String[] args) {
        
        String[] mechanisms = new String[]{"DIGEST-MD5", "PLAIN"}; 
        
        
//        SaslServer saslServer = Sasl.createSaslServer(mechanisms, null, null, props, new ServerCallBack());
        
        
     /*   SaslClient sc = Sasl.createSaslClient(mechanisms, authzid, protocol, 
                serverName, props, callbackHandler);*/
        
        
    }

}
