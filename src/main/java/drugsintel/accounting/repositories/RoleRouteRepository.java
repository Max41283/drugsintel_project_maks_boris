package drugsintel.accounting.repositories;

import drugsintel.accounting.models.RoleRoute;
import drugsintel.accounting.models.id.RoleRouteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRouteRepository extends JpaRepository<RoleRoute, RoleRouteId> {



}
