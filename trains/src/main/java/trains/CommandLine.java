package trains;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trains.calculators.Filter;

/**
 * Parse the command line from the input file
 */
public class CommandLine {
	private Command command;
	private String path;
	private Filter filter;
	
	private CommandLine(final Command command, final String path, final Filter filter)  {
		this.command = command;
		this.path = path;
		this.filter = filter;
	}
	
	public Command getCommand() {
		return command;
	}
	public String getPath() {
		return path;
	}
	public Filter getFilter() {
		return filter;
	}
	
	/**
	 * Parse the command line from the input file
	 * @param input
	 * @return
	 * @throws TrainException
	 */
	public static CommandLine parse(final String input) throws TrainException {
		// valid regex pattern for the command 
		final String commandPattern = "^(distance|trips|shortest)\\s([A-Z\\-]*)(.*)$";
		final Matcher commandMatcher = Pattern.compile(commandPattern).matcher(input);
		// check if the command text matches the regex pattern and create the appropriate command line object
		if (commandMatcher.matches()) {
			Command command = Command.UNKNOWN;
			switch(commandMatcher.group(1)) {
				case "distance" : 
					command = Command.DISTANCE;
					break;
				case "trips" : 
					command = Command.TRIPS;
					break;
				case "shortest" : 
					command = Command.SHORTEST;
					break;
					
			}
			String path = commandMatcher.group(2);
			// check if filter is provided in the command line, null if its not provided
			Filter filter = null;
			if (commandMatcher.group(3) != null && commandMatcher.group(3).length() > 0) {
				filter = new Filter(commandMatcher.group(3).trim());
			}
			return new CommandLine(command, path, filter);
		}
		else {
			throw new TrainException(String.format("Invalid command and/or expression '%s'", input));
		}
	}
	
}
