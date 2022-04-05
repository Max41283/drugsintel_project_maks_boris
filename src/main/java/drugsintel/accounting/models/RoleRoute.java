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
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @MapsId("route_id")
    @JoinColumn(name = "route_id")
    private Route route;

}
