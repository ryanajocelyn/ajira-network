/**
 * 
 */
package com.network.command.impl;

import java.util.Arrays;
import java.util.List;

import com.network.service.CommNetwork;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
public class InfoRouteCommand extends BaseCommand {

	public InfoRouteCommand(CommNetwork network) {
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
			retVal = network.infoRoute(commandTokens[1], commandTokens[2]);
		} else {
			retVal = String.format(Constants.ERROR_NODE_DOESNOT_EXIST, 
					commandTokens[1], commandTokens[2]);
		}
		
		return retVal;
	}

}
