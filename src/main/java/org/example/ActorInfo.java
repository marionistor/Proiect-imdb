package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
public class ActorInfo extends JFrame {
    private JLabel name;
    private JLabel biography;
    private JButton addToFavorites;
    private JButton removeFromFavorites;
    private JButton refresh;

    public ActorInfo(Actor actor, User logedInUser) {
        super("Actor info");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);

        JPanel actorPanel = new JPanel();
        actorPanel.setLayout(new BoxLayout(actorPanel, BoxLayout.Y_AXIS));
        actorPanel.setBackground(Color.darkGray);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.darkGray);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));


        name = new JLabel(actor.getName());
        name.setForeground(Color.YELLOW);
        infoPanel.add(name);

        if (actor.getBiography() !=  null) {
            biography = new JLabel("<html>" + actor.getBiography() + "</html>");
        } else {
            biography = new JLabel("No biography available");
        }
        biography.setForeground(Color.WHITE);
        infoPanel.add(biography);

        JLabel imageLabel = new JLabel(actor.getImageIcon());
        JPanel imgPanel = new JPanel();
        imgPanel.setBackground(Color.darkGray);
        imgPanel.add(imageLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.darkGray);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imgPanel, BorderLayout.WEST);
        imagePanel.add(infoPanel, BorderLayout.CENTER);

        actorPanel.add(imagePanel);

        JPanel performancesPanel = new JPanel();
        performancesPanel.setLayout(new BoxLayout(performancesPanel, BoxLayout.Y_AXIS));
        performancesPanel.setBackground(Color.darkGray);
        JLabel performanceText = new JLabel("Performances: ");
        performanceText.setForeground(Color.YELLOW);
        performancesPanel.add(performanceText);
        performancesPanel.add(new JLabel());

        for (Map.Entry<String, String> performance : actor.getPerformances().entrySet()) {
            JLabel performanceLabel = new JLabel(performance.getKey() + " (" + performance.getValue() + ")");
            performanceLabel.setForeground(Color.WHITE);
            performancesPanel.add(performanceLabel);
        }
        
        JScrollPane performancesScroll = new JScrollPane(performancesPanel);
        performancesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        actorPanel.add(performancesScroll);

        addToFavorites = new JButton("Add To Favorites");
        addToFavorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logedInUser.getFavoritesSet().contains(actor)) {
                    JOptionPane.showMessageDialog(ActorInfo.this, "Already added to favorites!");
                } else {
                    logedInUser.addFavorite(actor);
                }
            }
        });
        addToFavorites.setBackground(Color.GREEN);

        removeFromFavorites = new JButton("Remove From Favorites");
        removeFromFavorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!logedInUser.getFavoritesSet().contains(actor)) {
                    JOptionPane.showMessageDialog(ActorInfo.this, "Actor isn't added to favorites!");
                } else {
                    logedInUser.removeFavorite(actor);
                }
            }
        });
        removeFromFavorites.setBackground(Color.RED);

        refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActorInfo(actor, logedInUser);
                dispose();
            }
        });
        refresh.setBackground(Color.YELLOW);

        JPanel favoritesPanel = new JPanel();
        favoritesPanel.setBackground(Color.darkGray);
        favoritesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        favoritesPanel.add(addToFavorites);
        favoritesPanel.add(removeFromFavorites);


        actorPanel.add(new JLabel());
        actorPanel.add(favoritesPanel);

        JPanel refreshPanel = new JPanel();
        refreshPanel.setBackground(Color.darkGray);
        refreshPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        refreshPanel.add(refresh);


        actorPanel.add(new JLabel());
        actorPanel.add(refreshPanel);

        getContentPane().add(actorPanel);
        getContentPane().setBackground(Color.darkGray);
        setResizable(false);
        setVisible(true);
    }
}
