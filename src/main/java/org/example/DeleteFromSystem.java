package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteFromSystem extends JFrame {
    private JComboBox<String> contributionsNames;
    private JLabel SelectContribution;
    private JButton deleteContribution;
    public DeleteFromSystem(Staff logedInUser, JFrame previous) {
        super("Delete Production/Actor from System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 300);
        String[] contributionsStr;
        if (logedInUser instanceof Admin) {
             contributionsStr = new String[logedInUser.getContributions().size() + Admin.commonContributionsList.size()];
        } else {
            contributionsStr = new String[logedInUser.getContributions().size()];
        }
        int index = 0;
        for (Object contribution : logedInUser.getContributions()) {
            if (contribution instanceof Actor) {
                contributionsStr[index++] = ((Actor) contribution).getName();
            }
            if (contribution instanceof Production) {
                contributionsStr[index++] = ((Production) contribution).getTitle();
            }
        }
        if (logedInUser instanceof Admin) {
            for (Object contribution : Admin.commonContributionsList) {
                if (contribution instanceof Actor) {
                    contributionsStr[index++] = ((Actor) contribution).getName();
                }
                if (contribution instanceof Production) {
                    contributionsStr[index++] = ((Production) contribution).getTitle();
                }
            }
        }

        contributionsNames = new JComboBox<>(contributionsStr);
        SelectContribution = new JLabel("Select contribution: ");
        SelectContribution.setForeground(Color.WHITE);

        JPanel SelectPanel = new JPanel();
        SelectPanel.setLayout(new BoxLayout(SelectPanel, BoxLayout.X_AXIS));
        SelectPanel.setBackground(Color.darkGray);
        SelectPanel.add(SelectContribution);
        SelectPanel.add(contributionsNames);

        JPanel DeletePanel = new JPanel();
        DeletePanel.setBackground(Color.darkGray);
        DeletePanel.setLayout(new GridLayout(3, 1));
        DeletePanel.add(SelectPanel);
        DeletePanel.add(new JLabel());

        deleteContribution = new JButton("Delete Production/Actor");
        deleteContribution.setBackground(Color.YELLOW);
        deleteContribution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) contributionsNames.getSelectedItem();
                if (IMDB.getInstance().searchActorName(name)) {
                    logedInUser.removeActorSystem(name);
                } else {
                    logedInUser.removeProductionSystem(name);
                }
                dispose();
                new OptionsMenu(logedInUser);
                previous.dispose();
            }
        });

        DeletePanel.add(deleteContribution);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(DeletePanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
