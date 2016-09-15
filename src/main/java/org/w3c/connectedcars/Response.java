package org.w3c.connectedcars;

public class Response {
	private Integer status;
	private String stamp;
	private Signal signal;

	public Response(Integer status, String stamp, Signal signal) {
		super();
		this.status = status;
		this.stamp = stamp;
		this.signal = signal;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public Signal getSignal() {
		return signal;
	}
	public void setSignal(Signal signal) {
		this.signal = signal;
	}
}
