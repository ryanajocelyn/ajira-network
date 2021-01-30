/**
 * 
 */
package com.network.command;

import com.network.service.Node;

/**
 * @author ABIJEETH
 *
 */
public class Bridge extends Node {

	public Bridge(String nodeName) {
		super(nodeName, DeviceType.BRIDGE);
	}

	public NodeParams transform(NodeParams param) {
		NodeParams newParam = new NodeParams();
		newParam.setMessage(param.getMessage().toLowerCase());
		
		return newParam;
	}
}
