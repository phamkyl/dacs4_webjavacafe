package project.cafeweb.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.cafeweb.model.User;
import project.cafeweb.model.contact;
import project.cafeweb.model.dto.UserRegistrationDto;



public interface UserService extends UserDetailsService{

	User save(UserRegistrationDto registrationDto);

	User save(UserRegistrationDto registrationDto, String roleName);



	List<UserRegistrationDto> listAll();


	

	



	


	
	
}
