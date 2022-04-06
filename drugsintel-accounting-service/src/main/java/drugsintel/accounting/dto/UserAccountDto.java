package drugsintel.accounting.dto;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import drugsintel.accounting.model.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAccountDto {
	String userName;
	String email;
	String role;
	Set<String> routeNames;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate expiryDate;
	 
}
