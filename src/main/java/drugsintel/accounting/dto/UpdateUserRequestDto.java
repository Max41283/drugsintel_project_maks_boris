package drugsintel.accounting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserRequestDto {

    private String username;
    private String email;

}