package server.socketWorkers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import context.ConnectionContext;

/**
 * A factory for creating Sockets
 */
public class LbSocketFactory {

	/**
	 * Creates the socket that listens for input from
	 * a client. One thing to note is that this method
	 * assumes that the keystore and the trust keystore
	 * are the some thing.
	 *   
	 * @param port
	 * @param keystorePath
	 * @param keystoreType
	 * @param password
	 * @param keyAlgorithm
	 * @param protocol
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	public static SSLServerSocket createSSLServerSocket(Integer port, String keystorePath, String keystoreType,
			String password, String keyAlgorithm, String protocol)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, UnrecoverableKeyException, KeyManagementException {
		KeyStore ks = KeyStore.getInstance(keystoreType);
		ks.load(new FileInputStream(keystorePath), password.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(keyAlgorithm);
		kmf.init(ks, password.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(keyAlgorithm);
		tmf.init(ks);

		SSLContext sc = SSLContext.getInstance(protocol);
		TrustManager[] trustManagers = tmf.getTrustManagers();
		sc.init(kmf.getKeyManagers(), trustManagers, null);

		SSLServerSocketFactory ssf = sc.getServerSocketFactory();
		SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(port);
		return s;
	}
	
	/**
	 * Creates the Socket for initiating messages to a server.
	 * One thing to note is that this method assumes that the 
	 * keystore and the trust keystore are the some thing. 
	 * 
	 * Also this method leaves to the caller to initiate the
	 * handshake.
	 * 
	 * @param host
	 * @param port
	 * @param keystorePath
	 * @param keystoreType
	 * @param password
	 * @param keyAlgorithm
	 * @param protocol
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	public static SSLSocket createSSLSocket(String host, Integer port, String keystorePath, String keystoreType,
			String password, String keyAlgorithm, String protocol)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, UnrecoverableKeyException, KeyManagementException {
		KeyStore ks = KeyStore.getInstance(keystoreType);
		ks.load(new FileInputStream(keystorePath), password.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(keyAlgorithm);
		kmf.init(ks, password.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(keyAlgorithm);
		tmf.init(ks);

		SSLContext sc = SSLContext.getInstance(protocol);
		TrustManager[] trustManagers = tmf.getTrustManagers();
		sc.init(kmf.getKeyManagers(), trustManagers, null);

		SSLSocketFactory ssf = sc.getSocketFactory(); 
		SSLSocket s = (SSLSocket) ssf.createSocket(host, port);
		return s;
	}
	
	/**
	 * Creates either a plain socket or an ssl socket depending
	 * upon the Connection Context <c>.
	 * 
	 * @param c
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Socket createSocket(ConnectionContext c) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException{
		Socket s = null;
		
		if("None".equals(c.getProtocol())){
			s = new Socket(c.getHost(), c.getPort());
		}else{
			s = createSSLSocket(c.getHost(), c.getPort(), c.getKeystorePath(), c.getKeystoreType(), c.getKeystorePassword(), c.getKeyAlgorithm(), c.getProtocol());
		}
		
		return s;
	}
	
	/**
	 * Creates either a plain server socket or an ssl server socket
	 * depending upon the Connection Context <c>.
	 * 
	 * @param c
	 * @return
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	public static ServerSocket createServerSocket(ConnectionContext c) throws IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException{
		ServerSocket s = null;
		
		if("None".equals(c.getProtocol())){
			s = new ServerSocket(c.getPort());
		}else{
			s = createSSLServerSocket(c.getPort(), c.getKeystorePath(), c.getKeystoreType(), c.getKeystorePassword(), c.getKeyAlgorithm(), c.getProtocol());
		}
		
		return s;
	}
}
