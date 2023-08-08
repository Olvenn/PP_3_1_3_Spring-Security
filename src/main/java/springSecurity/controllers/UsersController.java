package springSecurity.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springSecurity.models.User;
import springSecurity.service.UserServiceImp;
import springSecurity.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class UsersController {

    private UserServiceImp userService;
    private final UserValidator userValidator;
    private final UserValidator UsersDetailsService;

    @Autowired
    public UsersController(UserServiceImp userService, UserValidator userValidator, UserValidator usersDetailsService) {

        this.userService = userService;
        this.userValidator = userValidator;
        UsersDetailsService = usersDetailsService;
    }

    @GetMapping()
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.findAllUsers());
        System.out.println(userService.findAllUsers());
        return "users/users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, ModelMap model) {
        System.out.println(id);
        model.addAttribute("user", userService.findOneUser(id));
        return "users/user";
    }


    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "users/user";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateUserForm(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findOneUser(id));
        return "users/editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "users/editUser";

        userService.updateUser(id, user);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "auth/admin";
    }
}
