package trains.calculators;

import trains.TrainException;

/**
 * Calculator implements this interface
 * @param <O>
 */
public interface Calculator<O> {
	O calculate(CalculatorInput input) throws TrainException;
}
