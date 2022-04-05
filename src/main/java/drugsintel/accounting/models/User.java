package drugsintel.accounting.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Entity
@ToString
@Table(schema = "sign", name = "user")
public class User {

    @Id
    @Column(name = "id")
    private Long user_id;

    private String username;
    private String email;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean active;

}
