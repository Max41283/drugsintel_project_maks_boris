package drugsintel.accounting.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserResponseDto {

    String userName;
    String email;
    String roleName;
    LocalDate updatedAt;

}
