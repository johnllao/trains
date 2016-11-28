package trains.calculators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trains.TrainException;
import trains.models.Trip;

/**
 * Evaluates the filter expressed in string
 * 
 * Syntax:
 * [property name] [conditional operation] [value]
 * 
 * Example: 
 * stops >= 3
 * distance > 30
 * 
 * The only valid property names are
 * - stops
 * - distance
 * 
 */
public class Filter {
	
	private String property;
	private String operator;
	private String value;
	
	/**
	 * Constructor
	 * @param expression
	 * @throws TrainException 
	 */
	public Filter(String expression) throws TrainException {
		
		final String pattern = "(stops|distance)\\s(>|<|>=|<=|=)\\s(\\d+)";
		final Matcher matcher = Pattern.compile(pattern).matcher(expression);
		if (matcher.matches()) {
			this.property = matcher.group(1);
			this.operator = matcher.group(2);
			this.value = matcher.group(3);
		}
		else {
			throw new TrainException(String.format("Invalid filter expression '%s'. Verify the property and any white spaces in the expression", expression));
		}
	}
	
	/**
	 * Gets the property name
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Gets the operator
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Gets the value
	 * @return
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Evaluates the filter condition on the trip
	 * @param trip
	 * @return
	 * @throws TrainException
	 */
	public boolean evaluate(Trip trip) throws TrainException{
		
		int expressionValue = Integer.valueOf(this.value);
		int propertyValue = 0;
		if (this.property.equals("stops")) {
			propertyValue = trip.getStops();
		}
		else if (this.property.equals("distance")) {
			propertyValue = trip.getDistance();
		}
		
		if (this.operator.equals(">")) {
			return propertyValue > expressionValue;
		}
		else if (this.operator.equals("<")) {
			return propertyValue < expressionValue;
		}
		else if (this.operator.equals(">=")) {
			return propertyValue >= expressionValue;
		}
		else if (this.operator.equals("<=")) {
			return propertyValue <= expressionValue;
		}
		else if (this.operator.equals("=")) {
			return propertyValue == expressionValue;
		}
		else {
			throw new TrainException(String.format("Invalid operator '%s' in the filter expression", this.operator));
		}
	}

	
}
