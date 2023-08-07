package springSecurity.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springSecurity.models.User;
import springSecurity.service.UserService;
import springSecurity.service.UserServiceImp;
import springSecurity.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UsersController {

    private UserServiceImp userService;
    private final UserValidator userValidator;


    @Autowired
    public UsersController(UserServiceImp userService, UserValidator userValidator) {

        this.userService = userService;
        this.userValidator = userValidator;
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

//    @GetMapping("/newUser")
//    public String showAddUserForm(@ModelAttribute("user") User user) {
//        return "users/newUser";
//    }
//
//    @PostMapping("/")
//    public String createUser(@ModelAttribute("user") @Valid User user,
//                             BindingResult bindingResult) {
//
//        userValidator.validate(user, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "/users/newUser";
//        }
//
//
//        userService.saveUser(user);
//
//        return "redirect:/auth/login";
//    }

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
