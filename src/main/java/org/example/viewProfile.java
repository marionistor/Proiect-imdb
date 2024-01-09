package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class viewProfile extends JFrame {
    private JLabel type;
    private JLabel username;
    private JLabel experience;
    private JLabel name;
    private JLabel country;
    private JLabel age;
    private JLabel gender;
    private JLabel birthday;
    public viewProfile(User logedInUser) {
        super("User Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.setLayout(new GridLayout(8, 1));

        username = new JLabel("Username: " + logedInUser.getUsername());
        username.setForeground(Color.WHITE);
        mainPanel.add(username);

        if (logedInUser.getUserExperience() != 0) {
            experience = new JLabel("Experience: " + logedInUser.getUserExperience());
        } else {
            if (logedInUser instanceof Admin) {
                experience = new JLabel("Experience: INFINITE");
            } else {
                experience = new JLabel("Experience: 0");
            }
        }
        experience.setForeground(Color.WHITE);
        mainPanel.add(experience);

        type = new JLabel(logedInUser.getUserType() + " user");
        type.setForeground(Color.WHITE);
        mainPanel.add(type);

        name = new JLabel("Name: " + logedInUser.getUserInfo().geName());
        name.setForeground(Color.WHITE);
        mainPanel.add(name);

        country = new JLabel("Country: " + logedInUser.getUserInfo().getCountry());
        country.setForeground(Color.WHITE);
        mainPanel.add(country);

        age = new JLabel("Age: " + logedInUser.getUserInfo().getAge() + " years");
        age.setForeground(Color.WHITE);
        mainPanel.add(age);

        gender = new JLabel("Gender: " + logedInUser.getUserInfo().getGender());
        gender.setForeground(Color.WHITE);
        mainPanel.add(gender);

        birthday = new JLabel("Birthdate: " + logedInUser.getUserInfo().getBirthday().toLocalDate());
        birthday.setForeground(Color.WHITE);
        mainPanel.add(birthday);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
    }
}
