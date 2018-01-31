package security.jaas;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.sun.security.auth.login.ConfigFile;

public class SampleAcn {

    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        
        URL resource = SampleAcn.class.getResource("config/sample_jaas.config");
        
        URL url = resource;
        
        ConfigFile configFile = new ConfigFile(url.toURI());
        
        LoginContext lc = null;
        try {
            lc = new LoginContext("Sample", null,new MyCallbackHandler(),configFile);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        int i;
        for (i = 0; i < 3; i++) {
            try {

                // attempt authentication
                lc.login();

                // if we return with no exception,
                // authentication succeeded
                break;

            } catch (LoginException le) {

                System.err.println("Authentication failed:");
                System.err.println("  " + le.getMessage());
                try {
                    Thread.currentThread().sleep(3000);
                } catch (Exception e) {
                    // ignore
                }

            }
        }
        
        
        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry");
            System.exit(-1);
        }

        System.out.println("Authentication succeeded!");

    }

}
