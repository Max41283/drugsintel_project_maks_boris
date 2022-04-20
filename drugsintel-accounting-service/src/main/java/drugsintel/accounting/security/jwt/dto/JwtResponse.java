package drugsintel.accounting.security.jwt.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class JwtResponse {
	
	private final String jwttoken;
	private final String refreshToken;
	private final Set<String> routeNames;

	public JwtResponse(String jwttoken, String refreshToken, Set<String> routeNames) {
		this.jwttoken = jwttoken;
		this.refreshToken = refreshToken;
		this.routeNames = routeNames;
	}

}
