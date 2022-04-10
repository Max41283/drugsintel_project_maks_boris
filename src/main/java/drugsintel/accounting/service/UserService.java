package drugsintel.accounting.service;

import drugsintel.accounting.dto.*;

import java.time.LocalDate;
import java.util.Set;

public interface UserService {

	boolean regUser(RegUserDto regUserDto);

	LoginUser loginUser(String login);
	
	GetUserDto getUser(Long id);

	UpdateUserResponseDto updateUser(Long id, UpdateUserRequestDto userRequestDto);

	GetUserDto deleteUser(String userName);

	UpdateUserResponseDto changeRole(String userName, String userRole);

	void changePassword(String password);

	Set<UpdateUserResponseDto> findByCreationDate(LocalDate dateFrom, LocalDate dateTo);

	Set<UpdateUserResponseDto> findByRole(String role);

}
