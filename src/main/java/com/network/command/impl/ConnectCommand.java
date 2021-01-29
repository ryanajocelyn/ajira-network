/**
 * 
 */
package com.network.command.impl;

import java.util.Arrays;
import java.util.List;

import com.network.service.Network;
import com.network.utils.Constants;

/**
 * Connect Command
 * 
 * @author ABIJEETH
 *
 */
public class ConnectCommand extends BaseCommand {

	public ConnectCommand(Network network) {
		this.network = network;
	}
	
	/**
	 * Check for Command Syntax and Validations
	 */
	@Override
	protected boolean isValidCommand() {
		boolean isValid = false;
		
		if (commandTokens != null && commandTokens.length == 3) {
			isValid = true;
		}
		
		return isValid;
	}

	/**
	 * Command Execution and Runtime Validations
	 */
	@Override
	protected String execute() {
		String retVal = null;
		
		List<String> nodesToConnect = Arrays.asList(new String[] { 
				commandTokens[1], commandTokens[2]});
		
		boolean hasNodes = network.hasNodes(nodesToConnect);
		if (hasNodes) {
			if (commandTokens[1].equalsIgnoreCase(commandTokens[2])) {
				retVal = Constants.ERROR_CANNOT_CONNECT_TO_ITSELF;	
			} else {
				retVal = network.connect(commandTokens[1], commandTokens[2]);
			}
		} else {
			retVal = String.format(Constants.ERROR_NODE_DOESNOT_EXIST, 
					commandTokens[1], commandTokens[2]);
		}
		
		return retVal;
	}

}
