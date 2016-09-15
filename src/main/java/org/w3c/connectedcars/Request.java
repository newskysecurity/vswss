package org.w3c.connectedcars;

public class Request {
	private String auth;
	private String stamp;
	private ActionItem item;
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public ActionItem getItem() {
		return item;
	}
	public void setItem(ActionItem item) {
		this.item = item;
	}
	public Request(String auth, String stamp, ActionItem item) {
		super();
		this.auth = auth;
		this.stamp = stamp;
		this.item = item;
	}
}
