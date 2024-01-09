package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class FavoritesDetails extends JFrame {

    private JScrollPane scroll;
    private JMenu options;
    private JMenuItem refreshList;
    public FavoritesDetails(User logedInUser) {
        super("Favorites");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // panel for favorite panels
        JPanel favoritesPanel = new JPanel();
        favoritesPanel.setBackground(Color.darkGray);

        if (logedInUser.getFavoritesSet().isEmpty()) {
            setSize(600, 300);
            JLabel NoFavMessage = new JLabel("You haven't added any Movie/Series/Actor to your favorites list!");
            NoFavMessage.setForeground(Color.YELLOW);
            favoritesPanel.add(NoFavMessage);
            getContentPane().add(favoritesPanel);
        } else {
            setSize(1000, 600);
            favoritesPanel.setLayout(new GridLayout(logedInUser.getFavoritesSet().size(), 1));

            for (Object favorite : logedInUser.getFavoritesSet()) {
                JPanel favoritePanel = new JPanel();
                favoritePanel.setBackground(Color.darkGray);

                // favorite item is an Actor
                if (favorite instanceof Actor) {
                    favoritePanel.setLayout(new GridLayout(4, 1));
                    JLabel name = new JLabel(((Actor) favorite).getName());
                    name.setForeground(Color.YELLOW);
                    favoritePanel.add(name);

                    JTextArea biography = new JTextArea(((Actor) favorite).getBiography());
                    biography.setLineWrap(true);
                    biography.setWrapStyleWord(true);
                    biography.setEditable(false);
                    biography.setBackground(Color.darkGray);
                    biography.setForeground(Color.WHITE);

                    favoritePanel.add(biography);

                    JButton viewMore = new JButton("View More");
                    viewMore.setBackground(Color.YELLOW);
                    viewMore.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new ActorInfo((Actor) favorite, logedInUser);
                        }
                    });

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setBackground(Color.darkGray);
                    buttonPanel.add(viewMore);
                    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    favoritePanel.add(buttonPanel);

                    JLabel imageLabel = new JLabel(((Actor) favorite).getImageIcon());
                    JPanel imgPanel = new JPanel();
                    imgPanel.setBackground(Color.darkGray);
                    imgPanel.add(imageLabel);

                    JPanel imagePanel = new JPanel();
                    imagePanel.setBackground(Color.darkGray);
                    imagePanel.setLayout(new BorderLayout());
                    imagePanel.add(imgPanel, BorderLayout.WEST);
                    imagePanel.add(favoritePanel, BorderLayout.CENTER);

                    favoritesPanel.add(imagePanel);
                }

                // favorite item is a Movie/Series
                if (favorite instanceof Production) {
                    favoritePanel.setLayout(new GridLayout(9, 1));
                    JLabel title = new JLabel(((Production) favorite).getTitle());
                    title.setForeground(Color.YELLOW);
                    favoritePanel.add(title);

                    JLabel releaseYear = new JLabel();
                    // put duration and movie type as infos if it is a movie
                    if (favorite instanceof Movie) {
                        JLabel type = new JLabel("Movie");
                        type.setForeground(Color.WHITE);
                        releaseYear.setText("" + ((Movie) favorite).getReleaseYear());
                        releaseYear.setForeground(Color.WHITE);
                        JLabel duration = new JLabel(((Movie) favorite).getDuration());
                        duration.setForeground(Color.WHITE);
                        favoritePanel.add(releaseYear);
                        favoritePanel.add(type);
                        favoritePanel.add(duration);
                    }
                    if (favorite instanceof Series) {
                        // put number of seasons and series type as infos if it is a series
                        JLabel type = new JLabel("Series");
                        type.setForeground(Color.WHITE);
                        releaseYear.setText("" + ((Series) favorite).getReleaseYear());
                        releaseYear.setForeground(Color.WHITE);
                        JLabel seasonsNum = new JLabel(((Series) favorite).getNumSeasons() + " seasons");
                        seasonsNum.setForeground(Color.WHITE);
                        favoritePanel.add(releaseYear);
                        favoritePanel.add(type);
                        favoritePanel.add(seasonsNum);
                    }
                    JLabel setBounds = new JLabel(((Production) favorite).getAverageRating() + " \\ 10.0");
                    setBounds.setForeground(Color.WHITE);
                    favoritePanel.add(setBounds);
                    JLabel genres = new JLabel("" + ((Production) favorite).getGenres());
                    genres.setForeground(Color.WHITE);
                    favoritePanel.add(genres);


                    JButton viewMore = new JButton("View More");
                    viewMore.setBackground(Color.YELLOW);
                    viewMore.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new ProductionInfo((Production) favorite, logedInUser);
                        }
                    });
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setBackground(Color.darkGray);
                    buttonPanel.add(viewMore);
                    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    favoritePanel.add(buttonPanel);

                    JLabel imageLabel = new JLabel(((Production) favorite).getImageIcon());
                    JPanel imgPanel = new JPanel();
                    imgPanel.setBackground(Color.darkGray);
                    imgPanel.add(imageLabel);

                    JPanel imagePanel = new JPanel();
                    imagePanel.setBackground(Color.darkGray);
                    imagePanel.setLayout(new BorderLayout());
                    imagePanel.add(imgPanel, BorderLayout.WEST);
                    imagePanel.add(favoritePanel, BorderLayout.CENTER);

                    favoritesPanel.add(imagePanel);
                }
            }

            scroll = new JScrollPane(favoritesPanel);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            options = new JMenu("Options");
            options.setBackground(Color.YELLOW);

            refreshList = new JMenuItem("Refresh");
            refreshList.setBackground(Color.YELLOW);
            refreshList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new FavoritesDetails(logedInUser);
                    dispose();
                }
            });
            options.add(refreshList);

            JMenuBar menuBar = new JMenuBar();
            menuBar.setBackground(Color.YELLOW);
            menuBar.add(options);
            setJMenuBar(menuBar);

            getContentPane().add(scroll);
        }
        setVisible(true);
    }
}
