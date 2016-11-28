package trains.calculators;

import trains.TrainException;
import trains.models.Graph;
import trains.models.Route;

/**
 * Calculates the total distance of the path. If one of the path are found to be invalid
 * an exception is thrown with a message NO SUCH ROUTE
 */
public class DistanceCalculator implements Calculator<Integer> {

	private Graph graph;
	
	/**
	 * Constructor
	 * @param graph
	 */
	public DistanceCalculator(final Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * Calculates the total distance of the path.If one of the path are found to be invalid
     * an exception is thrown with a message NO SUCH ROUTE.
     * 
     * @param input represents the path. The path has to be expressed with the hyphen sign, example A-B-C
	 */
	@Override
	public Integer calculate(final CalculatorInput input) throws TrainException {
		
		int distance = 0;
		// convert the path into an array
		final String[] path = input.getPath().split("-");
		if (path.length == 0)
			throw new TrainException(String.format("Invalid input path format '%s'", input));
		
		// the loop will evaluate 2 adjacent path per iteration and retrieve the distance between 
		// and increment it as the iteration goes on
		for (int i = 0; i < path.length - 1; i++) {
			String source = path[i];
			String destination = path[i + 1];
			
			Route route = this.graph.getTown(source).getRoute(destination);
			if (route == null)
				throw new TrainException("NO SUCH ROUTE");
			
			distance += route.getDistance();
		}
		
		return distance;
	}
	
}
