package it.lucichkevin.cip.kalima;

import java.io.Serializable;

import it.lucichkevin.cip.Utils;

public abstract class Response implements Serializable {

	protected Request request;
//	protected Object data;	//	Implemented into subclass
    protected String error;
    protected int timestamp;
    protected float time;

    public void handleData(){
//		Utils.logger( Request.Callbacks.getPrefixLog(request) + " handleData() CALLED", Utils.LOG_INFO);
	}

	////////////////////////////////////////
	//	Getter and Setter

	public Request getRequest(){
		return request;
	}
	public void setRequest( Request request ){
		this.request = request;
	}

	public Object getData() {
		return null;
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public float getTime() {
		return time;
	}
	public void setTime(float time) {
		this.time = time;
	}

}
