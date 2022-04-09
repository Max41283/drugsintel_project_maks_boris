package drugsintel.accounting.controller;

import drugsintel.accounting.dto.UpdateUserRequestDto;
import drugsintel.accounting.dto.UpdateUserResponseDto;
import drugsintel.accounting.dto.GetUserDto;
import drugsintel.accounting.dto.RegUserDto;
import drugsintel.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
