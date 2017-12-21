package sslserver;

import java.security.cert.Certificate;
import java.util.Arrays;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HandShakeListener implements HandshakeCompletedListener {


	public void handshakeCompleted(HandshakeCompletedEvent event) {
		System.out.println("handshake completed");
		String cipherSuite = event.getCipherSuite();
		System.out.println("cipherSuite:"+cipherSuite);
		
	/*	try {
			Certificate[] peerCertificates = event.getPeerCertificates();
			System.out.println("Certificate[]:"+Arrays.toString(peerCertificates));
		} catch (SSLPeerUnverifiedException e) {
			e.printStackTrace();
		}*/
		
	}

}
