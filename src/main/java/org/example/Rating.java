package org.example;

public class Rating {
    public String username;
    public int grade;
    public String comment;

    public Rating() {}
    public int getUserExperience() {
        User user = IMDB.getInstance().getUserByName(username);
        return user.getUserExperience();
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public int getGrade() {
        return grade;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        return username + " " + grade + " " + comment;
    }
}
