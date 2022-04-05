package drugsintel.accounting.service;

import drugsintel.accounting.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drugsintel.accounting.dto.GetUserDto;
import drugsintel.accounting.dto.exceptions.UserNotFoundException;
import drugsintel.accounting.models.User;

@Service
public class UserServiceImpl implements UserService {

	RoleRepository roleRepository;
	RoleRouteRepository roleRouteRepository;
	RouteRepository routeRepository;
	UserRepository userRepository;
	UserRoleRepository userRoleRepository;
	ModelMapper modelMapper;

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
	public GetUserDto getUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return modelMapper.map(user, GetUserDto.class);
	}

}