package drugsintel.accounting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RegUserDto {

    private String userName;
    private String email;
    private String password;

}
