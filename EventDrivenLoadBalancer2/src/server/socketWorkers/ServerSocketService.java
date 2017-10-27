package server.socketWorkers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import context.ConnectionContext;
import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.EventType;
import server.Service;

public class ServerSocketService extends Service{
	private final ConnectionContext cc;
	private ServerSocket ss = null;

	public ServerSocketService(EventListener listener, EventDispatcher dispatcher, ConnectionContext cc) {
		super(listener, dispatcher);
		this.cc = cc;
	}

	@Override
	public void run() {
		
		try {
			this.ss = LbSocketFactory.createServerSocket(this.cc);
			while(true){
				Socket s = this.ss.accept();
				SocketContextEvent sce = new SocketContextEvent(null, this, EventType.BALANCE_REQUEST, false, s, this.cc);
				this.getEventDispatcher().put(sce);
			}
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
