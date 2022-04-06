package drugsintel.accounting.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import drugsintel.accounting.dto.UserAccountDto;
import drugsintel.accounting.dto.UserRegisterDto;
import drugsintel.accounting.dto.UserTokenDto;
import drugsintel.accounting.dto.UserUpdateDto;
import drugsintel.accounting.service.AccountService;

@RestController
@RequestMapping("/accounting")
public class AccountController {
	
	AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/registation")
	public boolean register(@RequestBody UserRegisterDto userRegisterDto) {
		return accountService.addUser(userRegisterDto);
	}
	
	@PostMapping("/login")
	public UserTokenDto login(Principal principal) {
		return accountService.loginUser(principal.getName());
	}
	
	@GetMapping("/getuser/{id}")
	public UserAccountDto getUser(@PathVariable Long id) {
		return accountService.getUser(id);
	}
	
	@DeleteMapping("/deleteuser/{id}")
	public UserAccountDto deleteUser(@PathVariable Long id) {
		return accountService.removeUser(id);
	}
	
	@PutMapping("/updateuser/{id}")
	public UserAccountDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
		return accountService.updateUser(id, userUpdateDto);
	}

}
