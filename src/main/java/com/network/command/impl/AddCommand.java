/**
 * 
 */
package com.network.command.impl;

import org.apache.commons.lang3.EnumUtils;

import com.network.command.Device;
import com.network.service.Network;

/**
 * Add Command 
 * 
 * @author ABIJEETH
 *
 */
public class AddCommand extends BaseCommand {

	public AddCommand(Network network) {
		this.network = network;
	}
	
	/**
	 * Command Execution and Runtime Validations
	 */
	@Override
	public String execute() {
		String nodeType = commandTokens[1].toUpperCase();
		String nodeName = commandTokens[2];
		
		return network.add(nodeName, nodeType);
	}

	/**
	 * Check for Command Syntax and Validations
	 */
	@Override
	protected boolean isValidCommand() {
		boolean isValid = false;
		
		if (commandTokens != null && commandTokens.length == 3) {
			String networkType = commandTokens[1];
			String nodeName = commandTokens[2];
			
			if ( ( EnumUtils.isValidEnum(Device.class, networkType) )
					&& (nodeName != null && nodeName.length() > 0) ) {
				isValid = true;
			} 
		}
		
		return isValid;
	}

}
