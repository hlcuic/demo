package com.example.demo.manager.dispatcher;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.example.demo.manager.define.EventContainer;
import com.example.demo.manager.define.TransModel;
import com.example.demo.manager.interfaces.IEventHandler;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class MessageAsynListener implements IAsynListener {

	private EventContainerProducer producer;

	private EventCracker eventCracker;

	@SuppressWarnings("unchecked")
	public MessageAsynListener() {
		this.eventCracker = new EventCracker();
		
        Executor executor = Executors.newCachedThreadPool();
        int bufferSize = 1024;
        Disruptor<EventContainer> disruptor = new Disruptor<>(EventContainer::new, bufferSize, executor);
        StringEventHandler stringEventHandler = new StringEventHandler();
        disruptor.handleEventsWith(stringEventHandler);
        disruptor.start();
        
        RingBuffer<EventContainer> ringBuffer = disruptor.getRingBuffer(); 
        producer = new EventContainerProducer(ringBuffer);
        
	}

	@Override
	public void registry(IEventHandler handler) {
		eventCracker.register(handler);
	}

	@Override
	public boolean accpet(String msgType) {
		return eventCracker.contains(msgType);
	}

	public class StringEventHandler implements EventHandler<EventContainer> {

		@Override
		public void onEvent(EventContainer event, long sequence, boolean endOfBatch) throws Exception {
			eventCracker.invoke(event.getModel());
		}

	}

	@Override
	public void onEvent(TransModel model) {
		producer.onEvent(model);
	}

}
