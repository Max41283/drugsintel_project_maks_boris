package drugsintel.accounting.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserActiveResponseDto {
	String userName;
	String email;
	String role;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate expiryDate;
	boolean isActive;
}
