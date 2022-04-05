package drugsintel.accounting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetUserDto {

	String username;
	String email;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd@HH:mm:ss")
	LocalDateTime createdAt;
	@JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss")
	LocalDateTime updatedAt;

	boolean active;

}
