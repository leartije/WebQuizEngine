package engine.controller;

import engine.entity.User;
import engine.services.registeruser.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    private RegisterUserService registerUserService;

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        registerUserService.save(user);
    }

}
