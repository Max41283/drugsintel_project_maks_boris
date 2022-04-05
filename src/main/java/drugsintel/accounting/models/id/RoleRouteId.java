package drugsintel.accounting.models.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class RoleRouteId implements Serializable {

    @Column(name = "route_id")
    private Long route_id;
    @Column(name = "role_id")
    private Long role_id;

}
