package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditSeasons extends JFrame {
    private JComboBox<String> seasonsNames;
    private JLabel selectLabel;

    public EditSeasons(Series s) {
        super("Edit Seasons");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        selectLabel = new JLabel("Select a season: ");
        selectLabel.setForeground(Color.WHITE);
        
        String[] seasons = new String[s.getSeasons().size()];
        int index = 0;
        for (Map.Entry<String, List<Episode>> season : s.getSeasons().entrySet()) {
            seasons[index++] = season.getKey();
        }
        seasonsNames = new JComboBox<>(seasons);
        
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(Color.darkGray);
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.X_AXIS));
        choicePanel.add(selectLabel);
        choicePanel.add(seasonsNames);
        
        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(Color.darkGray);
        selectPanel.setLayout(new GridLayout(8, 1));
        selectPanel.add(choicePanel);
        selectPanel.add(new JLabel());

        seasonsNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPanel.removeAll();
                selectPanel.add(choicePanel);
                selectPanel.add(new JLabel());
                String nameStr = (String) seasonsNames.getSelectedItem();

                List<Episode> episodeList = s.getSeasons().get(nameStr);
                JButton addEpisode = new JButton("Add episode");
                addEpisode.setBackground(Color.YELLOW);
                addEpisode.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AddEpisode(episodeList);
                    }
                });
                selectPanel.add(addEpisode);
                selectPanel.add(new JLabel());

                JButton editEpisodes = new JButton("Edit episodes");
                editEpisodes.setBackground(Color.YELLOW);
                editEpisodes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new EditEpisodes(s, nameStr);
                    }
                });
                selectPanel.add(editEpisodes);
                selectPanel.add(new JLabel());

                JButton saveChanges = new JButton("Save changes");
                saveChanges.setBackground(Color.YELLOW);
                saveChanges.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        s.addSeason(nameStr, episodeList);
                        dispose();
                    }
                });
                selectPanel.add(saveChanges);
                selectPanel.add(new JLabel());
                revalidate();
                repaint();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(selectPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
