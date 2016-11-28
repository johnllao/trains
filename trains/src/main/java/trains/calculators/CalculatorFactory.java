package trains.calculators;

import java.util.HashMap;
import java.util.Map;

import trains.Command;
import trains.models.Graph;

public class CalculatorFactory {
	private final Map<Command, Calculator<?>> calculators;
	
	private CalculatorFactory(final Graph graph) {
		this.calculators = new HashMap<Command, Calculator<?>>();
		this.calculators.put(Command.DISTANCE, new DistanceCalculator(graph));
		this.calculators.put(Command.SHORTEST, new DijkstraCalculator(graph));
		this.calculators.put(Command.TRIPS, new RoutesCalculator(graph));
	}
	
	public static CalculatorFactory get(final Graph graph) {
		return new CalculatorFactory(graph);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Calculator<T> getCalculator(final Command command) {
		return (Calculator<T>) this.calculators.get(command);
	}
}
