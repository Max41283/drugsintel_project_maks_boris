package drugsintel.accounting.security.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import drugsintel.accounting.exceptions.UserAccessDeniedException;
import drugsintel.accounting.security.jwt.JwtUserDetailsService;
import drugsintel.accounting.security.jwt.UserProfile;

@Order(20)
@Service
public class RoleAccessFilter extends GenericFilterBean {
	
	JwtUserDetailsService jwtUserDetailService;
	
	@Autowired
	public RoleAccessFilter(JwtUserDetailsService jwtUserDetailService) {
		this.jwtUserDetailService = jwtUserDetailService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws UserAccessDeniedException, IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			UserProfile userDetails = (UserProfile) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			List<String> roles = userDetails.getAuthorities()
					.stream()
					.map(r -> r.toString().substring(5))
					.collect(Collectors.toList());
			if (!roles.contains("ADMIN")) {
				if (!userDetails.getRouteNames().contains(request.getServletPath())) {
					logger.error(userDetails.getUsername() + " - access denied!");
					response.sendError(403);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

}
