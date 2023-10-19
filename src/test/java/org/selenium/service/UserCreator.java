package org.selenium.service;

import org.selenium.model.User;

public class UserCreator {

    public static final String USER_NAME = "swebdriver";
    public static final String USER_PASSWORD = "Support-1234";

    public static User withCredentialsFromProperty(){
        return new User(USER_NAME,
                USER_PASSWORD);
    }

}
