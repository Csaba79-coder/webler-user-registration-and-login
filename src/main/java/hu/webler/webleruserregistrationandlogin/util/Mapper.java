package hu.webler.webleruserregistrationandlogin.util;

import hu.webler.webleruserregistrationandlogin.entity.User;
import hu.webler.webleruserregistrationandlogin.model.UserModel;

public class Mapper {

    public static UserModel mapUserEntityToUserModel(User entity) {
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        return model;
    }

    private Mapper() {

    }
}
