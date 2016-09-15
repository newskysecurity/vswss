package org.w3c.connectedcars;

public class ActionItem {
	private Action action;
	private Signal signal;
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Signal getSignal() {
		return signal;
	}
	public void setSignal(Signal signal) {
		this.signal = signal;
	}
	public ActionItem(Action action, Signal signal) {
		super();
		this.action = action;
		this.signal = signal;
	}
}
