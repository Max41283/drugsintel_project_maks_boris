package drugsintel.accounting.service;

import drugsintel.accounting.dto.GetUserDto;
import drugsintel.accounting.dto.RegUserDto;

public interface UserService {

	boolean regUser(RegUserDto regUserDto);
	
	GetUserDto getUser(Long id);

	GetUserDto deleteUser(String userName);


}
