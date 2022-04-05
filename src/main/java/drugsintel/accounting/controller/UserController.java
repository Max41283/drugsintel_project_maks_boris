package drugsintel.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import drugsintel.accounting.dto.GetUserDto;
import drugsintel.accounting.service.UserService;

@RestController
@RequestMapping("/accounting")
public class UserController {

	UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/getuser/{id}")
	private GetUserDto getUser(@PathVariable Long id) {
		return service.getUser(id);
	}
}
