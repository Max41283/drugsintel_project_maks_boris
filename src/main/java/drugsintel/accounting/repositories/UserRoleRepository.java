package drugsintel.accounting.repositories;

import drugsintel.accounting.models.UserRole;
import drugsintel.accounting.models.id.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {



}
