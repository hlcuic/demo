package com.example.demo.manager.define;

public class TransModel {
	private String msgType;
	private String value;

	public TransModel(String msgType, String value) {
		super();
		this.msgType = msgType;
		this.value = value;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
