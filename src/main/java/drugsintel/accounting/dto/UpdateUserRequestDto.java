package drugsintel.accounting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserRequestDto {

    String username;
    String email;

}
