package drugsintel.accounting.service;

import drugsintel.accounting.dto.*;
import drugsintel.accounting.exceptions.RoleNotFoundException;
import drugsintel.accounting.exceptions.UserAlreadyExistsException;
import drugsintel.accounting.exceptions.UserNotActiveException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
    public LoginUser loginUser(String login) {
        //ToDO
        return null;
    }

    @Override
    public GetUserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserRole userRole = userRoleRepository.findByUserIdAndStartBeforeAndEndAfter(id, LocalDateTime.now(), LocalDateTime.now()).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.getById(userRole.getRoleId());
        GetUserDto userDto = modelMapper.map(user, GetUserDto.class);
        userDto.setRoleName(role.getRoleName());
        userDto.setExpiryDate(userRole.getEnd().toLocalDate());
        return userDto;
    }

    @Override
    @Transactional
    public UpdateUserResponseDto updateUser(Long id, UpdateUserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (!user.getActive()) {
            throw new UserNotActiveException(id);
        }
        user.setUserName(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        UserRole userRole = userRoleRepository.findByUserIdAndStartBeforeAndEndAfter(id, LocalDateTime.now(), LocalDateTime.now()).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.getById(userRole.getRoleId());
        UpdateUserResponseDto userDto = modelMapper.map(user, UpdateUserResponseDto.class);
        userDto.setRoleName(role.getRoleName());
        userDto.setUpdatedAt(LocalDate.now());
        return userDto;
    }

    @Override
    @Transactional
    public GetUserDto deleteUser(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        GetUserDto removedUser = modelMapper.map(user, GetUserDto.class);
        UserRole userRole = userRoleRepository.findByUserIdAndStartBeforeAndEndAfter(user.getUserId(), LocalDateTime.now(), LocalDateTime.now()).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findById(userRole.getRoleId()).orElseThrow(RoleNotFoundException::new);
        removedUser.setRoleName(role.getRoleName());
        user.setActive(false);
        removedUser.setExpiryDate(LocalDateTime.now().toLocalDate());
//        userRepository.delete(user);
        return removedUser;
    }

    @Override
    public UpdateUserResponseDto changeRole(String userName, String userRole) {
        return null;
    }

    @Override
    public void changePassword(String password) {

    }

    @Override
    public Set<UpdateUserResponseDto> findByCreationDate(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }

    @Override
    public Set<UpdateUserResponseDto> findByRole(String role) {
        return null;
    }


}