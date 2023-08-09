package springSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import springSecurity.models.User;
import springSecurity.service.UserService;

import java.security.Principal;

public class UserController {
    private final UserService userServiceImp;

    @Autowired
    public UserController(UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/user")
    public String showUser(Principal principal, ModelMap model) {
        User user = userServiceImp.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
