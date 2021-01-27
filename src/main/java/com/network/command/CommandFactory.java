/**
 * 
 */
package com.network.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.network.service.CommNetwork;

/**
 * Factory Pattern
 * 
 * @author ABIJEETH
 *
 */
public class CommandFactory {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Command getCommand(String command, CommNetwork commNetwork) {
		Command retCmd = null;
		String[] commandTokens = command.split(" ");
		if (commandTokens != null && commandTokens.length > 0) {
			String cmdString = commandTokens[0].trim();
			
			// Get the Command Enum
			CommandEnum cmdEnum = getCommandEnum(cmdString);
			
			if (cmdEnum != null) {
				try {
					// Initialize the Command
					StringBuilder builder = new StringBuilder();
					builder.append("com.network.command.impl.");
					builder.append(cmdEnum.getCommand());
					builder.append("Command");
					
					Class cmdClass = Class.forName(builder.toString());
					retCmd = (Command) cmdClass.getDeclaredConstructors()[0].newInstance(commNetwork);
					
					Method setCmdMethod = cmdClass.getSuperclass().getDeclaredMethod("setCommand", String.class);
					setCmdMethod.invoke(retCmd, command);
					
				} catch (ClassNotFoundException | InstantiationException 
						| IllegalAccessException | IllegalArgumentException 
						| InvocationTargetException | SecurityException 
						| NoSuchMethodException e) {
					System.err.println("Error in Creating command: %s" + e.getMessage());
				}
			}
		}
		
		return retCmd;
	}

	private static CommandEnum getCommandEnum(String cmdString) {
		CommandEnum cmdEnum = null;
		
		try {
			cmdEnum = Enum.valueOf(CommandEnum.class, cmdString.toUpperCase());
		} catch (Exception e1) {
			// Error Message handled in invoker
		}
		
		return cmdEnum;
	}
}
