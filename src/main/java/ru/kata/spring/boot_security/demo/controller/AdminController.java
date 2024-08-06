package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String printUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "/admin";
    }

    @GetMapping("/new")
    public String createUserIndex(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());

        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result, @RequestParam List<Long> roles, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "new";
        }

        Set<Role> userRoles = new HashSet<>();
        for (long roleId : roles) {
            userRoles.add(roleService.getRole(roleId));
        }
        user.setRoles(userRoles);
        userService.addUser(user);

        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}/update")
    public String updateIndexUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());

        return "/update";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result, @RequestParam List<Long> roles, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "/update";
        }

        Set<Role> userRoles = new HashSet<>();
        for (long roleId : roles) {
            userRoles.add(roleService.getRole(roleId));
        }
        user.setRoles(userRoles);
        userService.updateUser(user);

        return "redirect:/admin";
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return "redirect:/admin";
    }
}
