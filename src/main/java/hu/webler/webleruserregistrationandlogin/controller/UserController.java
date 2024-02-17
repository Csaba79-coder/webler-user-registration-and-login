package hu.webler.webleruserregistrationandlogin.controller;

import hu.webler.webleruserregistrationandlogin.model.UserRegistrationModel;
import hu.webler.webleruserregistrationandlogin.model.UserModel;
import hu.webler.webleruserregistrationandlogin.model.UserUpdateModel;
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
    public ResponseEntity<UserModel> createNewUser(@RequestBody UserRegistrationModel userRegistrationModel) {
        return ResponseEntity.status(201).body(userService.createUser(userRegistrationModel));
    }

    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.status(200).body(userService.loginUser(email, password));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> renderUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(200).body(userService.findUserById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserModel> updateExistingUser(@PathVariable Long id, @RequestBody UserUpdateModel userUpdateModel) {
        return ResponseEntity.status(200).body(userService.partialUpdateExistingUser(id, userUpdateModel));
    }
}
