package drugsintel.accounting.dao;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import drugsintel.accounting.model.UserRole;
import drugsintel.accounting.model.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
	
	Optional<UserRole> findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(Long userId, Timestamp start, Timestamp end);
	
	Optional<UserRole> findByUserIdAndDateStartLessThanEqualAndDateEndGreaterThanEqualAndRoleIdNot(Long userId, Timestamp start, Timestamp end, Long roleId);
	
}
