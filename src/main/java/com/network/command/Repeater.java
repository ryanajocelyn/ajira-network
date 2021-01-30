/**
 * 
 */
package com.network.command;

import com.network.service.Node;

/**
 * @author ABIJEETH
 *
 */
public class Repeater extends Node {

	public Repeater(String nodeName) {
		super(nodeName, DeviceType.REPEATER);
	}
	
	public NodeParams transform(NodeParams param) {
		NodeParams newParam = new NodeParams();
		newParam.setStrength(param.getStrength() * 2);
		
		return newParam;
	}

}
