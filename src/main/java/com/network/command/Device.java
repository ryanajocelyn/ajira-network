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
}
