package hu.webler.webleruserregistrationandlogin.service;

import hu.webler.webleruserregistrationandlogin.controller.exception.UserAlreadyExistsException;
import hu.webler.webleruserregistrationandlogin.entity.User;
import hu.webler.webleruserregistrationandlogin.model.UserCreateModel;
import hu.webler.webleruserregistrationandlogin.model.UserModel;
import hu.webler.webleruserregistrationandlogin.persistence.UserRepository;
import hu.webler.webleruserregistrationandlogin.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
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
        User currentUser = findUserByEmail(userCreateModel.getEmail());
        if (currentUser != null) {
            String message = String.format("User already exists with email: %s", userCreateModel.getEmail());
            log.info(message);
            throw new UserAlreadyExistsException(message);
        } else {
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

    public User findUserByEmail(String email) {
        // van vagy nincs?
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        // ha nincs, akkor kezeljük a "hibát"
        // ha van akkor megyünk tovább a logikával
        return optionalUser.orElse(null);
    }
}
