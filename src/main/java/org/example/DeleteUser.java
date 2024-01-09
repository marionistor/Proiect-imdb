package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteUser extends JFrame {
    private JLabel selectLabel;
    private JComboBox<String> usersComboBox;
    public DeleteUser(User loggedInUser, JFrame previous) {
        super("Delete User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);

        selectLabel = new JLabel("Select user: ");
        selectLabel.setForeground(Color.WHITE);

        String[] users = new String[IMDB.getInstance().getUsersList().size() - 1];
        int index = 0;
        for (User user : IMDB.getInstance().getUsersList()) {
            if (!user.getUsername().equals(loggedInUser.getUsername())) {
                users[index++] = user.getUsername();
            }
        }

        usersComboBox = new JComboBox<>(users);

        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(Color.darkGray);
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(selectLabel);
        selectPanel.add(usersComboBox);

        JPanel removePanel = new JPanel();
        removePanel.setBackground(Color.darkGray);
        removePanel.setLayout(new GridLayout(3, 1));
        removePanel.add(selectPanel);
        removePanel.add(new JLabel());

        JButton removeUser = new JButton("Remove user");
        removeUser.setBackground(Color.YELLOW);
        removeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) usersComboBox.getSelectedItem();
                if (loggedInUser instanceof Admin) {
                    ((Admin) loggedInUser).removeUser(name);
                    dispose();
                    previous.dispose();
                    new OptionsMenu(loggedInUser);
                }
            }
        });
        removePanel.add(removeUser);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(removePanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
