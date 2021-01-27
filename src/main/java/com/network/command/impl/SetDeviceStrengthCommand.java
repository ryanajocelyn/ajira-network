/**
 * 
 */
package com.network.command.impl;

import com.network.service.CommNetwork;
import com.network.service.Node;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
public class SetDeviceStrengthCommand extends BaseCommand {

	public SetDeviceStrengthCommand(CommNetwork network) {
		this.network = network;
	}
	
	/**
	 * Check for Command Syntax and Validations
	 */
	@Override
	protected boolean isValidCommand() {
		boolean isValid = false;
		
		if (commandTokens != null && commandTokens.length == 3) {
			String deviceStrength = commandTokens[2];
			
			if (deviceStrength != null && deviceStrength.matches("[0-9]+")) {
				isValid = true;
			}
		}
		
		return isValid;
	}

	/**
	 * Command Execution and Runtime Validations
	 */
	@Override
	protected String execute() {
		String retVal = null;
		
		Node node = network.searchNode(commandTokens[1]);
		if (node != null) {
			if ("COMPUTER".equalsIgnoreCase(node.getType())) {
				node.setStrength(Integer.parseInt(commandTokens[2]));

				retVal = Constants.SUCCESS_SUCCESSFULLY_DEFINED_STRENGTH;
			} else {
				retVal = Constants.ERROR_CANNOT_SET_STRENGTH_TO_REPEATER;
			}
		} else {
			retVal = String.format(Constants.ERROR_NODE_DOESNOT_EXIST_STRENGTH, 
					commandTokens[1]);
		}
		
		return retVal;
	}

}
