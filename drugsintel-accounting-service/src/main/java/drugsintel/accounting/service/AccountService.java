package drugsintel.accounting.service;

import org.springframework.http.ResponseEntity;

import drugsintel.accounting.dto.ChangeRoleDto;
import drugsintel.accounting.dto.UserActiveResponseDto;
import drugsintel.accounting.dto.UserRegisterDto;
import drugsintel.accounting.dto.UserUpdateDto;

public interface AccountService {
	
	void addUser(UserRegisterDto userRegisterDto);
	
	ResponseEntity<?> getUser(Long id);
	
	ResponseEntity<?> removeUser(Long id);
	
	ResponseEntity<?> updateUser(Long id, UserUpdateDto userUpdateDto);
	
	void changePassword(Long id, String newPassword);
	
	ResponseEntity<?> changeRole(ChangeRoleDto changeRoleDto);
	
	UserActiveResponseDto toggleActiveUser(String userName);

}
