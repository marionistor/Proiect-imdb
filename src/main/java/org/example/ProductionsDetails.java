package org.example;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ProductionsDetails extends JFrame {
    private JMenu filterByRating;
    private JMenu filterByGenre;
    private JMenu defaultView;
    private JMenuItem setDefault;
    private JMenuItem setBounds;
    private JMenuItem Action;
    private JMenuItem Adventure;
    private JMenuItem Comedy;
    private JMenuItem Drama;
    private JMenuItem Horror;
    private JMenuItem SF;
    private JMenuItem Fantasy;
    private JMenuItem Romance;
    private JMenuItem Mystery;
    private JMenuItem Thriller;
    private JMenuItem Crime;
    private JMenuItem Biography;
    private JMenuItem War;
    private JMenuItem Cooking;
    private JScrollPane scroll;



    public ProductionsDetails(ArrayList<Production> productionsList, User logedInUser) {
        super("Productions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 1000);

        // panel for production panels
        JPanel productionsPanel = new JPanel();
        productionsPanel.setBackground(Color.darkGray);
        productionsPanel.setLayout(new GridLayout(IMDB.getInstance().getProductionsList().size(), 1));

        for (Production production : productionsList) {
            JPanel productionPanel = new JPanel();
            productionPanel.setLayout(new GridLayout(9, 1));
            productionPanel.setBackground(Color.darkGray);

            JLabel title = new JLabel(production.getTitle());
            title.setForeground(Color.YELLOW);
            productionPanel.add(title);

            JLabel releaseYear = new JLabel();
            // put duration and movie type as infos if it is a movie
            if (production instanceof Movie) {
                JLabel type = new JLabel("Movie");
                type.setForeground(Color.WHITE);
                releaseYear.setText("" + ((Movie) production).getReleaseYear());
                releaseYear.setForeground(Color.WHITE);
                JLabel duration = new JLabel(((Movie) production).getDuration());
                duration.setForeground(Color.WHITE);
                productionPanel.add(releaseYear);
                productionPanel.add(type);
                productionPanel.add(duration);
            }
            if (production instanceof Series) {
                // put number of seasons and series type as infos if it is a series
                JLabel type = new JLabel("Series");
                type.setForeground(Color.WHITE);
                releaseYear.setText("" + ((Series) production).getReleaseYear());
                releaseYear.setForeground(Color.WHITE);
                JLabel seasonsNum = new JLabel(((Series) production).getNumSeasons() + " seasons");
                seasonsNum.setForeground(Color.WHITE);
                productionPanel.add(releaseYear);
                productionPanel.add(type);
                productionPanel.add(seasonsNum);
            }
            JLabel setBounds = new JLabel(production.getAverageRating() + " \\ 10.0");
            setBounds.setForeground(Color.WHITE);
            productionPanel.add(setBounds);
            JLabel genres = new JLabel("" + production.getGenres());
            genres.setForeground(Color.WHITE);
            productionPanel.add(genres);
            JButton viewMore = new JButton("View More");
            viewMore.setBackground(Color.YELLOW);
            viewMore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ProductionInfo(production, logedInUser);
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.darkGray);
            buttonPanel.add(viewMore);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            productionPanel.add(buttonPanel);

            JLabel imageLabel = new JLabel(production.getImageIcon());
            JPanel imgPanel = new JPanel();
            imgPanel.setBackground(Color.darkGray);
            imgPanel.add(imageLabel);

            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(Color.darkGray);
            imagePanel.setLayout(new BorderLayout());
            imagePanel.add(imgPanel, BorderLayout.WEST);
            imagePanel.add(productionPanel, BorderLayout.CENTER);

            // add to page panel
            productionsPanel.add(imagePanel);
        }

        filterByRating = new JMenu("Filter by Rating");
        filterByRating.setBackground(Color.YELLOW);
        
        setBounds = new JMenuItem("Set Bounds");
        setBounds.setBackground(Color.YELLOW);
        setBounds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RatingFilter filterFrame = new RatingFilter(logedInUser, ProductionsDetails.this);
            }
        });
        filterByRating.add(setBounds);

        filterByGenre = new JMenu("Filter by Genre");
        filterByGenre.setBackground(Color.YELLOW);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genreStr = ((Component) e.getSource()).getName();
                List<Production> genreProductions = IMDB.getInstance().getGenreProductions(Genre.valueOf(genreStr));
                new ProductionsDetails((ArrayList<Production>) genreProductions, logedInUser);
                dispose();
            }
        };

        Action = new JMenuItem("Action");
        Action.setName("Action");
        Action.setBackground(Color.YELLOW);
        Action.addActionListener(listener);
        filterByGenre.add(Action);

        Adventure = new JMenuItem("Adventure");
        Adventure.setName("Adventure");
        Adventure.setBackground(Color.YELLOW);
        Adventure.addActionListener(listener);
        filterByGenre.add(Adventure);

        Comedy = new JMenuItem("Comedy");
        Comedy.setName("Comedy");
        Comedy.setBackground(Color.YELLOW);
        Comedy.addActionListener(listener);
        filterByGenre.add(Comedy);

        Drama = new JMenuItem("Drama");
        Drama.setName("Drama");
        Drama.setBackground(Color.YELLOW);
        Drama.addActionListener(listener);
        filterByGenre.add(Drama);

        Horror = new JMenuItem("Horror");
        Horror.setName("Horror");
        Horror.setBackground(Color.YELLOW);
        Horror.addActionListener(listener);
        filterByGenre.add(Horror);

        SF = new JMenuItem("SF");
        SF.setName("SF");
        SF.setBackground(Color.YELLOW);
        SF.addActionListener(listener);
        filterByGenre.add(SF);

        Fantasy = new JMenuItem("Fantasy");
        Fantasy.setName("Fantasy");
        Fantasy.setBackground(Color.YELLOW);
        Fantasy.addActionListener(listener);
        filterByGenre.add(Fantasy);

        Romance = new JMenuItem("Romance");
        Romance.setName("Romance");
        Romance.setBackground(Color.YELLOW);
        Romance.addActionListener(listener);
        filterByGenre.add(Romance);

        Mystery = new JMenuItem("Mystery");
        Mystery.setName("Mystery");
        Mystery.setBackground(Color.YELLOW);
        Mystery.addActionListener(listener);
        filterByGenre.add(Mystery);

        Thriller = new JMenuItem("Thriller");
        Thriller.setName("Thriller");
        Thriller.setBackground(Color.YELLOW);
        Thriller.addActionListener(listener);
        filterByGenre.add(Thriller);

        Crime = new JMenuItem("Crime");
        Crime.setName("Crime");
        Crime.setBackground(Color.YELLOW);
        Crime.addActionListener(listener);
        filterByGenre.add(Crime);

        Biography = new JMenuItem("Biography");
        Biography.setName("Biography");
        Biography.setBackground(Color.YELLOW);
        Biography.addActionListener(listener);
        filterByGenre.add(Biography);

        War = new JMenuItem("War");
        War.setName("War");
        War.setBackground(Color.YELLOW);
        War.addActionListener(listener);
        filterByGenre.add(War);

        Cooking = new JMenuItem("Cooking");
        Cooking.setName("Cooking");
        Cooking.setBackground(Color.YELLOW);
        Cooking.addActionListener(listener);
        filterByGenre.add(Cooking);

        defaultView = new JMenu("defaultView");
        defaultView.setBackground(Color.YELLOW);

        setDefault = new JMenuItem("Default view");
        setDefault.setBackground(Color.YELLOW);
        setDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductionsDetails(productionsList, logedInUser);
                dispose();
            }
        });
        defaultView.add(setDefault);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.YELLOW);
        menuBar.add(filterByRating);
        menuBar.add(filterByGenre);
        menuBar.add(defaultView);
        setJMenuBar(menuBar);

        scroll = new JScrollPane(productionsPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scroll);
        setVisible(true);
    }

}
