package org.example;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class ProductionInfo extends JFrame {
    private JLabel title;
    private JLabel releaseYear;
    private JLabel type;
    private JLabel directors;
    private JLabel actors;
    private JLabel genres;
    private JLabel plot;
    private JLabel averageRating;
    private JLabel duration;
    private JLabel seasonsNum;
    private JButton addToFavorites;
    private JButton addRating;
    private JButton removeRating;
    private JButton removeFromFavorites;
    private JButton refresh;

    public ProductionInfo(Production production, User logedInUser) {
        super("Production info");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        title = new JLabel(production.getTitle());
        title.setForeground(Color.YELLOW);



        JPanel productionPanel = new JPanel();
        productionPanel.setBackground(Color.darkGray);
        productionPanel.setLayout(new BoxLayout(productionPanel, BoxLayout.Y_AXIS));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.darkGray);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        if (production instanceof Movie) {
            setSize(800, 500);
            if (((Movie) production).getReleaseYear() != 0) {
                infoPanel.add(title);
                releaseYear = new JLabel("" + ((Movie) production).getReleaseYear());
                releaseYear.setForeground(Color.WHITE);
                infoPanel.add(releaseYear);
                type = new JLabel("Movie");
                type.setForeground(Color.WHITE);
                infoPanel.add(type);
                duration = new JLabel(((Movie) production).getDuration());
                duration.setForeground(Color.WHITE);
                infoPanel.add(duration);
            }
        }

        if (production instanceof Series) {
            setSize(1000, 1000);
            if (((Series) production).getReleaseYear() != 0) {
                infoPanel.add(title);
                releaseYear = new JLabel("" + ((Series) production).getReleaseYear());
                releaseYear.setForeground(Color.WHITE);
                infoPanel.add(releaseYear);
                type = new JLabel("Series");
                type.setForeground(Color.WHITE);
                infoPanel.add(type);
            }
        }

        plot = new JLabel("<html>" + production.getPlot() +  "</html>");
        plot.setForeground(Color.WHITE);
        infoPanel.add(plot);

        averageRating = new JLabel(production.getAverageRating() + " \\ 10.0");
        averageRating.setForeground(Color.WHITE);
        infoPanel.add(averageRating);

        directors = new JLabel("Directors: " + production.getDirectors());
        directors.setForeground(Color.WHITE);
        infoPanel.add(directors);

        actors = new JLabel("Actors: " + production.getActors());
        actors.setForeground(Color.WHITE);
        infoPanel.add(actors);

        genres = new JLabel("" + production.getGenres());
        genres.setForeground(Color.WHITE);
        infoPanel.add(genres);

        JLabel imageLabel = new JLabel(production.getImageIcon());
        JPanel imgPanel = new JPanel();
        imgPanel.setBackground(Color.darkGray);
        imgPanel.add(imageLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.darkGray);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imgPanel, BorderLayout.WEST);
        imagePanel.add(infoPanel, BorderLayout.CENTER);

        productionPanel.add(imagePanel);

        if (production instanceof Series) {
            seasonsNum = new JLabel(((Series) production).getNumSeasons() + " seasons");
            seasonsNum.setForeground(Color.WHITE);
            infoPanel.add(seasonsNum);

            JPanel seasonsPanel = new JPanel();
            seasonsPanel.setLayout(new BoxLayout(seasonsPanel, BoxLayout.Y_AXIS));
            seasonsPanel.setBackground(Color.darkGray);

            for (Map.Entry<String, List<Episode>> season : (((Series) production).getSeasons()).entrySet()) {
                JPanel episodePanel = new JPanel();
                episodePanel.setBackground(Color.darkGray);
                episodePanel.setLayout(new BoxLayout(episodePanel, BoxLayout.Y_AXIS));
                
                JLabel name = new JLabel(season.getKey());
                name.setForeground(Color.YELLOW);
                episodePanel.add(name);

                int index = 1;
                for (Episode episode : season.getValue()) {
                    JLabel episodeLabel = new JLabel("Episode " + index + ": \"" + episode.getEpisodeName() + "\", duration: " + episode.getDuration());
                    episodeLabel.setForeground(Color.WHITE);
                    episodePanel.add(episodeLabel);
                    index++;
                }
                
                seasonsPanel.add(episodePanel);
            }

            JScrollPane seasonsScroll = new JScrollPane(seasonsPanel);
            seasonsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            productionPanel.add(seasonsScroll);
        }

        JPanel ratingsPanel = new JPanel();
        ratingsPanel.setBackground(Color.darkGray);
        ratingsPanel.setLayout(new BoxLayout(ratingsPanel, BoxLayout.Y_AXIS));

        List<Rating> ratings = production.getRatings();
        ratings.sort(new ExperienceComparator());

        for (Rating rating : ratings) {
            JLabel user = new JLabel(rating.getUsername());
            user.setForeground(Color.YELLOW);
            ratingsPanel.add(user);

            JLabel grade = new JLabel("" + rating.getGrade());
            grade.setForeground(Color.WHITE);
            ratingsPanel.add(grade);

            JLabel comment = new JLabel(rating.getComment());
            comment.setForeground(Color.WHITE);
            ratingsPanel.add(comment);
        }

        JScrollPane ratingsScroll = new JScrollPane(ratingsPanel);
        ratingsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        productionPanel.add(ratingsScroll);

        addToFavorites = new JButton("Add To Favorites");
        addToFavorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logedInUser.getFavoritesSet().contains(production)) {
                    JOptionPane.showMessageDialog(ProductionInfo.this, "Already added to favorites!");
                } else {
                    logedInUser.addFavorite(production);
                }
            }
        });
        addToFavorites.setBackground(Color.GREEN);

        addRating = new JButton("Add Review");
        addRating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (production.isRated(logedInUser.getUsername())) {
                    JOptionPane.showMessageDialog(ProductionInfo.this, "Already added a review!");
                } else {
                    new WriteRating(logedInUser, production, ProductionInfo.this);
                }
            }
        });
        addRating.setBackground(Color.GREEN);

        removeRating = new JButton("Delete Review");
        removeRating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!production.isRated(logedInUser.getUsername())) {
                    JOptionPane.showMessageDialog(ProductionInfo.this, "No review added!");
                } else {
                    Rating rating = production.getRating(logedInUser.getUsername());
                    production.removeRating(rating);
                    production.updateAverageRating();
                    new ProductionInfo(production, logedInUser);
                    dispose();
                }
            }
        });
        removeRating.setBackground(Color.RED);

        removeFromFavorites = new JButton("Remove From Favorites");
        removeFromFavorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!logedInUser.getFavoritesSet().contains(production)) {
                    JOptionPane.showMessageDialog(ProductionInfo.this, "Production isn't added to favorites!");
                } else {
                    logedInUser.removeFavorite(production);
                }
            }
        });
        removeFromFavorites.setBackground(Color.RED);

        refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductionInfo(production, logedInUser);
                dispose();
            }
        });
        refresh.setBackground(Color.YELLOW);

        JPanel favoritesPanel = new JPanel();
        favoritesPanel.setBackground(Color.darkGray);
        favoritesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        favoritesPanel.add(addToFavorites);
        favoritesPanel.add(removeFromFavorites);

        JPanel addRatingPanel = new JPanel();
        addRatingPanel.setBackground(Color.darkGray);
        addRatingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addRatingPanel.add(addRating);
        addRatingPanel.add(removeRating);

        productionPanel.add(new JLabel());
        productionPanel.add(favoritesPanel);
        productionPanel.add(new JLabel());
        if (logedInUser instanceof Regular) {
            productionPanel.add(addRatingPanel);
            productionPanel.add(new JLabel());
        }

        JPanel refreshPanel = new JPanel();
        refreshPanel.setBackground(Color.darkGray);
        refreshPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        refreshPanel.add(refresh);

        productionPanel.add(refreshPanel);

        getContentPane().add(productionPanel);
        getContentPane().setBackground(Color.darkGray);
        setResizable(false);
        setVisible(true);
    }
}
