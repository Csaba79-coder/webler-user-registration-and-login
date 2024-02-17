package hu.webler.webleruserregistrationandlogin.service;

import hu.webler.webleruserregistrationandlogin.model.UserCreateModel;
import hu.webler.webleruserregistrationandlogin.model.UserModel;
import hu.webler.webleruserregistrationandlogin.persistence.UserRepository;
import hu.webler.webleruserregistrationandlogin.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<UserModel> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(Mapper::mapUserEntityToUserModel)
                .collect(Collectors.toList());
    }

    public UserModel createUser(UserCreateModel userCreateModel) {
        if (userCreateModel.getEmail().isBlank() || userCreateModel.getEmail().isEmpty() ||
        userCreateModel.getPassword().isBlank() || userCreateModel.getPassword().isEmpty()) {
            // TODO error handling!
            return null;
        } else if (userCreateModel.getUsername().isBlank() || userCreateModel.getUsername().isEmpty()) {
            String message = "Invalid input!";
            log.info(message);
            throw new IllegalArgumentException(message);
        } else {
            // TODO legyen az email unique?
            // TODO megvizsgálni, hogy a két jelszó megegyezik-e?
            if (!userCreateModel.getPassword().equals(userCreateModel.getRepeatPassword())) {
                String message = "Invalid input!";
                log.info(message);
                throw new InputMismatchException(message);
            } else {
                return Mapper.mapUserEntityToUserModel(userRepository.save(Mapper.mapUserCreateModelToUserEntity(userCreateModel)));
            }
        }
    }
}
