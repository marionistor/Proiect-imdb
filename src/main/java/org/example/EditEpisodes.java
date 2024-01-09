package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditEpisodes extends JFrame {
    private JLabel selectLabel;
    private JComboBox<String> episodesName;
    private JLabel titleLabel;
    private JTextField title;
    private JLabel durationLabel;
    private JTextField duration;
    private JButton saveChanges;
    private JButton deleteEpisode;
    public EditEpisodes(Series s, String seasonName) {
        super("Edit Seasons");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        selectLabel = new JLabel("Select episode name: ");
        selectLabel.setForeground(Color.WHITE);

        String[] names = new String[s.getSeasons().get(seasonName).size()];
        int index = 0;
        for (Episode episode : s.getSeasons().get(seasonName)) {
            String[] epDuration = episode.getDuration().split(" ");
            names[index++] = episode.getEpisodeName() + " - " + epDuration[0];
        }
        episodesName = new JComboBox<>(names);
        
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(Color.darkGray);
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.X_AXIS));
        choicePanel.add(selectLabel);
        choicePanel.add(episodesName);
        
        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(Color.darkGray);
        selectPanel.setLayout(new GridLayout(8, 1));
        selectPanel.add(choicePanel);
        selectPanel.add(new JLabel());

        episodesName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ep = (String) episodesName.getSelectedItem();
                String[] titleStr = ep.split("\\s*-\\s*");

                selectPanel.removeAll();
                selectPanel.add(choicePanel);
                selectPanel.add(new JLabel());

                titleLabel = new JLabel("Title: ");
                titleLabel.setForeground(Color.WHITE);
                title = new JTextField(50);
                title.setText(titleStr[0]);

                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(Color.darkGray);
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
                titlePanel.add(titleLabel);
                titlePanel.add(title);
                selectPanel.add(titlePanel);
                selectPanel.add(new JLabel());

                durationLabel = new JLabel("Duration(in minutes): ");
                durationLabel.setForeground(Color.WHITE);
                duration = new JTextField(50);
                duration.setText(titleStr[1]);


                JPanel typePanel = new JPanel();
                typePanel.setBackground(Color.darkGray);
                typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
                typePanel.add(durationLabel);
                typePanel.add(duration);
                selectPanel.add(typePanel);
                selectPanel.add(new JLabel());

                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setBackground(Color.darkGray);
                buttonsPanel.setLayout(new FlowLayout());
                saveChanges = new JButton("Save Changes");
                saveChanges.setBackground(Color.YELLOW);
                saveChanges.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String titleString = title.getText();
                        try {
                            int minutes = Integer.parseInt(duration.getText());

                            if (titleString.isEmpty()) {
                                JOptionPane.showMessageDialog(EditEpisodes.this, "Complete all fields!");
                            } else if (minutes <= 0) {
                                    JOptionPane.showMessageDialog(EditEpisodes.this, "Duration out of bounds!");
                            } else {
                                s.modifyEpisode(seasonName, titleStr[0], titleString, Integer.parseInt(titleStr[1]), minutes);
                                dispose();
                            }
                        } catch (NumberFormatException numberFormatException) {
                            JOptionPane.showMessageDialog(EditEpisodes.this, "You must enter an integer!");
                        }
                    }
                });
                buttonsPanel.add(saveChanges);

                deleteEpisode = new JButton("Remove episode");
                deleteEpisode.setBackground(Color.YELLOW);
                deleteEpisode.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (s.getSeasons().get(seasonName).size() > 1) {
                            s.deleteEpisode(seasonName, titleStr[0], Integer.parseInt(titleStr[1]));
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(EditEpisodes.this, "A season must have at least one episode!");
                        }
                    }
                });
                buttonsPanel.add(deleteEpisode);
                selectPanel.add(buttonsPanel);
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
