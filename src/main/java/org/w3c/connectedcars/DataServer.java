package org.w3c.connectedcars;

/**
  * A generic data server
  */
public interface DataServer {
	/**
	  * Get data value
	  */
	public double getValue();
	/**
	  * Get data unit
	  */
	public String getUnit();
	/**
	  * Set data value, may throw IllegalArgumentException
	  */
	public void setUnit(String unit);
}
