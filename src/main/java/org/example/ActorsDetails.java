package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class ActorsDetails extends JFrame {
    private JMenu options;
    private JMenuItem SortAlphabetically;
    private JMenuItem SortRevAlphabetically;
    private JMenuItem defaultView;
    private JScrollPane scroll;

    public ActorsDetails(ArrayList<Actor> actorsList, User logedInUser) {
        super("Actors");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        // panel for actor panels
        JPanel actorsPanel = new JPanel();
        actorsPanel.setBackground(Color.darkGray);
        actorsPanel.setLayout(new GridLayout(IMDB.getInstance().getActorsList().size(), 1));

        for (Actor actor : actorsList) {
            JPanel actorPanel = new JPanel();
            actorPanel.setLayout(new GridLayout(4, 1));
            actorPanel.setBackground(Color.darkGray);
            JLabel name = new JLabel(actor.getName());
            name.setForeground(Color.YELLOW);
            actorPanel.add(name);

            JTextArea biography = new JTextArea(actor.getBiography());
            biography.setLineWrap(true);
            biography.setWrapStyleWord(true);
            biography.setEditable(false);
            biography.setBackground(Color.darkGray);
            biography.setForeground(Color.WHITE);

            actorPanel.add(biography);

            JButton viewMore = new JButton("View More");
            viewMore.setBackground(Color.YELLOW);
            viewMore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ActorInfo(actor, logedInUser);
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.darkGray);
            buttonPanel.add(viewMore);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            actorPanel.add(buttonPanel);

            JLabel imageLabel = new JLabel(actor.getImageIcon());
            JPanel imgPanel = new JPanel();
            imgPanel.setBackground(Color.darkGray);
            imgPanel.add(imageLabel);

            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(Color.darkGray);
            imagePanel.setLayout(new BorderLayout());
            imagePanel.add(imgPanel, BorderLayout.WEST);
            imagePanel.add(actorPanel, BorderLayout.CENTER);

            actorsPanel.add(imagePanel);
        }

        scroll = new JScrollPane(actorsPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        options = new JMenu("Options");
        options.setBackground(Color.darkGray);

        SortAlphabetically = new JMenuItem("Sort A-Z");
        SortAlphabetically.setBackground(Color.YELLOW);
        SortAlphabetically.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Actor> actorsAlphabeticOrder = IMDB.getInstance().sortActorsAlphabetically();
                new ActorsDetails((ArrayList<Actor>) actorsAlphabeticOrder, logedInUser);
                dispose();
            }
        });
        options.add(SortAlphabetically);

        SortRevAlphabetically = new JMenuItem("Sort Z-A");
        SortRevAlphabetically.setBackground(Color.YELLOW);
        SortRevAlphabetically.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Actor> actorsRevAlphabeticOrder = IMDB.getInstance().sortActorsReverseAlphabetically();
                new ActorsDetails((ArrayList<Actor>) actorsRevAlphabeticOrder, logedInUser);
                dispose();
            }
        });
        options.add(SortRevAlphabetically);

        defaultView = new JMenuItem("Default View");
        defaultView.setBackground(Color.YELLOW);
        defaultView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActorsDetails(actorsList, logedInUser);
                dispose();
            }
        });
        options.add(defaultView);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.YELLOW);
        menuBar.add(options);
        setJMenuBar(menuBar);

        getContentPane().add(scroll);
        setResizable(false);
        setVisible(true);
    }
}
