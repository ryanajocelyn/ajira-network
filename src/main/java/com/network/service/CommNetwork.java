/**
 * 
 */
package com.network.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.network.utils.Constants;

/**
 * Main class containing the implementation of the commands
 * 
 * @author ABIJEETH
 *
 */
public class CommNetwork {

	private Map<String, List<Node>> network;
	
	private Map<String, Node> nodes;
	
	public CommNetwork() {
		this.network = new HashMap<String, List<Node>>();
		this.nodes = new HashMap<String, Node>();
	}
	
	/**
	 * Add a node to the network
	 * 
	 * @param nodeName
	 * @param nodeType
	 * @return
	 */
	public String add(String nodeName, String nodeType) {
		Node nodeToAdd = new Node(nodeName, nodeType);
		
		// Duplicate Check
		if (network.containsKey(nodeName)) {
			return Constants.ERROR_THAT_NAME_ALREADY_EXISTS;
		}
		
		// Add Node
		network.put(nodeName, new ArrayList<Node>());
		nodes.put(nodeName, nodeToAdd);
		
		return String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, nodeName);
	}

	public boolean hasNodes(List<String> nodesToConnect) {
		boolean hasAllNode = true;
		
		for (String conNodeName : nodesToConnect) {
			boolean hasNode = this.network.containsKey(conNodeName);
			if (hasNode == false) {
				hasAllNode = false;
				break;
			}
		}
		
		return hasAllNode;
	}

	/**
	 * Connect two nodes in the network
	 * 
	 * @param nodeName1
	 * @param nodeName2
	 * @return
	 */
	public String connect(String nodeName1, String nodeName2) {
		Node node1 = this.nodes.get(nodeName1);
		Node node2 = this.nodes.get(nodeName2);

		// Check for existing connection
		List<Node> node1Network = this.network.get(nodeName1);
		if (node1Network.contains(node2)) {
			return Constants.ERROR_DEVICES_ALREADY_CONNECTED;
		}

		// Create Connection - Bi directional network
		node1Network.add(node2);
		this.network.get(nodeName2).add(node1);
		
		return Constants.SUCCESS_SUCCESSFULLY_CONNECTED_NODE;
	}

	public Node searchNode(String nodeName) {
		return this.nodes.get(nodeName);
	}
	
	/**
	 * Check for Info Route - Path between 2 nodes
	 * 
	 * @param nodeName1
	 * @param nodeName2
	 * @return
	 */
	public String infoRoute(String nodeName1, String nodeName2) {
		List<Node> connections1 = this.network.get(nodeName1);
		List<Node> connections2 = this.network.get(nodeName2);
		
		// Return if no Direct Routes found
		if (connections1.isEmpty() || connections2.isEmpty()) {
			return Constants.ERROR_ROUTE_NOT_FOUND;
		}

		Node startNode = this.nodes.get(nodeName1);

		List<String> visitedNodes = new ArrayList<String>();
		Stack<Node> infoRoutes = new Stack<Node>();
		Stack<Node> infoRoutesPath = new Stack<Node>();
		
		Node endNode = this.nodes.get(nodeName2);
		
		// Search the network for a path
		infoRouteDFS(infoRoutesPath, infoRoutes, visitedNodes, startNode, endNode , 
				startNode.getStrength());
		
		// Handle Same Node scenario
		if (startNode.equals(endNode)) {
			infoRoutesPath.push(startNode);
		}
		
		// Return the Path or Route not found
		String routePath = getRoute(infoRoutesPath);
		routePath = "".equalsIgnoreCase(routePath)? Constants.ERROR_ROUTE_NOT_FOUND : routePath;
				
		return routePath;
	}

	/**
	 * @param infoRoutes
	 * @return
	 */
	private String getRoute(Stack<Node> infoRoutes) {
		return infoRoutes.stream()
				.map(irNode -> irNode.getName())
				.reduce("", (accum, route) -> {
					String separator = accum.length() > 0? " -> ": "";
					return accum + separator + route;
				});
	}

	/**
	 * Recursive method to find the Route between 2 nodes
	 * 
	 * @param infoRoutesPath
	 * @param infoRoutes
	 * @param visitedNodes
	 * @param startNode
	 * @param endNode
	 * @param remStrength
	 */
	private void infoRouteDFS(Stack<Node> infoRoutesPath, Stack<Node> infoRoutes, 
			List<String> visitedNodes, Node startNode, 
			Node endNode, int remStrength) {
		infoRoutes.push(startNode);
		
		// Strength check based on Node types
		if (remStrength <= 0) {
			return;
		}
		
		if (startNode.equals(endNode)) {
			infoRoutes.forEach(n -> infoRoutesPath.push(n));
			return;
		}
		
		visitedNodes.add(startNode.getName());
		
		// Traverse the node for the matching end node
		List<Node> snConnections = this.network.get(startNode.getName());
		if (snConnections.size() > 0) {
			
			for (Node node : snConnections) {
				
				// Check if the child node is already visited
				if (visitedNodes.contains(node.getName()) == false) {
					int remainingStrength = getRemainingStrength(node, remStrength);
			
					// Recursively search for non visited child node
					infoRouteDFS(infoRoutesPath, infoRoutes, visitedNodes, 
							node, endNode, remainingStrength);
				}
			}
			
		}
		
		infoRoutes.pop();
	}
	
	/**
	 * Calculate the strength after each hop
	 * 
	 * @param child
	 * @param remStrength
	 * @return
	 */
	private int getRemainingStrength(Node child, int remStrength) {
		if ("REPEATER".equalsIgnoreCase(child.getType())) {
			return remStrength * 2;
		}
		
		return remStrength - 1;
	}
}
