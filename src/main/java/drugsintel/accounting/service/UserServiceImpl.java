package drugsintel.accounting.service;

import drugsintel.accounting.dto.GetUserDto;
import drugsintel.accounting.dto.RegUserDto;
import drugsintel.accounting.exceptions.RoleNotFoundException;
import drugsintel.accounting.exceptions.UserAlreadyExistsException;
import drugsintel.accounting.exceptions.UserNotFoundException;
import drugsintel.accounting.models.Role;
import drugsintel.accounting.models.User;
import drugsintel.accounting.models.UserRole;
import drugsintel.accounting.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    RoleRepository roleRepository;
    RoleRouteRepository roleRouteRepository;
    RouteRepository routeRepository;
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    ModelMapper modelMapper;

    @Value("${period.userRolePeriod:50}")
    Long userRolePeriod;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, RoleRouteRepository roleRouteRepository,
                           RouteRepository routeRepository, UserRepository userRepository,
                           UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.roleRouteRepository = roleRouteRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public boolean regUser(RegUserDto regUserDto) {
        User user = userRepository.findByEmail(regUserDto.getEmail()).orElse(null);
        if (user != null) {
            throw new UserAlreadyExistsException(regUserDto.getEmail());
        }

        user = modelMapper.map(regUserDto, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        Long userId = userRepository.save(user).getUserId();

        LocalDateTime timeStart = LocalDateTime.now();
        LocalDateTime timeEnd = LocalDateTime.now().plusYears(userRolePeriod);
        Role role = roleRepository.findByRoleName("User".toUpperCase()).orElseThrow(RoleNotFoundException::new);
        UserRole userRole = new UserRole(userId, role.getRoleId(), timeStart, timeEnd);
        userRoleRepository.save(userRole);
        return true;
    }

    @Override
    public GetUserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserRole userRole = userRoleRepository.findByUserIdAndStartBeforeAndEndAfter(id, LocalDateTime.now(), LocalDateTime.now()).orElseThrow(UserNotFoundException::new);
        System.out.println("User Role ID = " + userRole.getRoleId());
        Role role = roleRepository.getById(userRole.getRoleId());
        GetUserDto userDto = modelMapper.map(user, GetUserDto.class);
        userDto.setRoleName(role.getRoleName());
        userDto.setExpiryDate(userRole.getEnd().toLocalDate());
        return userDto;
    }

    @Override
    @Transactional
    public GetUserDto deleteUser(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        GetUserDto removedUser = modelMapper.map(user, GetUserDto.class);
        System.out.println("User ID = " + user.getUserId());
        UserRole userRole = userRoleRepository.findByUserIdAndStartBeforeAndEndAfter(user.getUserId(), LocalDateTime.now(), LocalDateTime.now()).orElseThrow(UserNotFoundException::new);
        System.out.println("User Role ID = " + userRole.getRoleId());
        removedUser.setRoleName(userRole.getRoleId().toString());
//        userRepository.delete(user);
        return removedUser;
    }


}