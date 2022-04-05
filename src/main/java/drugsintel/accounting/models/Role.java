package drugsintel.accounting.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(schema = "sign", name = "role")
public class Role {

    @Id
    private Long id;

    @Column(name = "name")
    private String rolename;

}
