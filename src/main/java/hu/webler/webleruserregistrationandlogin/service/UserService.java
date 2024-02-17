package hu.webler.webleruserregistrationandlogin.service;

import hu.webler.webleruserregistrationandlogin.model.UserModel;
import hu.webler.webleruserregistrationandlogin.persistence.UserRepository;
import hu.webler.webleruserregistrationandlogin.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserModel> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(Mapper::mapUserEntityToUserModel)
                .collect(Collectors.toList());
    }
}
