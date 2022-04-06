package drugsintel.accounting.service;

import drugsintel.accounting.dto.UserAccountDto;
import drugsintel.accounting.dto.UserRegisterDto;
import drugsintel.accounting.dto.UserTokenDto;
import drugsintel.accounting.dto.UserUpdateDto;

public interface AccountService {
	
	boolean addUser(UserRegisterDto userRegisterDto);
	
	UserTokenDto loginUser(String login);
	
	UserAccountDto getUser(Long id);
	
	UserAccountDto removeUser(Long id);
	
	UserAccountDto updateUser(Long id, UserUpdateDto userUpdateDto);

}
