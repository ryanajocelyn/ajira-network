/**
 * 
 */
package com.network;

import com.network.command.Command;
import com.network.command.CommandFactory;
import com.network.service.CommNetwork;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
public class NetworkCmdExecutor {

	/**
	 * Run the command
	 * 
	 * @param commandLine
	 * @param commNetwork
	 * @return
	 */
	public String runCommand(String commandLine, CommNetwork commNetwork) {
		Command command = CommandFactory.getCommand(commandLine, commNetwork);
		
		String status = Constants.ERROR_INVALID_COMMAND;
		if (command != null) {
			status = command.run();
		} 

		return status;
	}
}
