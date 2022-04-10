package drugsintel.accounting.repositories;

import drugsintel.accounting.models.UserRole;
import drugsintel.accounting.models.id.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    Optional<UserRole> findByUserIdAndStartBeforeAndEndAfter(Long id, LocalDateTime currentTime1, LocalDateTime currentTime2);



}
