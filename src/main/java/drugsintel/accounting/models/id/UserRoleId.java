package drugsintel.accounting.models.id;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class UserRoleId  implements Serializable {

    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "role_id")
    private Long role_id;
    @Column(name = "start")
    private LocalDateTime role_start;
    @Column(name = "end")
    private LocalDateTime role_end;

}
