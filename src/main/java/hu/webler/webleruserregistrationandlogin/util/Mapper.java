package hu.webler.webleruserregistrationandlogin.util;

import hu.webler.webleruserregistrationandlogin.entity.User;
import hu.webler.webleruserregistrationandlogin.model.UserLoginModel;
import hu.webler.webleruserregistrationandlogin.model.UserRegistrationModel;
import hu.webler.webleruserregistrationandlogin.model.UserModel;

public class Mapper {

    public static UserModel mapUserEntityToUserModel(User entity) {
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        return model;
    }

    public static User mapUserCreateModelToUserEntity(UserRegistrationModel model) {
        User entity = new User();
        entity.setEmail(model.getEmail());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        return entity;
    }

    public static User mapUserLoginModelToUserEntity(String email, String password) {
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(password);
        return entity;
    }

    private Mapper() {

    }
}
