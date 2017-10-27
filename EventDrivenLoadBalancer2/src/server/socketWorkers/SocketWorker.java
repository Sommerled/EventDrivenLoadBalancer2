package server.socketWorkers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SocketWorker implements Runnable{
	private Socket s1 = null;
	private Socket s2 = null;
	private WorkerListener listener = null;
	private CountDownLatch latch;
	
	public SocketWorker(Socket s1, Socket s2, WorkerListener listener, CountDownLatch latch){
		this.s1 = s1;
		this.s2 = s2;
		this.listener = listener;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			this.latch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//https://stackoverflow.com/questions/8274966/reading-a-byte-array-from-socket
		try {
			InputStream in = this.s1.getInputStream();
			int s = 0;
			while(s > -1){
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte buffer[] = new byte[1024];
				
				while((s=in.read(buffer)) != -1)
				{
					baos.write(buffer, 0, s);
					
					byte result[] = baos.toByteArray();
					baos.reset();
					
					
					if(!this.s2.isClosed()){
						this.s2.getOutputStream().write(result);
						this.s2.getOutputStream().flush();
					}else{
						break;
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(this.listener != null){
			this.listener.WorkerFinished();
		}else{
			try {
				synchronized(this.s1){
					if(!this.s1.isClosed()){
						
						this.s1.close();
					}
				}
				
				synchronized(this.s2){
					if(!this.s2.isClosed()){
						this.s2.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.s1 = null;
		this.s2 = null;
		
	}
}
