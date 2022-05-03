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

import drugsintel.accounting.dao.AccountRepository;
import drugsintel.accounting.exceptions.EntityNotFoundException;
import drugsintel.accounting.model.Account;
import drugsintel.accounting.security.jwt.JwtTokenUtil;
import drugsintel.accounting.security.jwt.JwtUserDetailsService;
import drugsintel.accounting.security.jwt.UserProfile;

@Order(10)
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	JwtUserDetailsService jwtUserDetailService;
	JwtTokenUtil jwtTokenUtil;
	AccountRepository accountRepository;

	@Autowired
	public JwtRequestFilter(JwtUserDetailsService jwtUserDetailService,
			JwtTokenUtil jwtTokenUtil, AccountRepository accountRepository) {
		this.jwtUserDetailService = jwtUserDetailService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.accountRepository = accountRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String userName = null;
		String jwtToken = null;
		
		// Check the correctness of JWT
		
		if (requestTokenHeader != null) {
			if (requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				String errorMessage = jwtTokenUtil.chekJwtCorrect(jwtToken);
				if (errorMessage.isEmpty()) {
					userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
					
					// If JWT is correct, check user active condition
					
					if (isUserNotActive(jwtToken)) {
						responseError403(response, userName + " is not active");
						return;
					}
					
				} else {
					responseError403(response, errorMessage);
				}
			} else {
				responseError403(response, "JWT Token does not begin with Bearer String");
			}
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserProfile userDetails = (UserProfile) this.jwtUserDetailService.loadUserByUsername(userName);

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

	private boolean isUserNotActive(String jwtToken) {
		Long userId = jwtTokenUtil.getUserId(jwtToken);
		Account account = accountRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User " + userId));
		return !account.isActive();
	}

	private void responseError403(HttpServletResponse response, String errorMessage) throws IOException {
		logger.error(errorMessage);
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println("{ \"Unauthorized error\": \"" + errorMessage + "\" }");
	}

}
