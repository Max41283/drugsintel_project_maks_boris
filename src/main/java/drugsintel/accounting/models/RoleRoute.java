package drugsintel.accounting.models;

import drugsintel.accounting.models.id.RoleRouteId;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(schema = "sign", name = "role_route")
public class RoleRoute {

    @EmbeddedId
    private RoleRouteId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role roleId;

    @ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id")
    private Route routeId;

}
