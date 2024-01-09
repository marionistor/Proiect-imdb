package org.example;

import java.time.LocalDateTime;

public class UserFactory {
    public UserFactory() {}
    public static User<?> factory(AccountType userType, String username, String name, String country, int age,
                               char gender, LocalDateTime birthday, Credentials userCredentials) {
        if (userType == AccountType.REGULAR) {
            return new Regular(username, name, country, age, gender, birthday, userCredentials);
        }
        if (userType == AccountType.CONTRIBUTOR) {
            return new Contributor(username, name, country, age, gender, birthday, userCredentials);
        }
        if (userType == AccountType.ADMIN) {
            return new Admin(username, name, country, age, gender, birthday, userCredentials);
        }

        return null;
    }
}
