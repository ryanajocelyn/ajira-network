/**
 * 
 */
package com.network.command;

/**
 * @author ABIJEETH
 *
 */
public enum DeviceType {

	COMPUTER("COMPUTER"),
	REPEATER("REPEATER"),
	BRIDGE("BRIDGE");
	
	private String deviceName;
	
	private DeviceType(String name) {
		this.deviceName = name;
	}
}
