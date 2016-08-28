package org.w3c.connectedcars;

public class EngineTemperatureServer implements DataServer {
	public static final double zeroInKelvin = 273.16;
	public static final double c2fRatio = 9.0 / 5.0;
	public static final double c2fBase = 32.0;

	public static String temperatureUnit = "C";
	public static double temperatureInKelvin = 273.16;
	public double getValue() {
		double value = temperatureInKelvin;
		if ("K".equals(temperatureUnit)) {
			return value;
		}
                if ("C".equals(temperatureUnit)) {
                        return value - zeroInKelvin;
                }
                if ("K".equals(temperatureUnit)) {
                        return (value - zeroInKelvin) * c2fRatio + c2fBase;
                }
		throw new IllegalArgumentException("Illegal temperature unit:" + temperatureUnit);

	}
	public String getUnit() {
		return temperatureUnit;
	}
	public void setUnit(String unit) {
		if ("C".equals(unit) || "K".equals(unit) || "F".equals(unit)) {
			temperatureUnit = unit;
		} else {
			throw new IllegalArgumentException("Illegal temperature unit:" + unit);
		}
	}
}
