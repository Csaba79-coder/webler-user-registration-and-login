package hu.webler.webleruserregistrationandlogin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginModel {

    // feltételezve, hogy email alapján jelentkezik be, és nem user név alapján!
    private String email;
    private String password;
}
