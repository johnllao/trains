package trains;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Process the input file and extract the graph and the commands from the
 * input.
 * 
 * The input file uses the Properties file format which contains the following keys
 * - 'graph' key
 * - numeric keys that represents each of the commands to process
 * 
 * Example format of the input file
 * graph:AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7
 * 1:distance A-B-C
 * 2:distance A-D
 * 3:trips C-C stops <= 3
 * 4:shortest A-C
 */
public class Input {
	private String[] graph;
	private Map<Integer, String> commands;
	
	/**
	 * Private constructor, due to the parse factory methods
	 * @param graph
	 * @param commands
	 */
	private Input(final String[] graph, final Map<Integer, String> commands) {
		this.graph = graph;
		this.commands = commands;
	}

	/**
	 * Gets the graph array
	 * @return
	 */
	public String[] getGraph() {
		return graph;
	}

	/**
	 * Gets all the commands in the iput file
	 * @return
	 */
	public Map<Integer, String> getCommands() {
		return commands;
	}
	
	/**
	 * Parse the input file and extract the graph and command details from the
	 * input
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws TrainException
	 */
	public static Input parse(final String filename) throws IOException, TrainException {
		final FileInputStream inStream = new FileInputStream(filename);
		
		final Properties props = new Properties();
		props.load(inStream);
		inStream.close();
		
		final String graphPropertyValue = props.getProperty("graph");
		final String[] graph = graphPropertyValue.split(",");
		
		final Map<Integer, String> commands = new HashMap<Integer, String>();
		
		final Pattern commandPropertyPattern = Pattern.compile("^\\d+$");
		for (Entry<Object, Object> i : props.entrySet()) {
			Matcher matcher = commandPropertyPattern.matcher(i.getKey().toString());
			if (matcher.matches()) {
				commands.put(Integer.valueOf(i.getKey().toString()), i.getValue().toString());
			}
		}
		
		return new Input(graph, commands);
	}
}
