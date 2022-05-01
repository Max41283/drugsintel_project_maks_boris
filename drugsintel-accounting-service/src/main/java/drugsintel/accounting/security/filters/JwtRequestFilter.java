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
import drugsintel.accounting.exceptions.UserAccessDeniedException;
import drugsintel.accounting.exceptions.UserNotActiveException;
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
			throws UserNotActiveException, UserAccessDeniedException, ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null) {
			if (requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				String errorMessage = jwtTokenUtil.chekJwtCorrect(jwtToken);
				if (errorMessage.isEmpty()) {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
					final Long userId = jwtTokenUtil.getUserId(jwtToken);
					Account account = accountRepository.findById(userId)
							.orElseThrow(() -> new EntityNotFoundException("User " + userId));
					if (!account.isActive()) {
						logger.error(username + " is not active");
						responseError403(response, username + " is not active");
						return;
					}
				} else {
					responseError403(response, errorMessage);
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

	private void responseError403(HttpServletResponse response, String errorMessage) throws IOException {
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println("{ \"Unauthorized error\": \"" + errorMessage + "\" }");
	}

}
