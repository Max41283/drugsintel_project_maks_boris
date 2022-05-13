package drugsintel.accounting.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import drugsintel.accounting.dto.CheckJwtStatusDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4532298408480123744L;

//	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Value("${jwt.expirations}")
	private Long jwtValidity;

	@Value("${jwt.secret.word}")
	private String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserProfile userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userDetails.getUserId());
		claims.put("role", userDetails.getAuthorities().toString());
		claims.put("userActive", userDetails.isUserActive());
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtValidity))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public Boolean validateToken(String token, UserProfile userDetails) {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public CheckJwtStatusDto chekJwtCorrect(String token) {
		CheckJwtStatusDto checkJwtStatusDto = new CheckJwtStatusDto(0, "");
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		} catch (SignatureException e) {
			checkJwtStatusDto.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
			checkJwtStatusDto.setErrorMessage(e.getMessage());
		} catch (MalformedJwtException e) {
			checkJwtStatusDto.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
			checkJwtStatusDto.setErrorMessage(e.getMessage());
		} catch (ExpiredJwtException e) {
			checkJwtStatusDto.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
			checkJwtStatusDto.setErrorMessage(e.getMessage());
		} catch (UnsupportedJwtException e) {
			checkJwtStatusDto.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
			checkJwtStatusDto.setErrorMessage(e.getMessage());
		} catch (IllegalArgumentException e) {
			checkJwtStatusDto.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
			checkJwtStatusDto.setErrorMessage(e.getMessage());
		}
		return checkJwtStatusDto;
	}
	
	public Long getUserId(String token) {
		return Long.parseLong(
				Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.get("userId")
				.toString()
				);
	}
	
	public String getRole(String token) {
		String role = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.get("role")
				.toString();
		return role.substring(6, role.length() - 1);
	}
	
	public Boolean getUserActive(String token) {
		return Boolean.valueOf(Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.get("userActive")
				.toString());
	}
	
}
