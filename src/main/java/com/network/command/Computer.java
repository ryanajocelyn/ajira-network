/**
 * 
 */
package com.network.command;

import com.network.service.Node;

/**
 * @author ABIJEETH
 *
 */
public class Computer extends Node {

	public Computer(String nodeName) {
		super(nodeName, DeviceType.COMPUTER);
	}

	public NodeParams transform(NodeParams param) {
		NodeParams newParam = new NodeParams();
		newParam.setStrength(param.getStrength() - 1);
		
		return newParam;
	}

}
