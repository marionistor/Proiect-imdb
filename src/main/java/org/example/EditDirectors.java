package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditDirectors extends JFrame {
    private JLabel selectLabel;
    private JComboBox<String> directorsComboBox;
    private JLabel nameLabel;
    private JTextField name;
    private JButton deleteDirector;
    private JButton saveChanges;

    public EditDirectors(Production p) {
        super("Edit Directors Info");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        selectLabel = new JLabel("Select director name: ");
        selectLabel.setForeground(Color.WHITE);

        String[] directors = new String[p.getDirectors().size()];
        int index = 0;
        for (String director : p.getDirectors()) {
            directors[index++] = director;
        }

        directorsComboBox = new JComboBox<>(directors);

        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.X_AXIS));
        choicePanel.setBackground(Color.darkGray);
        choicePanel.add(selectLabel);
        choicePanel.add(directorsComboBox);

        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(Color.darkGray);
        selectPanel.setLayout(new GridLayout(6, 1));
        selectPanel.add(choicePanel);
        selectPanel.add(new JLabel());

        directorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String director = (String) directorsComboBox.getSelectedItem();
                selectPanel.removeAll();
                selectPanel.add(choicePanel);
                selectPanel.add(new JLabel());

                nameLabel = new JLabel("Name: ");
                nameLabel.setForeground(Color.WHITE);
                name = new JTextField(50);
                name.setText(director);

                JPanel namePanel = new JPanel();
                namePanel.setBackground(Color.darkGray);
                namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
                namePanel.add(nameLabel);
                namePanel.add(name);
                selectPanel.add(namePanel);
                selectPanel.add(new JLabel());

                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setBackground(Color.darkGray);
                buttonsPanel.setLayout(new FlowLayout());
                saveChanges = new JButton("Save Changes");
                saveChanges.setBackground(Color.YELLOW);
                saveChanges.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!name.getText().isEmpty()) {
                            p.modifyDirectorName(director, name.getText());
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(EditDirectors.this, "You must complete all fields!");
                        }
                    }
                });
                buttonsPanel.add(saveChanges);

                deleteDirector = new JButton("Remove director");
                deleteDirector.setBackground(Color.YELLOW);
                deleteDirector.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        p.removeDirector(director);
                        dispose();
                    }
                });
                buttonsPanel.add(deleteDirector);
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
