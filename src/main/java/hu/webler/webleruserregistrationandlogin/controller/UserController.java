package hu.webler.webleruserregistrationandlogin.controller;

import hu.webler.webleruserregistrationandlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
}
