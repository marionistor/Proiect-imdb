package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class User<T extends Comparable<T>> implements Observer {
    private Information userInfo;
    private AccountType userType;
    private String username;
    private int userExperience;
    private List<String> userNotifications;
    private SortedSet<T> favoritesSet;

    public User(String username, String name, String country, int age, char gender, LocalDateTime birthday, Credentials userCredentials) {

        this.username = username;
        this.userInfo = new Information.InformationBuilder(name, country, age, gender, birthday)
                                        .userCredentials(userCredentials)
                                        .build();
        userNotifications = new ArrayList<>();
        favoritesSet = new TreeSet<>();
    }
    public Information getUserInfo() {
        return userInfo;
    }

    public AccountType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public SortedSet<T> getFavoritesSet() {
        return favoritesSet;
    }

    public int getUserExperience() {
        return userExperience;
    }

    public List<String> getUserNotifications() {
        return userNotifications;
    }

    public void setUserExperience(int userExperience) {
        this.userExperience = userExperience;
    }

    public void setAccountType(AccountType userType) {
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class Information {
        private Credentials userCredentials;
        private String name;
        private String country;
        private int age;
        private char gender;
        private LocalDateTime birthday;

        private Information(InformationBuilder builder) {
            this.userCredentials = builder.userCredentials;
            this.name = builder.name;
            this.country = builder.country;
            this.age = builder.age;
            this.gender = builder.gender;
            this.birthday = builder.birthday;
        }

        public Credentials getUserCredentials() {
            return userCredentials;
        }
        public String geName() {
            return name;
        }
        public String getCountry() {
            return country;
        }
        public int getAge() {
            return age;
        }
        public char getGender() {
            return gender;
        }
        public LocalDateTime getBirthday() {
            return birthday;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setBirthday(LocalDateTime birthday) {
            this.birthday = birthday;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public static class InformationBuilder {
            private Credentials userCredentials;
            private String name;
            private String country;
            private int age;
            private char gender;
            private LocalDateTime birthday;

            public InformationBuilder(String name, String country, int age, char gender, LocalDateTime birthday) {
                this.name = name;
                this.country = country;
                this.age = age;
                this.gender = gender;
                this.birthday = birthday;
            }

            public InformationBuilder userCredentials(Credentials userCredentials) {
                this.userCredentials = userCredentials;
                return this;
            }

            public String toString() {
                return "name: "  + name + "\ncountry: " + country + "\nage: " + age + "\ngender: " + gender + "\nbirthday: " + birthday;
            }
            public Information build() {
                return new Information(this);
            }
        }
    }

    // update observer
    public void update(String notification) {
        userNotifications.add(notification);
    }
    public void removeNotifications(String notification) {
        userNotifications.remove(notification);
    }

    public void notifyUser(User<?> user, Event event, String request) {
        String notification;
        switch (event) {
            case SOLVED_REQUEST:
                notification = "Cererea trimisa a fost rezolvata de catre utilizatorul \"" + user.getUsername() + "\"";
                update(notification);
                break;
            case REJECTED_REQUEST:
                notification = "Cererea trimisa a fost refuzata de catre utilizatorul \"" + user.getUsername() + "\"";
                update(notification);
                break;
            case RECEIVED_REQUEST:
                notification = "Cerere noua de la \"" + user.getUsername() + "\": " + request;
                update(notification);
                break;
            case ADMIN_RECEIVED_REQUESTS:
                notification = "Cerere noua pentru echipa de admini de la \"" + getUsername() + "\": " + request;
                user.update(notification);
                break;
            default:
                break;
        }
    }
    // add elements in favorites list
    public void addFavorite(Object favorite) {
        favoritesSet.add((T) favorite);
    }

    public abstract String toString();

    // remove elements from favorites list
    public void removeFavorite(Object favorite) {
        favoritesSet.remove((T) favorite);
    }

    // update user xp
    public void updateExperience(ExperienceStrategy gainedExperience) {
        userExperience += gainedExperience.calculateExperience();
    }
}
