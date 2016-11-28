package trains.models;

/**
 * Destination details of the adjacent town containing the name of the next town 
 * and its distance from the source town
 */
public class Route {
	private Town town;
	private int distance;
	
	/**
	 * Constructor
	 * @param town destination town
	 * @param distance distance to the destination town
	 */
	public Route(final Town town, final int distance) {
		this.town = town;
		this.distance = distance;
	}
	
	/**
	 * Gets the destination town
	 * @return
	 */
	public Town getTown() {
		return town;
	}
	
	/**
	 * Gets the distance to the destination town
	 * @return
	 */
	public int getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
	    return town.getName() + "(" + String.valueOf(distance) + ")";
	}
}
