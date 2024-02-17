package hu.webler.webleruserregistrationandlogin.service;

import hu.webler.webleruserregistrationandlogin.controller.exception.UserAlreadyExistsException;
import hu.webler.webleruserregistrationandlogin.entity.User;
import hu.webler.webleruserregistrationandlogin.model.UserLoginModel;
import hu.webler.webleruserregistrationandlogin.model.UserRegistrationModel;
import hu.webler.webleruserregistrationandlogin.model.UserModel;
import hu.webler.webleruserregistrationandlogin.persistence.UserRepository;
import hu.webler.webleruserregistrationandlogin.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public UserModel createUser(UserRegistrationModel userRegistrationModel) {
        UserModel foundUser = findUserByEmail(userRegistrationModel.getEmail());
        if (foundUser != null) {
            String message = String.format("User already exists with email: %s", userRegistrationModel.getEmail());
            log.info(message);
            throw new UserAlreadyExistsException(message);
        } else {
            if (userRegistrationModel.getEmail().isBlank() || userRegistrationModel.getEmail().isEmpty() ||
                    userRegistrationModel.getPassword().isBlank() || userRegistrationModel.getPassword().isEmpty()) {
                String message = "Invalid input!";
                log.info(message);
                throw new InputMismatchException(message);
            } else if (userRegistrationModel.getUsername().isBlank() || userRegistrationModel.getUsername().isEmpty()) {
                String message = "Invalid input!";
                log.info(message);
                throw new IllegalArgumentException(message);
            } else {
                if (!userRegistrationModel.getPassword().equals(userRegistrationModel.getRepeatPassword())) {
                    String message = "Invalid input!";
                    log.info(message);
                    throw new InputMismatchException(message);
                } else {
                    return Mapper.mapUserEntityToUserModel(userRepository.save(Mapper.mapUserCreateModelToUserEntity(userRegistrationModel)));
                }
            }
        }
    }

    public UserModel loginUser(String email, String password) {
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isEmpty()) {
            String message = String.format("User not exists with email: %s", email);
            log.info(message);
            throw new NoSuchElementException(message);
        } else {
            if (existingUser.get().getPassword().equals(password)) {
                return Mapper.mapUserEntityToUserModel(existingUser.get());
            }
            return null;
        }
    }

    public UserModel findUserById(Long id) {
        return Mapper.mapUserEntityToUserModel(userRepository.findUserById(id).orElseThrow(() -> {
            String message = String.format("User not exists with id: %s", id);
            log.info(message);
            return new NoSuchElementException(message);
        }));
    }

    private UserModel findUserByEmail(String email) {
        // van vagy nincs?
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        // ha nincs, akkor kezeljük a "hibát" -> vagy null-t adunk vissza! (ez utóbbi jobb, mert szükség esetén kezeljük a null-t később, a helyén
        // ha van akkor megyünk tovább a logikával
        return optionalUser.map(Mapper::mapUserEntityToUserModel).orElse(null);
    }
}
