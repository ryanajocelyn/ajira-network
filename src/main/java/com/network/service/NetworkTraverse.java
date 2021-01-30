package com.network.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.network.command.Device;

public class NetworkTraverse {

	private Stack<Node> infoRoutes; 
	private List<String> visitedNodes;
	private boolean routeMatched = false;
	
	private Node startNode; 
	private Node endNode; 
	
	public NetworkTraverse(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
		
		infoRoutes = new Stack<Node>();
		visitedNodes = new ArrayList<String>();
		routeMatched = false;
	}
	
	public void startTraversal() {
		
		infoRouteDFS(this.startNode, this.startNode.getStrength());
	}
	
	/**
	 * Recursive method to find the Route between 2 nodes
	 * 
	 * @param infoRoutes
	 * @param visitedNodes
	 * @param currNode
	 * @param endNode
	 * @param remStrength
	 */
	private void infoRouteDFS(Node currNode, int remStrength) {
		// Strength check based on Node types
		if (remStrength <= 0) {
			return;
		}

		infoRoutes.push(currNode);
		
		if (currNode.equals(endNode)) {
			routeMatched = true;
			return;
		} 
		
		visitedNodes.add(currNode.getName());
		
		// Traverse the node for the matching end node
		List<Node> snConnections = currNode.getChildren();
		for (Node node : snConnections) {
			
			// Check if the child node is already visited
			if (visitedNodes.contains(node.getName()) == false) {
				int remainingStrength = getRemainingStrength(node, remStrength);
		
				// Recursively search for non visited child node
				infoRouteDFS(node, remainingStrength);
				if (routeMatched) return;
			}
		}
		
		infoRoutes.pop();
	}
	
	private int getRemainingStrength(Node child, int remStrength) {
		if (Device.REPEATER.equals(child.getType())) {
			return remStrength * 2;
		}
		
		return remStrength - 1;
	}

	/**
	 * @return the infoRoutes
	 */
	public Stack<Node> getInfoRoutes() {
		return infoRoutes;
	}

}