package drugsintel.accounting.models;

import drugsintel.accounting.models.id.UserRoleId;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(schema = "sign", name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @Id
    @Column(name = "role_start")
    private LocalDateTime start;

    @Id
    @Column(name = "role_end")
    private LocalDateTime end;

}
