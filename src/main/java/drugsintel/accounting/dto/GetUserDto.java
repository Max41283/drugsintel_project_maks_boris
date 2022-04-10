package drugsintel.accounting.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetUserDto {

    private String userName;
    private String email;
    private String roleName;
    private LocalDate expiryDate;

}
