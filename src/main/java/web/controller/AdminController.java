package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userServiceImpl;

    @ResponseBody
    @GetMapping(value = "/findUser/{id}")
    public User findUser(@PathVariable long id) {
        User user = userServiceImpl.getUserById(id);
        return user;
    }

    @ResponseBody
    @GetMapping("/rest")
    public List<User> allUsersPage() {
        List<User> users = userServiceImpl.listUsers();
        return users;
    }

    @ResponseBody
    @PostMapping(value = "/edit")
    public void editUser(@RequestBody() User user) {
        userServiceImpl.setRole(user,user.getRolesSet().iterator().next().getId());
        userServiceImpl.updateUser(user);
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public void createUser(@RequestBody User user) {
        userServiceImpl.setRole(user,user.getRolesSet().iterator().next().getId());
        userServiceImpl.add(user);
    }

    @ResponseBody
    @GetMapping(value = "/delete/")
    public void deleteUser(@RequestParam("id") long id) {
        userServiceImpl.deleteUser(id);
    }

    @ResponseBody
    @GetMapping("/error")
    public String errorUser(ModelMap model) {
        return "error";
    }
}
