package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    //GetMapping("/list")
    public ModelAndView allUsers() {
        List<User> users = userService.allUsers();
        List<Role> roleList = userService.allRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminPage");
        modelAndView.addObject("userList", users);
        modelAndView.addObject("roleList", roleList);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        List<Role> roles = userService.allRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUser");
        modelAndView.addObject("getRole", roles);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user") User user, @RequestParam("role") String[] role) {
        // получаем роль, и присваиваем
        if (role[0].equals("ROLE_NONE")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User userOld = userService.getUserByName(auth.getName());
            user.setRoles(userOld.getRoles());
        } else {
            Set<Role> rolesArray = new HashSet<>();
            for (String roles : role) {
                rolesArray.add(userService.getRoleByRoleName(roles));
            }
            user.setRoles(rolesArray);
        }

        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            User newUser = userService.getUserByName(user.getName());
            user.setPassword(newUser.getPassword());
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }

        userService.edit(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/list");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        List<Role> roles = userService.allRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("getRole", roles);
        modelAndView.setViewName("addUser");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user, @RequestParam("role") String[] role) {
        Set<Role> rolesArray = new HashSet<>();
        for (String roles : role) {
            rolesArray.add(userService.getRoleByRoleName(roles));
        }
        user.setRoles(rolesArray);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/list");
        userService.add(user);
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        userService.delete(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/list");
        return modelAndView;
    }
}