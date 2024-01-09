package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admin extends Staff {
    public Admin(String username, String name, String country, int age,
                 char gender, LocalDateTime birthday, Credentials userCredentials) {
        super(username, name, country, age, gender, birthday, userCredentials);
    }

    static List<Object> commonContributionsList = new ArrayList<>();
    public static class RequestHolder {
        static List<Request> TeamRequestsList = new ArrayList<>();

        public List<Request> getTeamRequestsList() {
            return TeamRequestsList;
        }
        public void addTeamRequest(Request r) {
            TeamRequestsList.add(r);
        }
        public void removeTeamRequest(Request r) {
            TeamRequestsList.remove(r);
        }
    }

    public boolean isValidName(String name) {
        String regex = "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)?$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean isValidBirthdate(String birthdate) {
        // year between 1924 and 2024 because age range is 0 to 100
        // month between 01 and 12
        // date betweem 01 and 31
        String regex = "^(19[2-9]\\d|20[01]\\d|202[0-4])-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(birthdate);

        return matcher.matches();
    }

    public boolean isValidCountry(String country) {
        String regex = "^[A-Z][a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(country);

        return matcher.matches();
    }

    public boolean isEmailTaken(String email, String name) {
        for (User user : IMDB.getInstance().getUsersList()) {
            if (user.getUserInfo().getUserCredentials().getEmail().equals(email) && (name != null && !user.getUsername().equals(name))) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameTaken(String username, String name) {
        for (User user : IMDB.getInstance().getUsersList()) {
            if (user.getUsername().equals(username) && (name != null && !user.getUsername().equals(name))) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordTaken(String password, String name) {
        for (User user : IMDB.getInstance().getUsersList()) {
            if (user.getUserInfo().getUserCredentials().getPassword().equals(password) && (name != null && !user.getUsername().equals(name))) {
                return true;
            }
        }
        return false;
    }

    public static String generateRandomString(int stringLength) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+?,.:;%";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < stringLength; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public String generatePassword(String name) {
        Random random = new Random();

        String nameWithoutSpace = name.replaceAll(" ", "");
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < nameWithoutSpace.length(); i ++) {
            String generatedChar = generateRandomString(1);
            if (i % 2 == 0) {
                password.append(generatedChar.charAt(0));
            } else {
                password.append(nameWithoutSpace.charAt(i));
            }
        }

        return generateRandomString(2 ) + password.toString() + generateRandomString(2);
    }

    public String generateUsername(String name) {
        String[] firstLastName = name.split(" ");
        if (firstLastName.length == 2) {
            return firstLastName[0] + "_" + firstLastName[1] + "_" + generateRandomString(5);
        } else {
            return firstLastName[0] + "_" + generateRandomString(5);
        }
    }

    // add user
    public void addUser(AccountType userType, String username, String name, String country, int age,
                        char gender, LocalDateTime birthday, Credentials userCredentials) {
        User<?> newUser = UserFactory.factory(userType, username, name, country, age, gender, birthday, userCredentials);
        if (newUser != null) {
            if (newUser instanceof Regular) {
                newUser.setAccountType(AccountType.REGULAR);
            } else if (newUser instanceof Contributor) {
                newUser.setAccountType(AccountType.CONTRIBUTOR);
            } else {
                newUser.setAccountType(AccountType.ADMIN);
            }
            newUser.setUserExperience(0);
            IMDB.getInstance().addUser(newUser);
        }
    }

    // delete user
    public void removeUser(String username) {
        User<?> user = IMDB.getInstance().getUserByName(username);
        IMDB.getInstance().removeUser(user);
        for (Production production : IMDB.getInstance().getProductionsList()) {
            production.getRatings().removeIf(rating -> rating.getUsername().equals(username));
            production.updateAverageRating();
        }

        RequestHolder.TeamRequestsList.removeIf(request -> request.getCreator() != null && request.getCreator().equals(username));
        for (User<?> u : IMDB.getInstance().getUsersList()) {
            if (u instanceof Staff) {
                ((Staff) u).getIndividualRequestsList().removeIf(request -> request.getCreator() != null && request.getCreator().equals(username));
            }
        }

        if (user instanceof Staff) {
            commonContributionsList.addAll(((Staff) user).getContributions());
            if (user instanceof Contributor) {
                RequestHolder.TeamRequestsList.addAll(((Contributor) user).getIndividualRequestsList());
            }
        }
    }
    public void updateUser(String userStr, String username, String name, String country, int age,
                           char gender, LocalDateTime birthday, Credentials userCredentials) {
        User<?> user = IMDB.getInstance().getUserByName(userStr);
        if (!user.getUsername().equals(username)) {
            user.setUsername(username);
        }
        if (!user.getUserInfo().getName().equals(name)) {
            user.getUserInfo().setName(name);
        }
        if (!user.getUserInfo().getCountry().equals(country)) {
            user.getUserInfo().setCountry(country);
        }
        if (user.getUserInfo().getAge() != age) {
            user.getUserInfo().setAge(age);
        }
        if (user.getUserInfo().getGender() != gender) {
            user.getUserInfo().setGender(gender);
        }
        if (!user.getUserInfo().getBirthday().equals(birthday)) {
            user.getUserInfo().setBirthday(birthday);
        }
        if (!user.getUserInfo().getUserCredentials().getEmail().equals(userCredentials.getEmail())) {
            user.getUserInfo().getUserCredentials().setEmail(userCredentials.getEmail());
        }
        if (!user.getUserInfo().getUserCredentials().getPassword().equals(userCredentials.getPassword())) {
            user.getUserInfo().getUserCredentials().setPassword(userCredentials.getPassword());
        }
    }
}