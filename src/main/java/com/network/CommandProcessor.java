/**
 * 
 */
package com.network;

import com.network.command.Command;
import com.network.command.CommandFactory;
import com.network.exception.CommandNotFoundException;
import com.network.service.Network;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
public class CommandProcessor {

	/**
	 * Run the command
	 * 
	 * @param commandLine
	 * @param commNetwork
	 * @return
	 */
	public String runCommand(String commandLine, Network commNetwork) {
		Command command = null;
		String status = null;

		try {
			command = CommandFactory.getCommand(commandLine, commNetwork);
			
			if (command != null) {
				status = command.run();
			} 
		} catch (CommandNotFoundException e) {
			status = Constants.ERROR_INVALID_COMMAND;
			System.err.println("Error: Command not recognized: " + commandLine);
		}

		return status;
	}
}
