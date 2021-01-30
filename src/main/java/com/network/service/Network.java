/**
 * 
 */
package com.network.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.network.command.Bridge;
import com.network.command.Computer;
import com.network.command.DeviceType;
import com.network.command.Repeater;
import com.network.utils.Constants;

/**
 * Main class containing the implementation of the commands
 * 
 * @author ABIJEETH
 *
 */
public class Network {

	public Map<String, Node> nodes;

	public Network() {
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
		// Duplicate Check
		if (this.nodes.containsKey(nodeName)) {
			return Constants.ERROR_THAT_NAME_ALREADY_EXISTS;
		}
		
		Node nodeToAdd = null;
		if (DeviceType.COMPUTER.name().equals(nodeType)) {
			nodeToAdd = new Computer(nodeName);
		} else if (DeviceType.REPEATER.name().equals(nodeType)) {
			nodeToAdd = new Repeater(nodeName);
		} else if (DeviceType.BRIDGE.name().equals(nodeType)) {
			nodeToAdd = new Bridge(nodeName);
		}
		
		if (nodeToAdd != null) {
			// Add Node
			this.nodes.put(nodeName, nodeToAdd);
			return String.format(Constants.SUCCESS_SUCCESSFULLY_ADDED_NODE, nodeName);
		}
	
		return Constants.ERROR_INVALID_COMMAND_SYNTAX;
	}

	public boolean hasNodes(List<String> nodesToConnect) {
		boolean hasAllNode = true;
		
		for (String conNodeName : nodesToConnect) {
			boolean hasNode = this.nodes.containsKey(conNodeName);
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
		List<Node> node1Network = node1.getChildren();
		if (node1Network.contains(node2)) {
			return Constants.ERROR_DEVICES_ALREADY_CONNECTED;
		}

		// Create Connection - Bi directional network
		node1Network.add(node2);
		node2.getChildren().add(node1);
		
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
		Node startNode = this.nodes.get(nodeName1);
		Node endNode = this.nodes.get(nodeName2);
		
		String error = canCalculateRoute(nodeName1, nodeName2);
		if (error != null) {
			return error;
		}
		
		NetworkTraverse traverse = new NetworkTraverse(startNode, endNode);
		traverse.startTraversal();

		Stack<Node> infoRoutesPath = traverse.getInfoRoutes();
		
		// Search the network for a path
//		infoRouteDFS(infoRoutesPath, infoRoutes, visitedNodes, startNode, endNode , 
//				startNode.getStrength());
		
		// Handle Same Node scenario
		if (startNode.equals(endNode)) {
			infoRoutesPath.push(startNode);
		}
		
		StringBuilder message = new StringBuilder(nodeName1);
		// Return the Path or Route not found
		String routePath = getRoute(infoRoutesPath, message);
		routePath = "".equalsIgnoreCase(routePath)? Constants.ERROR_ROUTE_NOT_FOUND : routePath;
		
		System.out.println(message);
		
		return routePath;
	}

	/**
	 * @param nodeName1
	 * @param nodeName2
	 * @param startNode
	 * @param endNode
	 * @return 
	 */
	private String canCalculateRoute(String nodeName1, String nodeName2) {
		Node startNode = this.nodes.get(nodeName1);
		Node endNode = this.nodes.get(nodeName2);

		List<Node> connections1 = startNode.getChildren();
		List<Node> connections2 = endNode.getChildren();
		
		// Return if no Direct Routes found
		if (connections1.isEmpty() || connections2.isEmpty()) {
			return Constants.ERROR_ROUTE_NOT_FOUND;
		}

		if (DeviceType.REPEATER.equals(startNode.getType())
				|| DeviceType.REPEATER.equals(endNode.getType())) {
			return Constants.ERROR_ROUTE_CANNOT_BE_CALCULATED;
		}
		
		return null;
	}

	/**
	 * @param infoRoutes
	 * @param message 
	 * @return
	 */
	private String getRoute(Stack<Node> infoRoutes, StringBuilder message) {
		return infoRoutes.stream()
				.map(irNode -> irNode.getName() )
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
		List<Node> snConnections = this.nodes.get(startNode.getName()).getChildren();
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
	
	public String infoRouteBFS(String nodeName1, String nodeName2) {
		Node startNode = this.nodes.get(nodeName1);
		Node endNode = this.nodes.get(nodeName2);
		
		String error = canCalculateRoute(nodeName1, nodeName2);
		if (error != null) {
			return error;
		}
		
		List<String> visitedNodes = new ArrayList<String>();
		Stack<Node> infoRoutes = new Stack<Node>();
		Stack<Node> infoRoutesPath = new Stack<Node>();
		
		Map<String, Integer> dist = new HashMap<>();
		Map<String, String> parent = new HashMap<>();
		
		// Handle Same Node scenario
		if (startNode.equals(endNode)) {
			infoRoutesPath.push(startNode);
			infoRoutesPath.push(endNode);
		} else {
			// Search the network for a path
			infoRouteBFS(dist, parent, visitedNodes, startNode);
			
			Node crawlNode = endNode;
			infoRoutes.add(crawlNode);
			int remStrength = startNode.getStrength();
			do {
				String pNode = parent.get(crawlNode.getName());
				
				crawlNode = this.nodes.get(pNode);
				if (crawlNode != null) {
					infoRoutes.add(crawlNode);
					remStrength --;
				}
				
				if (remStrength <= 0) {
					infoRoutes.clear();
					break;
				}
			} while (crawlNode != null);
			
			while (infoRoutes.isEmpty() == false) {
				infoRoutesPath.push(infoRoutes.pop());
			}
		}
		
		// Return the Path or Route not found
		String routePath = getRoute(infoRoutesPath, null);
		routePath = "".equalsIgnoreCase(routePath)? Constants.ERROR_ROUTE_NOT_FOUND : routePath;
				
		return routePath;
	}
	
	private void infoRouteBFS(Map<String, Integer> dist, Map<String, String> parent, List<String> visitedNodes,
			Node node) {
		
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(node);
		
		visitedNodes.add(node.getName());
		dist.put(node.getName(), 0);
		parent.put(node.getName(), null);
		
		while (queue.isEmpty() == false) {
			Node pNode = queue.remove();
			
			List<Node> children = this.nodes.get(pNode.getName()).getChildren();
			
			if (children != null && children.isEmpty() == false) {
				for (Node child : children) {
					
					if (visitedNodes.contains(child.getName()) == false) {
						dist.put(child.getName(), dist.get(pNode.getName()) + 1);
						parent.put(child.getName(), pNode.getName());
						visitedNodes.add(child.getName());
						
						queue.add(child);
					}
				}
			}
		}
		
		System.out.println(dist);
		System.out.println(parent);
	}

	/**
	 * Calculate the strength after each hop
	 * 
	 * @param child
	 * @param remStrength
	 * @return
	 */
	private int getRemainingStrength(Node child, int remStrength) {
		if (DeviceType.REPEATER.equals(child.getType())) {
			return remStrength * 2;
		}
		
		return remStrength - 1;
	}
}
