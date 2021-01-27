/**
 * 
 */
package com.network.service;

import com.network.utils.Constants;

/**
 * Data structure to store the Network
 * 
 * @author ABIJEETH
 *
 */
public class Node {

	/** Node Name */
	private String name;
	
	/** Node Type - Computer, Repeater */
	private String type;
	
	/** Node Strength */
	private int strength;
	
	public Node(String nodeName, String type) {
		this.name = nodeName;
		this.type = type;
		
		this.strength = Constants.DEFAULT_DEVICE_STRENGTH;
	}
	
	public Node(String nodeName) {
		this (nodeName, null);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean equal = false;
		
		if (obj != null && obj instanceof Node) {
			String otherName = ((Node) obj).getName();
			if (otherName != null && otherName.equalsIgnoreCase(name)) {
				equal = true;
			}
		}
		
		return equal;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	@Override
	public String toString() {
		return String.format("%s : %s", name, type);
	}
}
