/**
 * 
 */
package com.network;

import java.util.Scanner;

import com.network.service.Network;

/**
 * @author ABIJEETH
 *
 */
public class NetworkMain {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// Local Communication Network
		Network commNetwork = new Network();
		try {
			CommandProcessor network = new CommandProcessor();

			// Scan and Run the Network Commands
			while (true) {
				String command = scanner.nextLine();
				
				if ("QUIT".equalsIgnoreCase(command)) {
					System.out.println("Successfully Signed Off. Bye !");
					break;
				} else {
					String status = network.runCommand(command, commNetwork);
					System.out.println(status);
				}
			}
		} catch (Exception e) {
			System.err.println("Error while processing: " + e.getMessage());
		} finally {
			scanner.close();
		}
	}
	
}
