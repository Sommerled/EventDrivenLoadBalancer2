package server.socketWorkers;

import java.net.ServerSocket;
import java.net.Socket;

import context.ConnectionContext;
import events.Event;
import events.EventType;

public class SocketContextEvent extends Event{
	private final Socket s;
	private final ConnectionContext cc;
	
	public SocketContextEvent(String id, Object originator, EventType et, boolean broadcast, Socket ss, ConnectionContext cc) {
		super(id, originator, et, broadcast);
		this.s = ss;
		this.cc = cc;
	}
	
	public Socket getSocket(){
		return this.s;
	}
	
	public ConnectionContext getConnectionContext(){
		return this.cc;
	}

}
