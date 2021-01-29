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
public class SetDeviceStrengthCommandTest {

	private String command;
	
	private String expected;
	
	private static Network commNetwork;
	
	public SetDeviceStrengthCommandTest(String command, String expected) {
		this.command = command;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
			{ "SET_DEVICE_STRENGTH A1 2", Constants.SUCCESS_SUCCESSFULLY_DEFINED_STRENGTH },
			{ "SET_DEVICE_STRENGTH A1 10", Constants.SUCCESS_SUCCESSFULLY_DEFINED_STRENGTH },
			{ "SET_DEVICE_STRENGTH A2 20", Constants.SUCCESS_SUCCESSFULLY_DEFINED_STRENGTH },
			{ "SET_DEVICE_STRENGTH R1 10", Constants.ERROR_CANNOT_SET_STRENGTH_TO_REPEATER },
			{ "SET_DEVICE_STRENGTH R1 -1", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "SET_DEVICE_STRENGTH A3 10", String.format(Constants.ERROR_NODE_DOESNOT_EXIST_STRENGTH, "A3") },
			{ "SET_DEVICE_STRENGTH A2 -21", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "SET_DEVICE_STRENGTH ", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "SET_DEVICE_STRENGTH A1", Constants.ERROR_INVALID_COMMAND_SYNTAX },
			{ "SET_DEVICE_STRENGTH A1 A2", Constants.ERROR_INVALID_COMMAND_SYNTAX }
		};
		
		return Arrays.asList(data);
	}
	
	@BeforeClass
	public static void setup() {
		CommandProcessor executor = new CommandProcessor();
		
		commNetwork = new Network();
		executor.runCommand("ADD COMPUTER A1", commNetwork);
		executor.runCommand("ADD COMPUTER A2", commNetwork);
		executor.runCommand("ADD REPEATER R1", commNetwork);
	}
	
	@Test
	public void testAdd() {
		CommandProcessor executor = new CommandProcessor();
		
		String status = executor.runCommand(command, commNetwork);
		
		System.out.println(status);
		Assert.assertEquals(expected, status);
	}
}
