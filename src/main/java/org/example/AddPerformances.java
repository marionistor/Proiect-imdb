package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddPerformances extends JFrame {
    private JLabel enterPerformance;
    private JTextField performance;
    private JComboBox<String> type;
    private JLabel selectType;
    public AddPerformances(Actor a) {
        super("Add Perfomance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);


        enterPerformance = new JLabel("Enter title: ");
        enterPerformance.setForeground(Color.WHITE);
        performance = new JTextField(50);

        JPanel perfPanel = new JPanel();
        perfPanel.setBackground(Color.darkGray);
        perfPanel.setLayout(new BoxLayout(perfPanel, BoxLayout.X_AXIS));
        perfPanel.add(enterPerformance);
        perfPanel.add(performance);

        String[] types = {"Movie", "Series"};
        type = new JComboBox<>(types);
        selectType = new JLabel("Select type: ");
        selectType.setForeground(Color.WHITE);

        JPanel typePanel = new JPanel();
        typePanel.setBackground(Color.darkGray);
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
        typePanel.add(selectType);
        typePanel.add(type);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(5, 1));
        addPanel.setBackground(Color.darkGray);
        addPanel.add(perfPanel);
        addPanel.add(new JLabel());
        addPanel.add(typePanel);
        addPanel.add(new JLabel());

        JButton addPerformance = new JButton("Add performance");
        addPerformance.setBackground(Color.YELLOW);
        addPerformance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String perfStr = performance.getText();
                String typeStr = (String) type.getSelectedItem();

                if (perfStr.isEmpty()) {
                    JOptionPane.showMessageDialog(AddPerformances.this, "Complete all fields!");
                } else {
                    if (a.isPerformanceAdded(perfStr, typeStr)) {
                        JOptionPane.showMessageDialog(AddPerformances.this, "Performance already added!");
                    } else {
                        a.addPerformance(perfStr, typeStr);
                        JOptionPane.showMessageDialog(AddPerformances.this, "Added " + perfStr + " " + typeStr + "!");
                    }
                }
            }
        });
        addPanel.add(addPerformance);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
