package drugsintel.accounting.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import drugsintel.accounting.exceptions.UserAccessDeniedException;
import drugsintel.accounting.security.jwt.JwtTokenUtil;
import drugsintel.accounting.security.jwt.JwtUserDetailsService;
import drugsintel.accounting.security.jwt.UserProfile;

@Order(10)
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	JwtUserDetailsService jwtUserDetailService;
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	public JwtRequestFilter(JwtUserDetailsService jwtUserDetailService, JwtTokenUtil jwtTokenUtil) {
		this.jwtUserDetailService = jwtUserDetailService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws UserAccessDeniedException, ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		Boolean userActive = null;

		if (requestTokenHeader != null) {
			if (requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				String errorMessage = jwtTokenUtil.chekJwtCorrect(jwtToken);
				if (errorMessage.isEmpty()) {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
					userActive = jwtTokenUtil.getUserActive(jwtToken);
					if (!userActive) {
						logger.error(username + " is not active");
						response.sendError(403);
						return;
					}
				} else {
//					logger.error("JWT Token rejected");
					response.setContentType("application/json");
			        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			        response.getOutputStream().println("{ \"Unauthorized error\": \"" + errorMessage + "\" }");
				}
			} else {
				logger.warn("JWT Token does not begin with Bearer String");
			}
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserProfile userDetails = (UserProfile) this.jwtUserDetailService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}
