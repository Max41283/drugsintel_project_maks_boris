package drugsintel.accounting.controller;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import drugsintel.accounting.security.jwt.UserProfile;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@GetMapping()
	public String getDashboard(Principal principal) {
		UserProfile userDetails = (UserProfile) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userDetails.getUsername();
	}

}
