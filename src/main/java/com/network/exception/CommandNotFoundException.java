/**
 * 
 */
package com.network.exception;

/**
 * @author ABIJEETH
 *
 */
public class CommandNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommandNotFoundException(String message, Throwable e) {
		super(message, e);
	}
	
}
