package project.cafeweb.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.cafeweb.model.Role;
import project.cafeweb.model.User;
import project.cafeweb.model.dto.UserRegistrationDto;
import project.cafeweb.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	 @Override
	    public List<UserRegistrationDto> listAll() {
	        List<User> users = userRepository.findAll();
	        return users.stream()
	                .map(user -> convertToDto(user))
	                .collect(Collectors.toList());
	    }

	    // Phương thức chuyển đổi User thành DTO
	    private UserRegistrationDto convertToDto(User user) {
	    	
	        return new UserRegistrationDto(user.getFirstName(), user.getLastName(),
	        		user.getEmail(), user.getPassword());
	    }

	@Override
	public User save(UserRegistrationDto registrationDto) {
	    Role role = new Role(registrationDto.getRole());
	    User user = new User(registrationDto.getFirstName(), 
	                         registrationDto.getLastName(), 
	                         registrationDto.getEmail(),
	                         passwordEncoder.encode(registrationDto.getPassword()),
	                         Collections.singletonList(role));

	    return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), 
		                                                               user.getPassword(), 
		                                                               mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
		            .map(role -> new SimpleGrantedAuthority(role.getName()))
		            .collect(Collectors.toList());
	}

	@Override
	public User save(UserRegistrationDto registrationDto, String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	



	

	

}