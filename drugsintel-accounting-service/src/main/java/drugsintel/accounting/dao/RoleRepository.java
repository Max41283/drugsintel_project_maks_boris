package drugsintel.accounting.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import drugsintel.accounting.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
