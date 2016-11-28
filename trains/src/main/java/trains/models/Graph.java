package trains.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains details about all the towns and its destinations.
 * In computer science this data structure is represented as Directed Graph
 * https://en.wikipedia.org/wiki/Directed_graph
 */
public class Graph {
	private final Map<String, Town> towns;
	
	/**
	 * Constructor. Initializes the graph and populates all the towns
	 * @param input
	 */
	public Graph(final String[] input) {
		this.towns = new HashMap<String, Town>();
		
		// loop to each entry in the graph
		for (String i : input) {
			
			String sourceTownName = i.substring(0, 1);
			Town sourceTown = setupTown(sourceTownName);
			
			String destinationTownName = i.substring(1, 2);
			Town destinationTown = setupTown(destinationTownName);
			
			int distance = Integer.valueOf(i.substring(2, 3));
			
			sourceTown.addRoute(destinationTown, distance);
		}
	}
	
	/**
	 * Gets all the towns in the graph
	 * @return
	 */
	public Map<String, Town> getTowns() {
		return this.towns;
	}
	
	/**
	 * Gets a specific town in the graph
	 * @param name
	 * @return
	 */
	public Town getTown(final String name) {
		return this.towns.get(name);
	}
	
	private Town setupTown(final String townName) {
		if (!this.towns.containsKey(townName))
			this.towns.put(townName, new Town(townName));
		return this.towns.get(townName);
	}
}
