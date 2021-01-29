/**
 * 
 */
package com.network.command;

/**
 * @author ABIJEETH
 *
 */
public enum Device {

	COMPUTER("COMPUTER"),
	REPEATER("REPEATER");
	
	private String deviceName;
	
	private Device(String name) {
		this.deviceName = name;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
}
