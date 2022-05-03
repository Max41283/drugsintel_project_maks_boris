package drugsintel.accounting.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import drugsintel.accounting.security.filters.JwtRequestFilter;
import drugsintel.accounting.security.filters.RoleAccessFilter;
import drugsintel.accounting.security.jwt.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	UserDetailsService jwtUserDetailsService;
	JwtRequestFilter jwtRequestFilter;
	RoleAccessFilter roleAccessFilter;
	
	@Autowired
	public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter,
			RoleAccessFilter roleAccessFilter) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.jwtRequestFilter = jwtRequestFilter;
		this.roleAccessFilter = roleAccessFilter;
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		// Configure AuthenticationManager so that it knows 
		// from where to load user for matching credentials
		// Use BCryptPasswordEncoder
		authenticationManagerBuilder.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF
		httpSecurity.csrf().disable()
				// don't authenticate these particular requests
				.authorizeRequests()
					.antMatchers("/accounting/registation", "/accounting/login", "/accounting/refreshtoken").permitAll()
				// these particular requests need administrator's role
					.antMatchers("/accounting/admin/**")
						.hasRole("ADMIN")
				// all other requests need to be authenticated
					.anyRequest().authenticated()
				.and()
				.exceptionHandling()
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
				// session won't be used to store user's state
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		// Add a filter to check access rights
		httpSecurity.addFilterBefore(roleAccessFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
