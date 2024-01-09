package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddUser extends JFrame {
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
    private JButton addUser;

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

    public AddUser(Admin loggedInUser) {
        super("Add User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 1000);

        JPanel addPanel = new JPanel();
        addPanel.setBackground(Color.darkGray);
        addPanel.setLayout(new GridLayout(30, 1));

        nameLabel = new JLabel("Enter name(format: FirstName LastName): ");
        nameLabel.setForeground(Color.WHITE);
        name = new JTextField(50);
        JPanel namePanel = createPanel(nameLabel, name);
        addPanel.add(namePanel);
        addPanel.add(new JLabel());

        countryLabel = new JLabel("Enter country(ex: Romania or RO): ");
        countryLabel.setForeground(Color.WHITE);
        country = new JTextField(50);
        JPanel countryPanel = createPanel(countryLabel, country);
        addPanel.add(countryPanel);
        addPanel.add(new JLabel());

        ageLabel = new JLabel("Enter age(between 0 and 100): ");
        ageLabel.setForeground(Color.WHITE);
        age = new JTextField(50);
        JPanel agePanel = createPanel(ageLabel, age);
        addPanel.add(agePanel);
        addPanel.add(new JLabel());

        genderLabel = new JLabel("Choose gender: ");
        genderLabel.setForeground(Color.WHITE);
        String[] genders = {"M", "F", "N"};
        gender = new JComboBox<>(genders);
        JPanel genderPanel = createComboBoxPanel(genderLabel, gender);
        addPanel.add(genderPanel);
        addPanel.add(new JLabel());

        birthdateLabel = new JLabel("Enter birthdate(format: yyyy-mm-dd): ");
        birthdateLabel.setForeground(Color.WHITE);
        birthdate = new JTextField(50);
        JPanel birthdatePanel = createPanel(birthdateLabel, birthdate);
        addPanel.add(birthdatePanel);
        addPanel.add(new JLabel());

        emailLabel = new JLabel("Enter email: ");
        emailLabel.setForeground(Color.WHITE);
        email = new JTextField(50);
        JPanel emailPanel = createPanel(emailLabel, email);
        addPanel.add(emailPanel);
        addPanel.add(new JLabel());

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Color.WHITE);
        password = new JTextField(50);
        password.setEditable(false);
        JPanel passwordPanel = createPanel(passwordLabel, password);
        addPanel.add(passwordPanel);
        addPanel.add(new JLabel());

        genPassword = new JButton("Generate Password");
        genPassword.setBackground(Color.YELLOW);
        genPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameStr = name.getText();

                if (nameStr.isEmpty()) {
                    JOptionPane.showMessageDialog(AddUser.this, "Enter name, birthdate and country first!");
                } else if (!loggedInUser.isValidName(nameStr)) {
                    JOptionPane.showMessageDialog(AddUser.this, "Invalid name!");
                } else {
                    String passwordGenerated = loggedInUser.generatePassword(nameStr);
                    while (loggedInUser.isPasswordTaken(passwordGenerated, null)) {
                        passwordGenerated = loggedInUser.generatePassword(nameStr);
                    }
                    password.setText(passwordGenerated);
                }
            }
        });
        addPanel.add(genPassword);
        addPanel.add(new JLabel());

        usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(Color.WHITE);
        username = new JTextField(50);
        username.setEditable(false);
        JPanel usernamePanel = createPanel(usernameLabel, username);
        addPanel.add(usernamePanel);
        addPanel.add(new JLabel());

        genUsername = new JButton("Generate Username");
        genUsername.setBackground(Color.YELLOW);
        genUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameStr = name.getText();

                if (nameStr.isEmpty()) {
                    JOptionPane.showMessageDialog(AddUser.this, "Enter name first!");
                } else if (!loggedInUser.isValidName(nameStr)) {
                    JOptionPane.showMessageDialog(AddUser.this, "Invalid name!");
                } else {
                    String usernameGenerated = loggedInUser.generateUsername(nameStr);
                    while (loggedInUser.isUsernameTaken(usernameGenerated, null)) {
                        usernameGenerated = loggedInUser.generateUsername(nameStr);
                    }
                    username.setText(usernameGenerated);
                }
            }
        });
        addPanel.add(genUsername);
        addPanel.add(new JLabel());

        typeLabel = new JLabel("Choose user type: ");
        typeLabel.setForeground(Color.WHITE);
        String[] types = {"REGULAR", "CONTRIBUTOR", "ADMIN"};
        type = new JComboBox<>(types);
        JPanel typePanel = createComboBoxPanel(typeLabel, type);
        addPanel.add(typePanel);
        addPanel.add(new JLabel());

        addUser = new JButton("Add User");
        addUser.setBackground(Color.YELLOW);
        addUser.addActionListener(new ActionListener() {
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
                    String typeStr = (String) type.getSelectedItem();
                    String genderStr = (String) gender.getSelectedItem();
                    char genderChar = genderStr.charAt(0);

                    if (nameStr.isEmpty() || countryStr.isEmpty() || birthdateStr.isEmpty()
                            || emailStr.isEmpty() || passwordStr.isEmpty() || usernameStr.isEmpty()) {
                        JOptionPane.showMessageDialog(AddUser.this, "Complete all fields!");
                    } else if (!loggedInUser.isValidName(nameStr)) {
                        JOptionPane.showMessageDialog(AddUser.this, "Entered name is not valid!");
                    } else if (!loggedInUser.isValidCountry(countryStr)) {
                        JOptionPane.showMessageDialog(AddUser.this, "Entered country is not valid!");
                    } else if (!loggedInUser.isValidBirthdate(birthdateStr)) {
                        JOptionPane.showMessageDialog(AddUser.this, "Entered birthdate is not valid!");
                    } else if (ageInt < 0 || ageInt > 100) {
                        JOptionPane.showMessageDialog(AddUser.this, "Age out bounds!");
                    } else if (!loggedInUser.isValidEmail(emailStr)) {
                        JOptionPane.showMessageDialog(AddUser.this, "Invalid email address!");
                    } else if (loggedInUser.isEmailTaken(emailStr, null)) {
                        JOptionPane.showMessageDialog(AddUser.this, "Email is taken!");
                    } else {
                        DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(birthdateStr, DTFormatter);
                        Credentials newCredentials = new Credentials(emailStr, passwordStr);
                        loggedInUser.addUser(AccountType.valueOf(typeStr), usernameStr, nameStr, countryStr, ageInt, genderChar, date.atStartOfDay(), newCredentials);
                        dispose();
                    }
                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(AddUser.this, "Age must be integer!");
                }
            }
        });
        addPanel.add(addUser);
        addPanel.add(new JLabel());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
