package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class RatingFilter extends JFrame {
    private JComboBox<Integer> minRatingBox;
    private JComboBox<Integer> maxRatingBox;
    private JLabel minLabel;
    private JLabel maxLabel;
    private JButton filter;

    public RatingFilter(User logedInUser, JFrame previousFrame) {
        super("Rating Filter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        minLabel = new JLabel("min Rating: ");
        minLabel.setForeground(Color.WHITE);
        maxLabel = new JLabel("Max Rating: ");
        maxLabel.setForeground(Color.WHITE);

        Integer[] ratings = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        minRatingBox = new JComboBox<>(ratings);
        maxRatingBox = new JComboBox<>(ratings);


        JPanel minPanel = new JPanel();
        minPanel.setLayout(new BoxLayout(minPanel, BoxLayout.X_AXIS));
        minPanel.setBackground(Color.darkGray);
        minPanel.add(minLabel);
        minPanel.add(minRatingBox);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new BoxLayout(maxPanel, BoxLayout.X_AXIS));
        maxPanel.setBackground(Color.darkGray);
        maxPanel.add(maxLabel);
        maxPanel.add(maxRatingBox);

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.darkGray);
        filterPanel.setLayout(new GridLayout(5, 1));
        filterPanel.add(minPanel);
        filterPanel.add(new JLabel());
        filterPanel.add(maxPanel);
        filterPanel.add(new JLabel());

        filter = new JButton("Filter");
        filter.setBackground(Color.YELLOW);
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer minRating = (Integer) minRatingBox.getSelectedItem();
                    Integer maxRating = (Integer) maxRatingBox.getSelectedItem();
                    if (minRating <= maxRating) {
                        List<Production> ratingFilteredList = IMDB.getInstance().getFilteredProductions(minRating.doubleValue(), maxRating.doubleValue());
                        new ProductionsDetails((ArrayList<Production>) ratingFilteredList, logedInUser);
                        previousFrame.dispose();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(RatingFilter.this, "Min Rating must be smaller than or equal to Max Rating!");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(RatingFilter.this, "You must select a number!");
                }
            }
        });
        filterPanel.add(filter);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(filterPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
