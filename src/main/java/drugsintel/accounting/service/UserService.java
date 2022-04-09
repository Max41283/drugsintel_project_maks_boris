package drugsintel.accounting.service;

import drugsintel.accounting.dto.UpdateUserRequestDto;
import drugsintel.accounting.dto.UpdateUserResponseDto;
import drugsintel.accounting.dto.GetUserDto;
import drugsintel.accounting.dto.RegUserDto;

public interface UserService {

	boolean regUser(RegUserDto regUserDto);
	
	GetUserDto getUser(Long id);

	UpdateUserResponseDto updateUser(Long id, UpdateUserRequestDto userRequestDto);

	GetUserDto deleteUser(String userName);


}
