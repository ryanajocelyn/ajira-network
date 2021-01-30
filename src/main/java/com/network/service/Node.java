/**
 * 
 */
package com.network.service;

import java.util.ArrayList;
import java.util.List;

import com.network.command.DeviceType;
import com.network.command.NodeParams;
import com.network.utils.Constants;

/**
 * Data structure to store the Network
 * 
 * @author ABIJEETH
 *
 */
public abstract class Node {

	/** Node Name */
	private String name;
	
	/** Node Type - Computer, Repeater */
	private DeviceType type;
	
	/** Node Strength */
	private int strength;
	
	private List<Node> children;
	
	private List<Node> blocked;
	
	public abstract NodeParams transform(NodeParams param);
	
	public Node(String nodeName, DeviceType type) {
		this.name = nodeName;
		this.type = type;
		this.strength = Constants.DEFAULT_DEVICE_STRENGTH;
		this.children = new ArrayList<Node>();
	}
	
	public Node(String nodeName) {
		this (nodeName, null);
	}

	public void addBlockedNode(Node blockedNode) {
		if (blocked == null) {
			blocked = new ArrayList<Node>();
		}
		
		blocked.add(blockedNode);
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

	/**
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * @return the blocked
	 */
	public List<Node> getBlocked() {
		return blocked;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(List<Node> blocked) {
		this.blocked = blocked;
	}

	/**
	 * @return the type
	 */
	public DeviceType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DeviceType type) {
		this.type = type;
	}
}
