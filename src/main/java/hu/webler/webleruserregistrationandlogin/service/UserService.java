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
import java.util.Objects;
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
        UserModel foundUser = findUserByEmail(userCreateModel.getEmail());
        if (foundUser != null) {
            String message = String.format("User already exists with email: %s", userCreateModel.getEmail());
            log.info(message);
            throw new UserAlreadyExistsException(message);
        } else {
            if (userCreateModel.getEmail().isBlank() || userCreateModel.getEmail().isEmpty() ||
                    userCreateModel.getPassword().isBlank() || userCreateModel.getPassword().isEmpty()) {
                String message = "Invalid input!";
                log.info(message);
                throw new InputMismatchException(message);
            } else if (userCreateModel.getUsername().isBlank() || userCreateModel.getUsername().isEmpty()) {
                String message = "Invalid input!";
                log.info(message);
                throw new IllegalArgumentException(message);
            } else {
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

    public UserModel findUserByEmail(String email) {
        // van vagy nincs?
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        // ha nincs, akkor kezeljük a "hibát" -> vagy null-t adunk vissza! (ez utóbbi jobb, mert szükség esetén kezeljük a null-t később, a helyén
        // ha van akkor megyünk tovább a logikával
        return optionalUser.map(Mapper::mapUserEntityToUserModel).orElse(null);
    }
}
