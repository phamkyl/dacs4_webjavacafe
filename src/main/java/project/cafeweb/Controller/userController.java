package project.cafeweb.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import project.cafeweb.model.User;
import project.cafeweb.model.dto.UserRegistrationDto;
import project.cafeweb.service.UserService;
import project.cafeweb.service.UserServiceList;
@Controller
public class userController {
	private UserServiceList userServiceList;

    

    public userController(UserServiceList userServiceList) {
        this.userServiceList = userServiceList;
    }

    @GetMapping("/user-list")
    public String listUsers(Model model) {
		 List<User> users = userServiceList.listAll(); 
        model.addAttribute("listUsers", users);
        return "list_user";
    }
}

