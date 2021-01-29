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
public class InfoRouteCommandTest {

	private String command;
	
	private String expected;
	
	private static Network commNetwork;
	
	public InfoRouteCommandTest(String command, String expected) {
		this.command = command;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
			{ "INFO_ROUTE A1 A4", "A1 -> A3 -> R1 -> A4" },
			{ "INFO_ROUTE A6 A5", "A6 -> A4 -> A5" },
			{ "INFO_ROUTE A6 A7", "A6 -> A4 -> A5 -> A7" },
			{ "INFO_ROUTE A2 A6", "A2 -> A1 -> A3 -> R1 -> A4 -> A6" },
			{ "INFO_ROUTE A1 A1", "A1 -> A1" },
			{ "INFO_ROUTE A4 A7", Constants.ERROR_ROUTE_NOT_FOUND },
			{ "INFO_ROUTE A4 A5", "A4 -> A5" },
			{ "INFO_ROUTE A4 A8", String.format(Constants.ERROR_NODE_DOESNOT_EXIST, "A4", "A8") },
			{ "INFO_ROUTE A1", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "INFO_ROUTE", Constants.ERROR_INVALID_COMMAND_SYNTAX }
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
		executor.runCommand("ADD COMPUTER A6", commNetwork);
		executor.runCommand("ADD COMPUTER A7", commNetwork);
		executor.runCommand("ADD REPEATER R1", commNetwork);
		executor.runCommand("CONNECT A1 A2", commNetwork);
		executor.runCommand("CONNECT A1 A3", commNetwork);
		executor.runCommand("CONNECT A3 R1", commNetwork);
		executor.runCommand("CONNECT R1 A4", commNetwork);
		executor.runCommand("CONNECT A4 A6", commNetwork);
		executor.runCommand("CONNECT A4 A5", commNetwork);
		executor.runCommand("CONNECT A5 A7", commNetwork);
		executor.runCommand("SET_DEVICE_STRENGTH A4 2", commNetwork);
	}
	
	@Test
	public void testInfoRoute() {
		CommandProcessor executor = new CommandProcessor();
		
		String status = executor.runCommand(command, commNetwork);
		
		System.out.println(status);
		Assert.assertEquals(expected, status);
	}
}
