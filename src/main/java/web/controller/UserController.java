package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;


@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPage");
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }

}
