package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
        Optional<User> user = userService.getById(id);
        List<Role> roles = userService.allRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUser");
        modelAndView.addObject("getRole", roles);
        modelAndView.addObject("user", user.get());
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user") User user, @RequestParam("role") String[] role) {
        // получаем роль, и присваиваем
        if (role[0].equals("ROLE_NONE")) {
            // если роль не выбрана, грузим из базы старую роль
            Optional<User> userOld = userService.getById(user.getId());
            user.setRoles(userOld.get().getRoles());
        } else {
            Set<Role> rolesArray = new HashSet<>();
            for (String roles : role) {
                Optional<Role> currentRole = userService.getRoleByRoleName(roles);
                rolesArray.add(currentRole.get());
            }
            user.setRoles(rolesArray);
        }

        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            Optional<User> newUser = userService.getUserByName(user.getName());
            user.setPassword(newUser.get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            Optional<Role> currentRole = userService.getRoleByRoleName(roles);
            rolesArray.add(currentRole.get());
        }
        user.setRoles(rolesArray);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/list");
        userService.add(user);
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.getById(id);
        userService.delete(user.get());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/list");
        return modelAndView;
    }
}