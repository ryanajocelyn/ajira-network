package com.network.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.network.command.NodeParams;

public class NetworkTraverse {

	private Stack<Node> infoRoutes; 
	private Set<String> visitedNodes;
	private boolean routeMatched = false;
	
	private Node startNode; 
	private Node endNode; 
	
	public NetworkTraverse(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
		
		infoRoutes = new Stack<Node>();
		visitedNodes = new HashSet<String>();
		routeMatched = false;
	}
	
	public void startTraversal() {
		
		NodeParams params = new NodeParams();
		params.setStrength(this.startNode.getStrength());
		params.setMessage(this.startNode.getName());
		
		NodeParams newParam = infoRouteDFS(this.startNode, params);
	}
	
	/**
	 * Recursive method to find the Route between 2 nodes
	 * 
	 * @param infoRoutes
	 * @param visitedNodes
	 * @param currNode
	 * @param endNode
	 * @param remStrength
	 * @return 
	 */
	private NodeParams infoRouteDFS(Node currNode, NodeParams param) {
		// Strength check based on Node types
		if (param.getStrength() <= 0) {
			return null;
		}

		infoRoutes.push(currNode);
		
		if (currNode.equals(endNode)) {
			routeMatched = true;
			return param;
		} 
		
		visitedNodes.add(currNode.getName());
		
		// Traverse the node for the matching end node
		List<Node> snConnections = currNode.getChildren();
		for (Node node : snConnections) {
			
			// Check if the child node is already visited
			if (visitedNodes.contains(node.getName()) == false) {
				if (currNode.getBlocked() == null || currNode.getBlocked().contains(node) == false) {
					NodeParams transformParam = node.transform(param);

					// Recursively search for non visited child node
					transformParam = infoRouteDFS(node, transformParam);
					if (routeMatched) return transformParam;
				}
			}
		}
		
		infoRoutes.pop();
		visitedNodes.remove(currNode.getName());
		
		return null;
	}

	/**
	 * @return the infoRoutes
	 */
	public Stack<Node> getInfoRoutes() {
		return infoRoutes;
	}

}