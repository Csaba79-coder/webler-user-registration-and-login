package hu.webler.webleruserregistrationandlogin.service;

import hu.webler.webleruserregistrationandlogin.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
