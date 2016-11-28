package trains.calculators;

public class CalculatorInput {
	
	private String path;
	private Filter filter;
	
	public CalculatorInput(final String path, final Filter filter) {
		this.path = path;
		this.filter = filter;
	}
	
	public String getPath() {
		return path;
	}
	public Filter getFilter() {
		return filter;
	}
}
