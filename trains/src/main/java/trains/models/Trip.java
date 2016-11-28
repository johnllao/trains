package trains.models;

import java.util.Objects;

/**
 * Tracks the trip details such as the path of the trip or route, 
 * the total number of stops and the total distance.
 * For example for a path A-D-E where distance is 5km each the trip 
 * will record it as A-D-E with 2 stops (e.g. A-D and D-E) and a
 * total distance of 10km
 */
public class Trip {
	private String name;
	private int stops;
	private int distance;
	
	/**
	 * Constructor
	 * @param name it represents the path
	 * @param stops total number of stops
	 * @param distance total distance
	 */
	public Trip(String name, int stops, int distance) {
		this.name = name;
		this.stops = stops;
		this.distance = distance;
	}
	
	/**
	 * Gets the name which represents the path of the trip
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name which represents the path of the trip
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the total stops
	 * @return
	 */
	public int getStops() {
		return stops;
	}

	/**
	 * Sets the total stops
	 * @param stops
	 */
	public void setStops(int stops) {
		this.stops = stops;
	}

	/**
	 * Gets the total distance
	 * @return
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Sets the total distance
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj == this) return true;
	    if (!(obj instanceof Trip)) return false;
	    
	    Trip town = (Trip) obj;
	    return town.getName().equals(this.name);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(this.name);
	}
	
	@Override
	public String toString() {
	    return String.format("%s [%s,%s]", this.name, this.stops, this.distance);
	}
}
