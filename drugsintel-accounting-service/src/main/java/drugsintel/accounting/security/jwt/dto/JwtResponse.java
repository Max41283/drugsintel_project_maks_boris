package drugsintel.accounting.security.jwt.dto;

import java.io.Serializable;
import java.util.Set;

import drugsintel.accounting.model.Route;

public class JwtResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5851815719327534752L;
	private final String jwttoken;
	private Set<String> routeNames;

	public JwtResponse(String jwttoken, Set<String> routeNames) {
		this.jwttoken = jwttoken;
		this.routeNames = routeNames;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public Set<String> getRouteNames() {
		return this.routeNames;
	}

}
