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

import com.network.NetworkCmdExecutor;
import com.network.utils.Constants;

/**
 * Test Case for the below network
 * 
 *          A1
 *        /    \
 *      A2     A3
 *              |
 *             R1 --- A4 --- A5
 *                    |       |
 *                    A6     A7
 * 
 * @author ABIJEETH
 *
 */
@RunWith(Parameterized.class)
public class Scenario1Test {

	private String command;
	
	private String expected;
	
	private static CommNetwork commNetwork;
	
	public Scenario1Test(String command, String expected) {
		this.command = command;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
			{ "ADD COMPUTER A1", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A1") },
			{ "ADD COMPUTER A2", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A2") },
			{ "ADD COMPUTER A3", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A3") },
			{ "ADD ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD PHONE A1", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD COMPUTER A1", Constants.ERROR_THAT_NAME_ALREADY_EXISTS },
			{ "ADD COMPUTER A4", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A4") },
			{ "ADD COMPUTER A5", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A5") },
			{ "ADD COMPUTER A6", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A6") },
			{ "ADD REPEATER R1", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "R1") },
			{ "SET_DEVICE_STRENGTH A1 HELLOWORLD", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "SET_DEVICE_STRENGTH A1 2", Constants.SUCCESS_SUCCESSFULLY_DEFINED_STRENGTH },
			{ "CONNECT A1 A2", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT A1 A3", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT A1 A1", Constants.ERROR_CANNOT_CONNECT_TO_ITSELF },
			{ "CONNECT A1 A2", Constants.ERROR_DEVICES_ALREADY_CONNECTED },
			{ "CONNECT A5 A4", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT R1 A2", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT R1 A5", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "CONNECT A1", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "CONNECT ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "CONNECT A8 A1", String.format(Constants.ERROR_NODE_DOESNOT_EXIST, "A8", "A1") },
			{ "CONNECT A2 A4", Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE },
			{ "INFO_ROUTE A1 A4", Constants.ERROR_ROUTE_NOT_FOUND },
			{ "INFO_ROUTE A1 A5", "A1 -> A2 -> R1 -> A5" },
			{ "INFO_ROUTE A4 A3", "A4 -> A5 -> R1 -> A2 -> A1 -> A3" },
			{ "INFO_ROUTE A1 A1", "A1 -> A1" },
			{ "INFO_ROUTE A1 A6", Constants.ERROR_ROUTE_NOT_FOUND },
			{ "INFO_ROUTE A2 R1", Constants.ERROR_ROUTE_CANNOT_BE_CALCULATED },
			{ "INFO_ROUTE A3 ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "INFO_ROUTE ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "INFO_ROUTE A1 A10", String.format(Constants.ERROR_NODE_DOESNOT_EXIST, "A1", "A10") }
		};
		
		return Arrays.asList(data);
	}
	
	@BeforeClass
	public static void setup() {
		commNetwork = new CommNetwork();
	}
	
	@Test
	public void testScenario1() {
		NetworkCmdExecutor executor = new NetworkCmdExecutor();
		
		String status = executor.runCommand(command, commNetwork);
		
		System.out.println(status);
		Assert.assertEquals(expected, status);
	}
}
