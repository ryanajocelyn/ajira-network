/**
 * 
 */
package com.network.command.impl;

import com.network.command.Command;
import com.network.service.Network;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
public abstract class BaseCommand implements Command {

	protected String command;

	protected String[] commandTokens;
	
	protected Network network;
	
	public String run() {
		commandTokens = command.split(" ");
		
		if (isValidCommand()) {
			return execute();
		}
		
		return Constants.ERROR_INVALID_COMMAND_SYNTAX;
	}
	
	protected abstract boolean isValidCommand();
	
	protected abstract String execute();

	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(Network network) {
		this.network = network;
	}

	/**
	 * @return the commandArgs
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param commandArgs the commandArgs to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
