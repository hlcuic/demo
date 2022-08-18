package com.example.demo.manager.dispatcher;

import com.example.demo.manager.define.EventContainer;
import com.example.demo.manager.define.TransModel;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

// 队列生产者
public class EventContainerProducer {

	private final RingBuffer<EventContainer> ringBuffer;

	public EventContainerProducer(RingBuffer<EventContainer> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onEvent(TransModel model) {
		ringBuffer.publishEvent(TRANSLATOR, model);
	}

	private static final EventTranslatorOneArg<EventContainer, TransModel> TRANSLATOR = new EventTranslatorOneArg<EventContainer, TransModel>() {
		public void translateTo(EventContainer event, long sequence, TransModel model) {
			event.setModel(model);
		}
	};

}
