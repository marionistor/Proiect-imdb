package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoveGenres extends JFrame {
    private JLabel removeGenre;
    private JComboBox<Genre> genreJComboBox;

    public RemoveGenres(Production p) {
        super("Remove Genre");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);

        removeGenre = new JLabel("Choose a Genre: ");
        removeGenre.setForeground(Color.WHITE);
        Genre[] genres = new Genre[p.getGenres().size()];
        int index = 0;
        for (Genre genre : p.getGenres()) {
            genres[index++] = genre;
        }
        
        genreJComboBox = new JComboBox<>(genres);

        JPanel GenrePanel = new JPanel();
        GenrePanel.setBackground(Color.darkGray);
        GenrePanel.setLayout(new BoxLayout(GenrePanel, BoxLayout.X_AXIS));
        GenrePanel.add(removeGenre);
        GenrePanel.add(genreJComboBox);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3, 1));
        addPanel.setBackground(Color.darkGray);
        addPanel.add(GenrePanel);
        addPanel.add(new JLabel());

        JButton removeGenre = new JButton("Remove Genre");
        removeGenre.setBackground(Color.YELLOW);
        removeGenre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Genre name = (Genre) genreJComboBox.getSelectedItem();
                p.removeGenre(name);
                dispose();
            }
        });
        addPanel.add(removeGenre);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
