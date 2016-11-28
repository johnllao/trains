package trains;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import trains.calculators.Calculator;
import trains.calculators.CalculatorFactory;
import trains.calculators.CalculatorInput;
import trains.models.Graph;
import trains.models.Route;
import trains.models.Town;
import trains.models.Trip;

/**
 * Process each of the command
 */
public class Commands {
	private Graph graph;
	private Map<Command, Function<CalculatorInput, String>> processCommands;
	private CalculatorFactory calcFactory;
	
	/**
	 * Constructor
	 * @param graph
	 */
	public Commands(final Graph graph) {
		this.graph = graph;
		this.processCommands = new HashMap<Command, Function<CalculatorInput, String>>();
		this.processCommands.put(Command.DISTANCE, this::distance);
		this.processCommands.put(Command.TRIPS, this::noOfRoutes);
		this.processCommands.put(Command.SHORTEST, this::shortestDistance);
		this.calcFactory = CalculatorFactory.get(this.graph);
	}
	
	/**
	 * Process each commands 
	 * @param commands
	 * @return
	 * @throws TrainException
	 */
	public Map<Integer, String> process(final Map<Integer, String> commands) throws TrainException {
		
		final Map<Integer, String> result = new HashMap<Integer, String>();
		
		for (Map.Entry<Integer, String> i : commands.entrySet()) {
			result.put(i.getKey(), process(i.getValue()));
		}
		
		return result;
		
	}
	
	/**
	 * Process a particular command expressed in a string
	 * @param command
	 * @return
	 * @throws TrainException
	 */
	public String process(final String command) throws TrainException {
		
		String result = "";
		
		final CommandLine commandLine = CommandLine.parse(command);
		if(commandLine.getCommand() != Command.UNKNOWN) {
			final CalculatorInput input = new CalculatorInput(commandLine.getPath(), commandLine.getFilter());
			result = this.processCommands.get(commandLine.getCommand()).apply(input);
		}
		
		return result;
		
	}
	
	/**
	 * Returns the expected output string to get the distance.
	 * If there are any exception/errors while getting the distance such as 'no route',
	 * the error message will be returned
	 * @param path
	 * @return
	 */
	private String distance(final CalculatorInput input) {
		try {
			Calculator<Integer> calc = this.calcFactory.<Integer>getCalculator(Command.DISTANCE);
			return calc.calculate(input).toString();
		}
		catch (TrainException e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Returns the expected output string to get the number of trips.
	 * If there are any exception/errors while getting the trips
	 * the error message will be returned
	 * @param path
	 * @param filter
	 * @return
	 */
	private String noOfRoutes(final CalculatorInput input) {
		try {
			Calculator<List<Trip>> calc = this.calcFactory.<List<Trip>>getCalculator(Command.TRIPS);
			return String.valueOf(calc.calculate(input).size());
		}
		catch (TrainException e) {
			return e.getMessage();
		}
	}
	
	/**
 	 * Returns the expected output string to get the shortest distance.
	 * If there are any exception/errors while getting the shortest distance
	 * the error message will be returned
 	 * @param path
 	 * @return
 	 */
	private String shortestDistance(final CalculatorInput input) {
		final String[] p = input.getPath().split("-");
		final Town sourceTown = this.graph.getTown(p[0]);
		final Town destinationTown = this.graph.getTown(p[1]);
		
		String result = String.valueOf(Integer.MAX_VALUE);
		
		try {
			Calculator<Integer> calc = this.calcFactory.<Integer>getCalculator(Command.SHORTEST);
			if (sourceTown.equals(destinationTown)) {
				
				int shortestDistance = Integer.MAX_VALUE;
				for (Route route : sourceTown.getRoutes()) {
					
					Town nextTown = route.getTown();
					int nextTownDistance = route.getDistance();
					
					CalculatorInput calcInput = new CalculatorInput(String.format("%s-%s", nextTown.getName(), destinationTown.getName()), input.getFilter());
					int distance = calc.calculate(calcInput) + nextTownDistance;
					if (distance <= shortestDistance)
						shortestDistance = distance;
				}
				result = String.valueOf(shortestDistance);
			}
			else {
				result = calc.calculate(input).toString();
			}
				
		}
		catch (TrainException e) {
			result = e.getMessage();
		}
		
		return result;
	}
}
