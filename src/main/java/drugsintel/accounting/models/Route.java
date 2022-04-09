package drugsintel.accounting.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(schema = "sign", name = "route")
public class Route {

    @Id
    private Long id;

    @Column(name = "name")
    private String routeName;

}
