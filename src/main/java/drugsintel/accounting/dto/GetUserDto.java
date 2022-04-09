package drugsintel.accounting.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetUserDto {

    String userName;
    String email;
    String roleName;
    LocalDate expiryDate;

}
