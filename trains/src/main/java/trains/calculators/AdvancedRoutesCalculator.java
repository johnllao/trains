package trains.calculators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import trains.TrainException;
import trains.models.Graph;
import trains.models.Route;
import trains.models.Town;
import trains.models.Trip;

/**
 * Calculates the possible routes / path from the source to the destination town, provided
 * that it satisfies the condition set in the filter
 * 
 * The filter condition can be expressed as
 * - the maximum number of stops (e.g. stops <= 3)
 * - the maximum number of distances (e.g. distance <= 30)
 */
public class AdvancedRoutesCalculator implements Calculator<List<Trip>> {

private Graph graph;
	
	/**
	 * Constructor
	 * @param graph
	 * @param conditionalFilter
	 * @throws TrainException 
	 */
	public AdvancedRoutesCalculator(final Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * Calculates the possible routes / path from the source to the destination town, provided
	 * that it satisfies the condition set in the filter
	 */
	@Override
	public List<Trip> calculate(final CalculatorInput input) throws TrainException {
		
		// get the source and destination town from the input
		final String[] path = input.getPath().split("-");
		if (path.length != 2)
			throw new TrainException(String.format("Invalid input path format '%s'", input));
			
		Town sourceTown = this.graph.getTown(path[0]);
		Town destinationTown = this.graph.getTown(path[1]);
		
		List<Trip> trips = new ArrayList<Trip>();
		
		final Filter conditionalFilter = input.getFilter();
		// Defines the limit to the stops/hops when evaluating the path. Normally you wont go beyond 
		// condition set in the filter
		final Filter limitFilter = new Filter(conditionalFilter.getProperty() + " <= " + conditionalFilter.getValue());
		
		// lets start with the first town, obviously the distance and stops starts at zero
		final Map<Town, Trip> breadcrumb = new HashMap<Town, Trip>();
		Trip trip = new Trip(sourceTown.getName(), 0, 0);		
		breadcrumb.put(sourceTown, trip);
		
		final Stack<Town> townsToProcess = new Stack<Town>();
		townsToProcess.push(sourceTown);
		
		while (!townsToProcess.isEmpty()) {
			
			Town currentTown = townsToProcess.pop();
		
			// keep record of the previous trip/path. it contains the breadcrumb information
			Trip previousTrip = new Trip(trip.getName(), trip.getStops(), trip.getDistance());
			
			// iterate into all the destinations
			for (Route route : currentTown.getRoutes()) {
				
				Town nextTown = route.getTown();
				
				// calculate the bread crumb, total stops and total distance as we traverse into the branch
				Trip currentTrip = new Trip(previousTrip.getName() + "-" + nextTown.getName(), 
						previousTrip.getStops() + 1,
						previousTrip.getDistance() + route.getDistance());
				
				breadcrumb.put(nextTown, currentTrip);
				
				// check if we have reached the limit into the hops, we dont want to
				// be looping endlessly
				if (!(limitFilter.evaluate(currentTrip)))
					continue;
				
				// check if we reached the destination
				if (nextTown.equals(destinationTown) && conditionalFilter.evaluate(currentTrip)) {
					trips.add(currentTrip);
				}
				
				townsToProcess.push(nextTown);
				
			}
			
		}
		
		return trips;
	}
	
	/**
	 * Recursive method that will evaluate all the path in the trip
	 * @param sourceTown
	 * @param destinationTown
	 * @param previousTown
	 * @param initialTrip
	 * @param trips
	 * @throws TrainException
	 */
	@SuppressWarnings("unused")
	private void findTrips(final Town sourceTown, final Town destinationTown, final Town previousTown, final Trip initialTrip, final Filter conditionalFilter, final Filter limitFilter, final List<Trip> trips) throws TrainException {
		
		// keep record of the previous trip/path. it contains the breadcrumb information
		Trip previousTrip = new Trip(initialTrip.getName(), initialTrip.getStops(), initialTrip.getDistance());
		
		// iterate into all the destinations
		for (Route route : sourceTown.getRoutes()) {
			
			Town nextTown = route.getTown();
			
			// calculate the bread crumb, total stops and total distance as we traverse into the branch
			Trip currentTrip = new Trip(previousTrip.getName() + "-" + nextTown.getName(), 
					previousTrip.getStops() + 1,
					previousTrip.getDistance() + route.getDistance());
			
			// check if we have reached the limit into the hops, we dont want to
			// be looping endlessly
			if (!(limitFilter.evaluate(currentTrip)))
				continue;
			
			// check if we reached the destination
			if (nextTown.equals(destinationTown) && conditionalFilter.evaluate(currentTrip)) {
				trips.add(currentTrip);
			}
			
			findTrips(nextTown, destinationTown, sourceTown, currentTrip, conditionalFilter, limitFilter, trips);
		}
	}
	
}
