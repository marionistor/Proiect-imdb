package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddDirectors extends JFrame {
    private JLabel addDirector;
    private JTextField director;

    public AddDirectors(Production p) {
        super("Add Director");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 300);

        addDirector = new JLabel("Enter director name: ");
        addDirector.setForeground(Color.WHITE);
        director = new JTextField(50);

        JPanel directorPanel = new JPanel();
        directorPanel.setBackground(Color.darkGray);
        directorPanel.setLayout(new BoxLayout(directorPanel, BoxLayout.X_AXIS));
        directorPanel.add(addDirector);
        directorPanel.add(director);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3, 1));
        addPanel.setBackground(Color.darkGray);
        addPanel.add(directorPanel);
        addPanel.add(new JLabel());

        JButton addDirector = new JButton("Add Director");
        addDirector.setBackground(Color.YELLOW);
        addDirector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = director.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(AddDirectors.this, "Enter a name!");
                } else {
                    if (p.isDirectorAdded(name)) {
                        JOptionPane.showMessageDialog(AddDirectors.this, "Director already added!");
                    } else {
                        p.addDirector(name);
                        JOptionPane.showMessageDialog(AddDirectors.this, "Added director " + name + "!");
                        director.setText("");
                    }
                }
            }
        });
        addPanel.add(addDirector);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
