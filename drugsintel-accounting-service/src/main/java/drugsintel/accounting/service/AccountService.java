package drugsintel.accounting.service;

import drugsintel.accounting.dto.ChangeRoleDto;
import drugsintel.accounting.dto.UserAccountDto;
import drugsintel.accounting.dto.UserActiveDto;
import drugsintel.accounting.dto.UserRegisterDto;
import drugsintel.accounting.dto.UserUpdateDto;

public interface AccountService {
	
	void addUser(UserRegisterDto userRegisterDto);
	
	UserAccountDto getUser(Long id);
	
	UserAccountDto removeUser(Long id);
	
	UserAccountDto updateUser(Long id, UserUpdateDto userUpdateDto);
	
	void changePassword(Long id, String newPassword);
	
	UserAccountDto changeRole(ChangeRoleDto changeRoleDto);
	
	UserActiveDto toggleActiveUser(String userName);

}
