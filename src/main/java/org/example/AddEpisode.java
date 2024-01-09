package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class AddEpisode extends JFrame {
    private JLabel enterName;
    private JLabel enterDuration;
    private JTextField name;
    private JTextField duration;

    public boolean episodeExists(List<Episode> episodeList, Episode e) {
        if (episodeList != null) {
            for (Episode episode : episodeList) {
                if (episode.getEpisodeName().equals(e.getEpisodeName()) && episode.getDuration().equals(e.getDuration())) {
                    return true;
                }
            }
        }
        return false;
    }
    public AddEpisode(List<Episode> episodeList) {

        super("Add Episode");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);


        enterName = new JLabel("Enter title: ");
        enterName.setForeground(Color.WHITE);
        name = new JTextField(50);

        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.darkGray);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(enterName);
        namePanel.add(name);

        enterDuration = new JLabel("Enter duration(in minutes): ");
        enterDuration.setForeground(Color.WHITE);
        duration = new JTextField(10);

        JPanel durationPanel = new JPanel();
        durationPanel.setBackground(Color.darkGray);
        durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.X_AXIS));
        durationPanel.add(enterDuration);
        durationPanel.add(duration);

        JPanel episodePanel = new JPanel();
        episodePanel.setBackground(Color.darkGray);
        episodePanel.setLayout(new GridLayout(5, 1));
        episodePanel.add(namePanel);
        episodePanel.add(new JLabel());
        episodePanel.add(durationPanel);
        episodePanel.add(new JLabel());

        JButton addEpisode = new JButton("Add episode");
        addEpisode.setBackground(Color.YELLOW);
        addEpisode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameStr = name.getText();
                try {
                    int minutes = Integer.parseInt(duration.getText());

                if (nameStr.isEmpty()) {
                    JOptionPane.showMessageDialog(AddEpisode.this, "Complete all fields!");
                } else if (minutes <= 0) {
                        JOptionPane.showMessageDialog(AddEpisode.this, "Duration out of bounds!");
                } else {
                    Episode episode = new Episode(nameStr, minutes + " minutes");
                    if (episodeExists(episodeList, episode)) {
                        JOptionPane.showMessageDialog(AddEpisode.this, "Episode already added!");
                    } else {
                        episodeList.add(episode);
                        name.setText("");
                        duration.setText("");
                        JOptionPane.showMessageDialog(AddEpisode.this, "Added episode " + nameStr + "!");
                    }
                }
                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(AddEpisode.this, "You must enter an integer!");
                }
            }
        });
        episodePanel.add(addEpisode);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(episodePanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
