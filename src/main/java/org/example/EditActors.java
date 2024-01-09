package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EditActors extends JFrame {
    private JComboBox<String> actorsComboBox;
    private JLabel selectLabel;
    private JLabel nameLabel;
    private JTextField name;
    private JButton saveChanges;
    private JButton deleteActor;

    public EditActors(Production p) {
        super("Edit Actor Info");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 300);

        selectLabel = new JLabel("Select actor name: ");
        selectLabel.setForeground(Color.WHITE);

        String[] actors = new String[p.getActors().size()];
        int index = 0;
        for (String actor : p.getActors()) {
            actors[index++] = actor;
        }
        actorsComboBox = new JComboBox<>(actors);

        JPanel actorPanel = new JPanel();
        actorPanel.setBackground(Color.darkGray);
        actorPanel.setLayout(new BoxLayout(actorPanel, BoxLayout.X_AXIS));
        actorPanel.add(selectLabel);
        actorPanel.add(actorsComboBox);

        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(Color.darkGray);
        selectPanel.setLayout(new GridLayout(6, 1));
        selectPanel.add(actorPanel);
        selectPanel.add(new JLabel());
       
        actorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPanel.removeAll();
                selectPanel.add(actorPanel);
                selectPanel.add(new JLabel());
                String actor = (String) actorsComboBox.getSelectedItem();

                nameLabel = new JLabel("Name: ");
                nameLabel.setForeground(Color.WHITE);
                name = new JTextField(50);
                name.setText(actor);

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
                            p.modifyActorName(actor, name.getText());
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(EditActors.this, "You must complete all fields!");
                        }
                    }
                });
                buttonsPanel.add(saveChanges);

                deleteActor = new JButton("Remove Actor");
                deleteActor.setBackground(Color.YELLOW);
                deleteActor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        p.removeActor(actor);
                        for (Request request : new Admin.RequestHolder().getTeamRequestsList()) {
                            if (request.getTitleName() != null && request.getTitleName().equals(actor)) {
                                new Admin.RequestHolder().removeTeamRequest(request);
                            }
                        }
                        dispose();
                    }
                });
                buttonsPanel.add(deleteActor);
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
