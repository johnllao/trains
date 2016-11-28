package trains.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Holds the details about the town including the name that represents the
 * town and all the destinations from the towns represented as routes. 
 */
public class Town {
	private String name;
	private Map<String, Route> routes;
	
	/**
	 * Constructor
	 * @param name name of the town
	 */
	public Town(final String name) {
		this.name = name;
		this.routes = new HashMap<String, Route>();
	}
	
	/**
	 * Gets the name of the town
	 * @return
	 */
	public String getName() {
		return this.name;
	} 
	
	/**
	 * Gets the all the destinations from the town
	 * @return
	 */
	public Collection<Route> getRoutes() {
		return this.routes.values();
	}
	
	/**
	 * Gets the specific destination 
	 * @param name name of the destination town
	 * @return returns the route details
	 */
	public Route getRoute(final String name) {
		return this.routes.get(name);
	}
	
	/**
	 * Add route to this town
	 * @param route
	 */
	public void addRoute(final Route route) {
		final String name = route.getTown().getName();
		routes.put(name, route);
	}
	
	/**
	 * Add route to this town
	 * @param town
	 * @param distance
	 */
	public void addRoute(final Town town, final int distance) {
		final Route route = new Route(town, distance);
		this.addRoute(route);
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == this) return true;
	    if (!(obj instanceof Town)) return false;
	    
	    Town town = (Town) obj;
	    return town.getName().equals(this.name);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(this.name);
	}
	
	@Override
	public String toString() {
	    return this.name;
	}
}
