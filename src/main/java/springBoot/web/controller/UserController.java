package springBoot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springBoot.web.model.User;
import springBoot.web.service.UserService;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/table", method = RequestMethod.GET)
    public String getTable(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "table";
    }

    @RequestMapping(value = "/admin/table", method = RequestMethod.POST)
    public String addUser(Model model, @RequestParam String name, String password) {
        userService.addUser(new User(name, password));
        return getTable(model);
    }

    @RequestMapping(value = "/admin/remove", method = RequestMethod.POST)
    public String removeUser(@RequestParam Long id) {
        userService.removeUser(id);
        return "redirect:/admin/table";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public String getUpdate(Model model, @RequestParam Long id) {
        model.addAttribute("users", userService.getUserById(id));
        return "/update";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUser(Model model, @RequestParam String name, String password, Long id) {
        userService.updateUser(name, password, id);
        return getUpdate(model, id);
    }

    @RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
    public String loginPage() {
        userService.addAdminAndUserPanel();
        return "login";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "user";
    }

}