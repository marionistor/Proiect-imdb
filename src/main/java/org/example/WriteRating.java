package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WriteRating extends JFrame {
    private JLabel commentLabel;
    private JTextField comment;
    private JLabel gradeLabel;
    private JComboBox<Integer> grade;
    private JButton addRating;

    public WriteRating(User<?> loggedInUser, Production production, JFrame previous) {
        super("New Rating");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        gradeLabel = new JLabel("Grade: ");
        gradeLabel.setForeground(Color.WHITE);
        commentLabel = new JLabel("Comment: ");
        commentLabel.setForeground(Color.WHITE);
        comment = new JTextField(50);

        Integer[] grades = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        grade = new JComboBox<>(grades);

        JPanel gradePanel = new JPanel();
        gradePanel.setLayout(new BoxLayout(gradePanel, BoxLayout.X_AXIS));
        gradePanel.setBackground(Color.darkGray);
        gradePanel.add(gradeLabel);
        gradePanel.add(grade);

        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.X_AXIS));
        commentPanel.setBackground(Color.darkGray);
        commentPanel.add(commentLabel);
        commentPanel.add(comment);

        JPanel ratingPanel = new JPanel();
        ratingPanel.setBackground(Color.darkGray);
        ratingPanel.setLayout(new GridLayout(5, 1));
        ratingPanel.add(gradePanel);
        ratingPanel.add(new JLabel());
        ratingPanel.add(commentPanel);
        ratingPanel.add(new JLabel());

        addRating = new JButton("Add Review");
        addRating.setBackground(Color.YELLOW);
        addRating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rating newRating = new Rating();
                newRating.setUsername(loggedInUser.getUsername());
                try {
                    newRating.setGrade((Integer) grade.getSelectedItem());
                    newRating.setComment(comment.getText());
                    production.addRating(newRating);
                    production.updateAverageRating();
                    production.addObserver(loggedInUser, Event.RATED_PRODUCTION_REVIEW);
                    if (production instanceof Movie) {
                        production.notifyObserver(Event.FAVORITE_PRODUCTION_REVIEW, "Filmul ", loggedInUser.getUsername(), newRating.getGrade(), newRating.getUsername());
                        production.notifyObserver(Event.RATED_PRODUCTION_REVIEW, "Filmul ",  loggedInUser.getUsername(), newRating.getGrade(), newRating.getUsername());
                        production.notifyObserver(Event.ADDED_PRODUCTION_REVIEW, "Filmul ", loggedInUser.getUsername(), newRating.getGrade(), newRating.getUsername());
                    } else {
                        production.notifyObserver(Event.FAVORITE_PRODUCTION_REVIEW, "Serialul ", loggedInUser.getUsername(), newRating.getGrade(), newRating.getUsername());
                        production.notifyObserver(Event.RATED_PRODUCTION_REVIEW, "Serialul ", loggedInUser.getUsername(), newRating.getGrade(), newRating.getUsername());
                        production.notifyObserver(Event.ADDED_PRODUCTION_REVIEW, "Serialul ", loggedInUser.getUsername(), newRating.getGrade(), newRating.getUsername());
                    }
                    dispose();
                    loggedInUser.updateExperience(new ReviewStrategy());
                    new ProductionInfo(production, loggedInUser);
                    previous.dispose();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(WriteRating.this, "You must select a grade!");
                }
            }
        });

        ratingPanel.add(addRating);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(ratingPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
