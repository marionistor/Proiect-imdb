package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGenres extends JFrame {
    private JLabel addGenre;
    private JComboBox<Genre> genreJComboBox;

    public AddGenres(Production p) {
        super("Add Genre");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);

        addGenre = new JLabel("Choose a Genre: ");
        addGenre.setForeground(Color.WHITE);
        Genre[] genres = {Genre.Action, Genre.Adventure, Genre.Cooking, Genre.Crime, Genre.Biography, Genre.Comedy, Genre.Drama, Genre.Fantasy, Genre.Horror, Genre.Mystery, Genre.Romance, Genre.SF, Genre.Thriller, Genre.War};
        genreJComboBox = new JComboBox<>(genres);

        JPanel GenrePanel = new JPanel();
        GenrePanel.setBackground(Color.darkGray);
        GenrePanel.setLayout(new BoxLayout(GenrePanel, BoxLayout.X_AXIS));
        GenrePanel.add(addGenre);
        GenrePanel.add(genreJComboBox);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3, 1));
        addPanel.setBackground(Color.darkGray);
        addPanel.add(GenrePanel);
        addPanel.add(new JLabel());

        JButton addGenre = new JButton("Add Genre");
        addGenre.setBackground(Color.YELLOW);
        addGenre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Genre name = (Genre) genreJComboBox.getSelectedItem();

                if (p.isGenreAdded(name)) {
                    JOptionPane.showMessageDialog(AddGenres.this, "Genre already added!");
                } else {
                    p.addGenre(name);
                    JOptionPane.showMessageDialog(AddGenres.this, "Added Genre " + name + "!");
                }
            }
        });
        addPanel.add(addGenre);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
