package org.selenium.model.user;

import org.selenium.util.TestDataReader;

public class UserCreator {

    public static final String TESTDATA_USER_NAME = "testdata.user.name";
    public static final String TESTDATA_USER_PASSWORD = "testdata.user.password";

    public static User withCredentialsFromProperty() {
        return User.builder()
                .username(TestDataReader.getTestData(TESTDATA_USER_NAME))
                .password(TestDataReader.getTestData(TESTDATA_USER_PASSWORD))
                .build();
    }

}
