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
public class AddCommandTest {

	private String command;
	
	private String expected;
	
	private static Network commNetwork;
	
	public AddCommandTest(String command, String expected) {
		this.command = command;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
			{ "ADD COMPUTER A1", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A1") },
			{ "ADD COMPUTER A2", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "A2") },
			{ "ADD COMPUTER A1", Constants.ERROR_THAT_NAME_ALREADY_EXISTS },
			{ "ADD REPEATER R1", String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, "R1") },
			{ "ADD REPEATER R1", Constants.ERROR_THAT_NAME_ALREADY_EXISTS },
			{ "ADD ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD TEST A1", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD TEST A3", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD PHONE A4", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD COMPUTER", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "ADD A1", Constants.ERROR_INVALID_COMMAND_SYNTAX }
		};
		
		return Arrays.asList(data);
	}
	
	@BeforeClass
	public static void setup() {
		commNetwork = new Network();
	}
	
	@Test
	public void testAdd() {
		CommandProcessor executor = new CommandProcessor();
		
		String status = executor.runCommand(command, commNetwork);
		
		System.out.println(status);
		Assert.assertEquals(expected, status);
	}
}
