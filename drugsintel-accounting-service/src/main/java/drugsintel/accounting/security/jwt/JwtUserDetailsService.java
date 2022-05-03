package drugsintel.accounting.security.jwt;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import drugsintel.accounting.dao.AccountRepository;
import drugsintel.accounting.dao.RoleRepository;
import drugsintel.accounting.dao.UserRoleRepository;
import drugsintel.accounting.exceptions.EntityNotFoundException;
import drugsintel.accounting.exceptions.UserNotActiveException;
import drugsintel.accounting.model.Account;
import drugsintel.accounting.model.Role;
import drugsintel.accounting.model.UserRole;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	AccountRepository accountRepository;
	UserRoleRepository userRoleRepository;
	RoleRepository roleRepository;
	
	@Autowired
	public JwtUserDetailsService(AccountRepository accountRepository, UserRoleRepository userRoleRepository,
			RoleRepository roleRepository) {
		this.accountRepository = accountRepository;
		this.userRoleRepository = userRoleRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserNotActiveException {
		
		Account userAccount = accountRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		if (!userAccount.isActive()) {
			throw new UserNotActiveException(userAccount.getUserName());
		}
		
		Role role = findActualRole(userAccount.getId());
		String[] roles = new String[] {"ROLE_" + role.getRoleName().toUpperCase()};
		
		return new UserProfile(
				username,
				userAccount.getPassword(),
				AuthorityUtils.createAuthorityList(roles),
				userAccount.getId(),
				role.getRoutes()
					.stream()
					.map(r -> r.getRouteName())
					.collect(Collectors.toSet()),
				userAccount.isActive()
					);
	}

	private Role findActualRole(Long id) {
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		UserRole userRole = userRoleRepository
				.findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqualAndRoleIdNot(
						id, now, now, roleRepository.findByRoleName("USER").get().getId())
				.orElse(null);
		if (userRole == null) {
			userRole = userRoleRepository
					.findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(id, now, now)
					.orElseThrow(() -> new EntityNotFoundException("Actual USER-role for User " + id));
		}
		return roleRepository.findById(userRole.getRoleId()).get();
	}

}
