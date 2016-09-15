package org.w3c.connectedcars;

public class Signal {
	private String path;
	private String value;
	private String unit;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Signal(String path, String value, String unit) {
		super();
		this.path = path;
		this.value = value;
		this.unit = unit;
	}
}
