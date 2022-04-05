package drugsintel.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drugsintel.accounting.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



}
