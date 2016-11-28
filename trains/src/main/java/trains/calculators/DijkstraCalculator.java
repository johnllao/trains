package trains.calculators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import trains.TrainException;
import trains.models.Graph;
import trains.models.Route;
import trains.models.Town;

/**
 * Implementation of the Dijkstra algorithm. A popular algorithm in determining the
 * shortest path.
 * 
 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 */
public class DijkstraCalculator implements Calculator<Integer> {
	private Graph graph; 
	
	public DijkstraCalculator(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * Calculate the shortest path using the Dijkstra algorithm
	 */
	@Override
	public Integer calculate(final CalculatorInput input) throws TrainException {
		
		// get the source and destination town from the input
		final String[] path = input.getPath().split("-");
		if (path.length != 2)
			throw new TrainException(String.format("Invalid input path format '%s'", input));
		
		final Town sourceTown = this.graph.getTown(path[0]);
		final Town destinationTown = this.graph.getTown(path[1]);
		
		// the unvisited and visited serves as a staging to know which towns we have processed so far
		final Set<Town> unvisited = new HashSet<Town>();
		final Set<Town> visited = new HashSet<Town>();
		
		// the townDistances will contain the shortest distance from source to each of the towns
		final Map<Town, Integer> townDistances = new HashMap<Town, Integer>();
		// Initially we set all distances to each to to the MAX
		for (Town town : this.graph.getTowns().values()) {
			townDistances.put(town, Integer.MAX_VALUE);
		}
		
		// obviously the distance to the source town must be zero
		townDistances.put(sourceTown, 0);
		// now lets start evaluating from the source town
		unvisited.add(sourceTown);
		
		while (!unvisited.isEmpty()) {
			// fins the shortest distance from the unvisited list 
			Town shortestDistanceTown = findShortestDistanceTown(unvisited, townDistances);
			int shortestDistanceTownDistance = townDistances.get(shortestDistanceTown);
			
			visited.add(shortestDistanceTown);
			unvisited.remove(shortestDistanceTown);
			
			// iterate thru the adjacent towns
			for (Route route : shortestDistanceTown.getRoutes()) {
				Town nextTown = route.getTown();
				int nextTownDistance = route.getDistance();
				
				// skip if we already processed it
				if (visited.contains(nextTown))
					continue;
				
				// recalculate the distance accumulated if it is shorter then set it
				int nextTownCurrentDistance = townDistances.get(nextTown);
				int nextTownTentativeDistance = shortestDistanceTownDistance + nextTownDistance;
				if (nextTownTentativeDistance < nextTownCurrentDistance) {
					townDistances.put(nextTown, nextTownTentativeDistance);
					unvisited.add(nextTown);
				}
			}
		}
		
		return townDistances.get(destinationTown);
	}

	private static Town findShortestDistanceTown (final Set<Town> towns, final Map<Town, Integer> townDistances) {
		Town result = null;
		
		int shortestDistance = Integer.MAX_VALUE;
		for (Town town : towns) {
			int distance = townDistances.get(town);
			if (distance <= shortestDistance) {
				result = town;
				shortestDistance = distance;
			}
		}
		
		return result;
	}
}
