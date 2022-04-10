package drugsintel.accounting.controller;

import drugsintel.accounting.dto.*;
import drugsintel.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/accounting")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public boolean regUser(@RequestBody RegUserDto regUserDto) {
        return userService.regUser(regUserDto);
    }

    @PostMapping("/login/{login}")
    public LoginUser loginUser(@PathVariable String login) {
        return userService.loginUser(login);
    }

    @GetMapping("/getuser/{id}")
    public GetUserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/user/{username}")
    public GetUserDto deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @PutMapping("/update/{id}")
    public UpdateUserResponseDto updateDto(@PathVariable Long id, @RequestBody UpdateUserRequestDto userRequestDto) {
        return userService.updateUser(id, userRequestDto);
    }

    @PutMapping("/user/{username}/role/{role}")
    public UpdateUserResponseDto userResponseDto(@PathVariable("username") String userName, @PathVariable("role")  String userRole) {
        return userService.changeRole(userName, userRole);
    }

    @PutMapping("/password/{password}")
    public void changePassword(@PathVariable String password) {};

    @GetMapping("/dates/{from}/{to}")
    public Set<UpdateUserResponseDto> findByCreationDate(@PathVariable("from")LocalDate dateFrom, @PathVariable("to") LocalDate dateTo) {
        return userService.findByCreationDate(dateFrom, dateTo);
    }

    @GetMapping("/role/{role}")
    public Set<UpdateUserResponseDto> findByRole(@PathVariable String role) {
        return  userService.findByRole(role);
    }

}
