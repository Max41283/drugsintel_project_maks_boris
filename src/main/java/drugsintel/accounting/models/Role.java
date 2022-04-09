package drugsintel.accounting.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "roleId")
@Table(schema = "sign", name = "role")
public class Role {

    @Id
    @Column(name = "id")
    private Long roleId;

    @Column(name = "name")
    private String roleName;

    @ManyToMany
    @JoinTable(schema = "sign", name = "role_route",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    Set<Route> routes;

}
