package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String userList(Model model, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("loginUser", personDetails.getUser());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("userNew", new User());
        return "users";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("user") User user, @RequestParam(name = "roles", required = false) List<Long> roleId) {
        if (roleId != null && !roleId.isEmpty()) {
            Set<Role> roles = roleId.stream()
                    .map(roleService::findRoleById)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        userService.add(user);
        return "redirect:/admin";

    }

    @PostMapping(value = "/update")
    public String update(@ModelAttribute("userEdited") User user, @RequestParam(name = "roles", required = false) List<Long> roleId) {
        Set<Role> roleSet = new HashSet<>();
        if (roleId != null) {
            for (Long role : roleId) {
                roleSet.add(roleService.findRoleById(role));
            }
            user.setRoles(roleSet);
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam("userId") Long userId) {
        userService.deleteById(userId);
        return "redirect:/admin";
    }
}
