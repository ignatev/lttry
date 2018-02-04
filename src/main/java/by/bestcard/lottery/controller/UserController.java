package by.bestcard.lottery.controller;

import by.bestcard.lottery.model.User;
import by.bestcard.lottery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    private final UserRepository users;

    @Autowired
    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping("/users/new")
    public String initCreationForm(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return user.toString();
    }

    @PostMapping("/users/new")
    public String processCreationForm(User user, BindingResult bindingResult) {
        this.users.save(user);
        return "/users/" + user.getId();
    }

    @GetMapping("/")
    public String hello() {
        return "Hello world";
    }
}
