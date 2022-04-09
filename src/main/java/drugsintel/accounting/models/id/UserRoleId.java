package drugsintel.accounting.models.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRoleId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6826436654358918772L;

    private Long userId;
    private Long roleId;
    private LocalDateTime start;
    private LocalDateTime end;

}
