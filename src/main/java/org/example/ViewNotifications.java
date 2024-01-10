package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;

public class ViewNotifications extends JFrame {
    private List<String> notificationsList;
    private JScrollPane scroll;

    public ViewNotifications(User<?> loggedInUser) {
        super("Notifications");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel notificationsPanel = new JPanel();
        notificationsPanel.setBackground(Color.darkGray);

        if (loggedInUser.getUserNotifications().isEmpty()) {
            setSize(600, 300);
            JLabel noNotificationsMsg = new JLabel("You don't have any notifications right now!");
            noNotificationsMsg.setForeground(Color.YELLOW);
            notificationsPanel.add(noNotificationsMsg);
            getContentPane().add(notificationsPanel);
        } else {
            setSize(1200, 300);
            notificationsList = new ArrayList<>();
            notificationsList.addAll(loggedInUser.getUserNotifications());

            notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));
            for (String notification : notificationsList) {
                JPanel notificationPanel = new JPanel();
                notificationPanel.setBackground(Color.darkGray);
                notificationPanel.setLayout(new FlowLayout());

                JLabel notificationMessage = new JLabel(notification);
                notificationMessage.setForeground(Color.WHITE);
                notificationPanel.add(notificationMessage);

                JButton removeNotification = new JButton("Delete");
                removeNotification.setBackground(Color.RED);
                removeNotification.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loggedInUser.getUserNotifications().remove(notification);
                        new ViewNotifications(loggedInUser);
                        dispose();
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.setBackground(Color.darkGray);
                buttonPanel.add(removeNotification);

                notificationPanel.add(buttonPanel);
                notificationsPanel.add(notificationPanel);
            }
            scroll = new JScrollPane(notificationsPanel);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            getContentPane().add(scroll);
        }
        setVisible(true);
    }
}
