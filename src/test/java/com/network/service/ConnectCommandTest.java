/**
 * 
 */
package com.network.service;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.network.CommandProcessor;
import com.network.utils.Constants;

/**
 * @author ABIJEETH
 *
 */
@RunWith(Parameterized.class)
public class ConnectCommandTest {

	private String command;
	
	private String expected;
	
	private static Network commNetwork;
	
	public ConnectCommandTest(String command, String expected) {
		this.command = command;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
			{ "CONNECT A1 A2", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT A1 A1", Constants.ERROR_CANNOT_CONNECT_TO_ITSELF },
			{ "CONNECT A1 A3", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT A3 R1", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT R1 A4", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT R1 A5", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT A1 A2", Constants.ERROR_DEVICES_ALREADY_CONNECTED },
			{ "CONNECT R1 A5", Constants.ERROR_DEVICES_ALREADY_CONNECTED },
			{ "CONNECT A5 A6", String.format(Constants.ERROR_NODE_DOESNOT_EXIST, "A5", "A6") },
			{ "CONNECT A6 A7", String.format(Constants.ERROR_NODE_DOESNOT_EXIST, "A6", "A7") },
			{ "CONNECT ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "CONNECT A1", Constants.ERROR_INVALID_COMMAND_SYNTAX }
		};
		
		return Arrays.asList(data);
	}
	
	@BeforeClass
	public static void setup() {
		CommandProcessor executor = new CommandProcessor();
		
		commNetwork = new Network();
		executor.runCommand("ADD COMPUTER A1", commNetwork);
		executor.runCommand("ADD COMPUTER A2", commNetwork);
		executor.runCommand("ADD COMPUTER A3", commNetwork);
		executor.runCommand("ADD COMPUTER A4", commNetwork);
		executor.runCommand("ADD COMPUTER A5", commNetwork);
		executor.runCommand("ADD COMPUTER R1", commNetwork);
	}
	
	@Test
	public void testAdd() {
		CommandProcessor executor = new CommandProcessor();
		
		String status = executor.runCommand(command, commNetwork);
		
		System.out.println(status);
		Assert.assertEquals(expected, status);
	}
}
