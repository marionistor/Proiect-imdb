package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateUser extends JFrame {
    private JLabel selectLabel;
    private JComboBox<String> users;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel countryLabel;
    private JTextField country;
    private JLabel ageLabel;
    private JTextField age;
    private JLabel genderLabel;
    private JComboBox<String> gender;
    private JLabel birthdateLabel;
    private JTextField birthdate;
    private JLabel usernameLabel;
    private JTextField username;
    private JButton genUsername;
    private JLabel typeLabel;
    private JComboBox<String> type;
    private JLabel emailLabel;
    private JTextField email;
    private JLabel passwordLabel;
    private JTextField password;
    private JButton genPassword;
    private JButton updateUser;

    public JPanel createPanel(JLabel label, JTextField text) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(text);
        return panel;
    }

    public JPanel createComboBoxPanel (JLabel label, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(comboBox);
        return panel;
    }

    public UpdateUser(Admin loggedInUser) {
        super("Add User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 1000);

        selectLabel = new JLabel("Select user: ");
        selectLabel.setForeground(Color.WHITE);

        String[] usersStr = new String[IMDB.getInstance().getUsersList().size() - 1];
        int index = 0;
        for (User user : IMDB.getInstance().getUsersList()) {
            if (!user.getUsername().equals(loggedInUser.getUsername())) {
                usersStr[index++] = user.getUsername();
            }
        }
        users = new JComboBox<>(usersStr);

        JPanel selectPanel = createComboBoxPanel(selectLabel, users);

        JPanel updatePanel = new JPanel();
        updatePanel.setBackground(Color.darkGray);
        updatePanel.setLayout(new GridLayout(30, 1));
        updatePanel.add(selectPanel);
        updatePanel.add(new JLabel());

        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePanel.removeAll();
                updatePanel.add(selectPanel);
                updatePanel.add(new JLabel());

                String userStr = (String) users.getSelectedItem(); 
                User user = IMDB.getInstance().getUserByName(userStr);
                
                nameLabel = new JLabel("Name: ");
                nameLabel.setForeground(Color.WHITE);
                name = new JTextField(50);
                name.setText(user.getUserInfo().getName());
                JPanel namePanel = createPanel(nameLabel, name);
                updatePanel.add(namePanel);
                updatePanel.add(new JLabel());

                countryLabel = new JLabel("Country: ");
                countryLabel.setForeground(Color.WHITE);
                country = new JTextField(50);
                country.setText(user.getUserInfo().getCountry());
                JPanel countryPanel = createPanel(countryLabel, country);
                updatePanel.add(countryPanel);
                updatePanel.add(new JLabel());

                ageLabel = new JLabel("Age(between 0 and 100): ");
                ageLabel.setForeground(Color.WHITE);
                age = new JTextField(50);
                age.setText(user.getUserInfo().getAge() + "");
                JPanel agePanel = createPanel(ageLabel, age);
                updatePanel.add(agePanel);
                updatePanel.add(new JLabel());

                genderLabel = new JLabel("Gender: ");
                genderLabel.setForeground(Color.WHITE);
                String[] genders = {"M", "F", "N"};
                gender = new JComboBox<>(genders);
                gender.setSelectedItem(user.getUserInfo().getGender());
                JPanel genderPanel = createComboBoxPanel(genderLabel, gender);
                updatePanel.add(genderPanel);
                updatePanel.add(new JLabel());

                birthdateLabel = new JLabel("Birthdate(format: yyyy-mm-dd): ");
                birthdateLabel.setForeground(Color.WHITE);
                birthdate = new JTextField(50);
                birthdate.setText(user.getUserInfo().getBirthday().toLocalDate().toString());
                JPanel birthdatePanel = createPanel(birthdateLabel, birthdate);
                updatePanel.add(birthdatePanel);
                updatePanel.add(new JLabel());

                emailLabel = new JLabel("Email: ");
                emailLabel.setForeground(Color.WHITE);
                email = new JTextField(50);
                email.setText(user.getUserInfo().getUserCredentials().getEmail());
                JPanel emailPanel = createPanel(emailLabel, email);
                updatePanel.add(emailPanel);
                updatePanel.add(new JLabel());

                passwordLabel = new JLabel("Password: ");
                passwordLabel.setForeground(Color.WHITE);
                password = new JTextField(50);
                password.setText(user.getUserInfo().getUserCredentials().getPassword());
                password.setEditable(false);
                JPanel passwordPanel = createPanel(passwordLabel, password);
                updatePanel.add(passwordPanel);
                updatePanel.add(new JLabel());
                
                genPassword = new JButton("Generate Password");
                genPassword.setBackground(Color.YELLOW);
                genPassword.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nameStr = name.getText();

                        if (nameStr.isEmpty()) {
                            JOptionPane.showMessageDialog(UpdateUser.this, "Enter name, birthdate and country first!");
                        } else if (!loggedInUser.isValidName(nameStr)) {
                            JOptionPane.showMessageDialog(UpdateUser.this, "Invalid name!");
                        } else {
                            String passwordGenerated = loggedInUser.generatePassword(nameStr);
                            while (loggedInUser.isPasswordTaken(passwordGenerated, userStr)) {
                                passwordGenerated = loggedInUser.generatePassword(nameStr);
                            }
                            password.setText(passwordGenerated);
                        }
                    }
                });
                updatePanel.add(genPassword);
                updatePanel.add(new JLabel());

                usernameLabel = new JLabel("Username: ");
                usernameLabel.setForeground(Color.WHITE);
                username = new JTextField(50);
                username.setText(user.getUsername());
                username.setEditable(false);
                JPanel usernamePanel = createPanel(usernameLabel, username);
                updatePanel.add(usernamePanel);
                updatePanel.add(new JLabel());

                genUsername = new JButton("Generate Username");
                genUsername.setBackground(Color.YELLOW);
                genUsername.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nameStr = name.getText();

                        if (nameStr.isEmpty()) {
                            JOptionPane.showMessageDialog(UpdateUser.this, "Enter name first!");
                        } else if (!loggedInUser.isValidName(nameStr)) {
                            JOptionPane.showMessageDialog(UpdateUser.this, "Invalid name!");
                        } else {
                            String usernameGenerated = loggedInUser.generateUsername(nameStr);
                            while (loggedInUser.isUsernameTaken(usernameGenerated, userStr)) {
                                usernameGenerated = loggedInUser.generateUsername(nameStr);
                            }
                            username.setText(usernameGenerated);
                        }
                    }
                });
                updatePanel.add(genUsername);
                updatePanel.add(new JLabel());

                updateUser = new JButton("Update User Info");
                updateUser.setBackground(Color.YELLOW);
                updateUser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nameStr = name.getText();
                            String countryStr = country.getText();
                            String birthdateStr = birthdate.getText();
                            int ageInt = Integer.parseInt(age.getText());
                            String emailStr = email.getText();
                            String passwordStr = password.getText();
                            String usernameStr = username.getText();
                            String genderStr = (String) gender.getSelectedItem();
                            char genderChar = genderStr.charAt(0);

                            if (nameStr.isEmpty() || countryStr.isEmpty() || birthdateStr.isEmpty()
                                    || emailStr.isEmpty() || passwordStr.isEmpty() || usernameStr.isEmpty()) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Complete all fields!");
                            } else if (!loggedInUser.isValidName(nameStr)) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Entered name is not valid!");
                            } else if (!loggedInUser.isValidCountry(countryStr)) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Entered country is not valid!");
                            } else if (!loggedInUser.isValidBirthdate(birthdateStr)) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Entered birthdate is not valid!");
                            } else if (ageInt < 0 || ageInt > 100) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Age out bounds!");
                            } else if (!loggedInUser.isValidEmail(emailStr)) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Invalid email address!");
                            } else if (loggedInUser.isEmailTaken(emailStr, userStr)) {
                                JOptionPane.showMessageDialog(UpdateUser.this, "Email is taken!");
                            } else {
                                DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate date = LocalDate.parse(birthdateStr, DTFormatter);
                                Credentials newCredentials = new Credentials(emailStr, passwordStr);
                                loggedInUser.updateUser(userStr, usernameStr, nameStr, countryStr, ageInt, genderChar, date.atStartOfDay(), newCredentials);
                                dispose();
                            }
                        } catch (NumberFormatException numberFormatException) {
                            JOptionPane.showMessageDialog(UpdateUser.this, "Age must be integer!");
                        }
                    }
                });
                updatePanel.add(updateUser);
                updatePanel.add(new JLabel());
                
                revalidate();
                repaint();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(updatePanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
