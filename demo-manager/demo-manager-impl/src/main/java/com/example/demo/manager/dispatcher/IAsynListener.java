package com.example.demo.manager.dispatcher;

import com.example.demo.manager.define.TransModel;
import com.example.demo.manager.interfaces.IEventHandler;

public interface IAsynListener {

	void registry(IEventHandler handler);

	boolean accpet(String msgType);

	void onEvent(TransModel model);

}
