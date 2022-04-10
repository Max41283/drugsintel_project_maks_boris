package drugsintel.accounting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginUser {

    private Long userId;
    private String  token;

}
