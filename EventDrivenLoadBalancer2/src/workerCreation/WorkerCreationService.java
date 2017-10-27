package workerCreation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.CountDownLatch;

import context.ContextEvent;
import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.Event;
import events.EventType;
import server.Service;
import server.socketWorkers.LbSocketFactory;
import server.socketWorkers.ServerSocketService;
import server.socketWorkers.SocketContextEvent;
import server.socketWorkers.SocketWorker;

public class WorkerCreationService extends Service{

	public WorkerCreationService(EventListener listener, EventDispatcher dispatcher) {
		super(listener, dispatcher);
	}

	@Override
	public void run() {
		while(true){
			try {
				Event event = this.getEventListener().peek();
				
				switch(event.getEventType()){
				case BALANCE_RESPONSE:
					if(this.getEventListener().remove(event)){
						createSession(event);
					}
					break;
				case NEW_SERVER_SOCKET_SERVICE:
					if(this.getEventListener().remove(event)){
						ContextEvent ce = (ContextEvent)event;
						
						ServerSocketService sss = new ServerSocketService(this.getEventListener(), this.getEventDispatcher(),
								ce.getContext());
						
						Thread portListener = new Thread(sss);
						portListener.start();
					}
					break;
				default:
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void createSession(Event event){
		SocketContextEvent sce = (SocketContextEvent)event;
		Socket s1 = sce.getSocket();
		try {
			Socket s2 = LbSocketFactory.createSocket(sce.getConnectionContext());
			
			CountDownLatch latch = new CountDownLatch(1);
			SocketWorker sw1 = new SocketWorker(s1, s2, null, latch);
			SocketWorker sw2 = new SocketWorker(s2, s1, null, latch);
			
			Thread worker1 = new Thread(sw1);
			Thread worker2 = new Thread(sw2);
			
			worker1.start();
			worker2.start();
			latch.countDown();
			
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
			try {
				s1.close();
				s1 = null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
