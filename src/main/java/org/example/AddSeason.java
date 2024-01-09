package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class AddSeason extends JFrame {
    private JLabel addSeasonLabel;
    private JTextField Season;
    public AddSeason(Series s, int numSeasons, String operationType) {
        super("Add Season");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 300);

        addSeasonLabel = new JLabel("Current Season: ");
        addSeasonLabel.setForeground(Color.WHITE);
        Season = new JTextField(50);
        if (operationType.equals("Update info")) {
            int index = s.getSeasons().size() + 1;
            Season.setText("Season " + index);
        } else {
            Season.setText("Season " + s.getSeasonIndex());
        }
        Season.setEditable(false);

        JPanel SeasonPanel = new JPanel();
        SeasonPanel.setBackground(Color.darkGray);
        SeasonPanel.setLayout(new BoxLayout(SeasonPanel, BoxLayout.X_AXIS));
        SeasonPanel.add(addSeasonLabel);
        SeasonPanel.add(Season);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(5, 1));
        addPanel.setBackground(Color.darkGray);
        addPanel.add(SeasonPanel);
        addPanel.add(new JLabel());

        List<Episode> episodeList = new ArrayList<>();
        JButton addEpisode = new JButton("Add episode");
        addEpisode.setBackground(Color.YELLOW);
        addEpisode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEpisode(episodeList);
            }
        });
        addPanel.add(addEpisode);
        addPanel.add(new JLabel());

        JButton addSeason = new JButton("Add Season");
        addSeason.setBackground(Color.YELLOW);
        addSeason.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = Season.getText();
                if (!episodeList.isEmpty()) {
                    if (s.getAddedSeasons() >= numSeasons && operationType.equals("Add to system")) {
                        JOptionPane.showMessageDialog(AddSeason.this, "There must be " + numSeasons + " season(s)!");
                    } else {
                        List<Episode> copyList = new ArrayList<>(episodeList);
                        s.addSeason(name, copyList);
                        s.incrementAddedSeasons();
                        if (operationType.equals("Update info")) {
                            s.incrementSeasons();
                        }
                        if (operationType.equals("Update info")) {
                            JOptionPane.showMessageDialog(AddSeason.this, "Added Season " + s.getSeasons().size() + "!");
                        } else {
                            JOptionPane.showMessageDialog(AddSeason.this, "Added Season " + s.getSeasonIndex() + "!");
                        }
                        episodeList.clear();
                        s.incrementSeasonIndex();
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(AddSeason.this, "You must add episodes to the season!");
                }
            }
        });
        addPanel.add(addSeason);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
