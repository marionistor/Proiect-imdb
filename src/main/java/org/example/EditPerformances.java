package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class EditPerformances extends JFrame {
    private JLabel selectLabel;
    private JComboBox<String> performancesComboBox;
    private JLabel titleLabel;
    private JLabel typeLabel;
    private JTextField title;
    private JComboBox<String> type;
    private JButton deletePerformance;
    private JButton saveChanges;

    public EditPerformances(Actor a) {
        super("Edit Actor Performances");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        selectLabel = new JLabel("Select a performance: ");
        selectLabel.setForeground(Color.WHITE);

        String[] performancesStr = new String[a.getPerformances().size()];
        int index = 0;
        for (Map.Entry<String, String> performance : a.getPerformances().entrySet()) {
            performancesStr[index++] = performance.getKey() + " - " + performance.getValue();
        }

        performancesComboBox = new JComboBox<>(performancesStr);

        JPanel perfPanel = new JPanel();
        perfPanel.setBackground(Color.darkGray);
        perfPanel.setLayout(new BoxLayout(perfPanel, BoxLayout.X_AXIS));
        perfPanel.add(selectLabel);
        perfPanel.add(performancesComboBox);

        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(Color.darkGray);
        selectPanel.setLayout(new GridLayout(8, 1));
        selectPanel.add(perfPanel);
        selectPanel.add(new JLabel());

        performancesComboBox.addActionListener(new ActionListener() {
            String performance = (String) performancesComboBox.getSelectedItem();
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPanel.removeAll();
                selectPanel.add(perfPanel);
                selectPanel.add(new JLabel());
                titleLabel = new JLabel("Title: ");
                titleLabel.setForeground(Color.WHITE);
                title = new JTextField(50);
                String[] perf = performance.split("\\s*-\\s*");
                title.setText(perf[0]);

                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(Color.darkGray);
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
                titlePanel.add(titleLabel);
                titlePanel.add(title);
                selectPanel.add(titlePanel);
                selectPanel.add(new JLabel());

                String[] types = {"Movie", "Series"};
                type = new JComboBox<>(types);
                type.setSelectedItem(perf[1]);
                typeLabel = new JLabel("Type: ");

                JPanel typePanel = new JPanel();
                typePanel.setBackground(Color.darkGray);
                typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
                typePanel.add(typeLabel);
                typePanel.add(type);
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
                        if (!title.getText().isEmpty()) {
                            a.modifyPerformance(perf[0], title.getText(), perf[1], (String) type.getSelectedItem());
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(EditPerformances.this, "You must complete all fields!");
                        }
                    }
                });
                buttonsPanel.add(saveChanges);

                deletePerformance = new JButton("Remove performance");
                deletePerformance.setBackground(Color.YELLOW);
                deletePerformance.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        a.removePerformance(perf[0], perf[1]);
                        dispose();
                    }
                });
                buttonsPanel.add(deletePerformance);
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
