package hu.webler.webleruserregistrationandlogin.controller;

import hu.webler.webleruserregistrationandlogin.model.UserCreateModel;
import hu.webler.webleruserregistrationandlogin.model.UserModel;
import hu.webler.webleruserregistrationandlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> renderAllUsers() {
        return ResponseEntity.status(200).body(userService.findAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> createNewUser(@RequestBody UserCreateModel userCreateModel) {
        return ResponseEntity.status(201).body(userService.createUser(userCreateModel));
    }
}
