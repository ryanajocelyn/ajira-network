/**
 * 
 */
package com.network.command;

/**
 * @author ABIJEETH
 *
 */
public enum CommandEnum {
	ADD ("Add"),
	CONNECT ("Connect"),
	INFO_ROUTE ("InfoRoute"),
	SET_DEVICE_STRENGTH ("SetDeviceStrength"),
	SET_FIREWALL("SetFirewall");

	private String command;
	private CommandEnum(String command) {
		this.command = command;
	}
	
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
