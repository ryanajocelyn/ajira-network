/**
 * 
 */
package com.network.command.impl;

import com.network.service.Network;
import com.network.service.Node;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
public class SetFirewallCommand extends BaseCommand {

	public SetFirewallCommand(Network network) {
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
		
		Node node1 = network.searchNode(commandTokens[1]);
		Node node2 = network.searchNode(commandTokens[2]);
		if (node1 != null && node2 != null) {
			node2.addBlockedNode(node1);
		} else {
			retVal = String.format(Constants.ERROR_NODE_DOESNOT_EXIST_STRENGTH, 
					commandTokens[1]);
		}
		
		return retVal;
	}

}
