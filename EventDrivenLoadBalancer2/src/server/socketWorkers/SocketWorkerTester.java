package server.socketWorkers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SocketWorkerTester {
	public static void main(String[] args) {
		ServerSocket serverSocket;
		
		
		try {
			serverSocket = new ServerSocket(9000);
			Socket incomming = null;
			
			while(true){
				incomming = serverSocket.accept();
				CountDownLatch latch = new CountDownLatch(1);
				Socket outgoing = new Socket("localhost", 9001);
				SocketWorker sw1 = new SocketWorker(incomming, outgoing, null, latch);
				SocketWorker sw2 = new SocketWorker(outgoing, incomming, null, latch);
				
				Thread t1 = new Thread(sw1);
				Thread t2 = new Thread(sw2);
				
				t1.start();
				t2.start();
				latch.countDown();
				incomming = null;
				outgoing = null;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
