package server;

import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;

import eventHandler.EventDispatcher;
import eventHandler.EventDispatcherAware;
import events.EventType;
import events.StringEvent;

public class PingTask extends TimerTask implements EventDispatcherAware {
	private EventDispatcher dispatcher = null;
	private ConcurrentLinkedDeque<String> IDList;

	public PingTask(EventDispatcher dispatcher, ConcurrentLinkedDeque<String> IDList){
		this.dispatcher = dispatcher;
		this.IDList = IDList;
	}
	
	@Override
	public EventDispatcher getEventDispatcher() {
		return this.dispatcher;
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}

	public void setIDList(ConcurrentLinkedDeque<String> IDList){
		this.IDList = IDList;
	}
	
	@Override
	public void run() {
		if(this.IDList.size() > 0){
			Iterator<String> i = this.IDList.iterator();
			try {
				while(i.hasNext()){
					StringEvent se = new StringEvent("",this, EventType.THREAD_PING,false, i.next());
					this.dispatcher.put(se);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
