package drugsintel.accounting.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
	String userName;
	String email;
	String role;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate expiryDate;
	
	public UserResponseDto(String userName, String email, String role, LocalDate expiryDate) {
		this.userName = userName;
		this.email = email;
		this.role = role;
		this.expiryDate = expiryDate;
	}
	
}
