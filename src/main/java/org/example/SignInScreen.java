package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class SignInScreen extends JFrame {
    private JLabel SignInText;
    private JLabel emailText;
    private JLabel passwordText;
    private JTextField email;
    private JPasswordField password;
    private JButton loginButton;

    public SignInScreen() {
        super("IMDB Sign In Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        SignInText = new JLabel("Sign In");
        emailText = new JLabel("Email");
        passwordText = new JLabel("Password");
        email = new JTextField(20);
        password = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.YELLOW);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEmail = email.getText();
                String userPassword = new String(password.getPassword());

                if (!IMDB.getInstance().searchCredentials(userEmail, userPassword)) {
                    JOptionPane.showMessageDialog(SignInScreen.this, "Wrong email or password!");
                } else {
                    User logedInUser = IMDB.getInstance().getUser(userEmail);
                    dispose();
                    new OptionsMenu(logedInUser);
                }
            }
        });

        // panel for email
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.add(emailText);
        emailPanel.add(email);

        // panel for password
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.add(passwordText);
        passwordPanel.add(password);

        // panel that contains credentials panels
        JPanel credentialsPanel = new JPanel();
        credentialsPanel.setLayout(new GridLayout(4, 1));
        credentialsPanel.add(SignInText);
        credentialsPanel.add(emailPanel);
        credentialsPanel.add(passwordPanel);
        credentialsPanel.add(loginButton);

        SignInBackground mainPanel = new SignInBackground("src/main/resources/images/imdb-lead-image.jpg");
        mainPanel.add(credentialsPanel);

        getContentPane().add(mainPanel);
        setResizable(false);
        setVisible(true);
    }
}
