package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AddActors extends JFrame {
    private JLabel addActor;
    private JTextField Actor;

    public AddActors(Production p, String type) {
        super("Add Actor to Production");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 300);

        addActor = new JLabel("Enter actor name: ");
        addActor.setForeground(Color.WHITE);
        Actor = new JTextField(50);

        JPanel ActorPanel = new JPanel();
        ActorPanel.setBackground(Color.darkGray);
        ActorPanel.setLayout(new BoxLayout(ActorPanel, BoxLayout.X_AXIS));
        ActorPanel.add(addActor);
        ActorPanel.add(Actor);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3, 1));
        addPanel.setBackground(Color.darkGray);
        addPanel.add(ActorPanel);
        addPanel.add(new JLabel());

        JButton addActor = new JButton("Add Actor");
        addActor.setBackground(Color.YELLOW);
        addActor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = Actor.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(AddActors.this, "Enter a name!");
                } else {
                    if (p.isActorAdded(name)) {
                        JOptionPane.showMessageDialog(AddActors.this, "Actor already added!");
                    } else {
                        p.addActor(name);
                        if (!IMDB.getInstance().searchActorName(name)) {
                            Actor a = new Actor();
                            a.setName(name);
                            a.addPerformance(p.getTitle(), type);

                            Request request = new Request();
                            request.setRequestType(RequestTypes.OTHERS);
                            request.setSolver("ADMIN");
                            String description = "Please add the actor " + name +".";
                            request.setDescription(description);
                            request.setTitleName(name);
                            ZonedDateTime date = ZonedDateTime.now();
                            DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                            String dateStr = date.format(DTFormatter);
                            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DTFormatter);
                            request.setDate(dateTime);
                            new Admin.RequestHolder().addTeamRequest(request);
                            // create request
                            // notify
                        }
                        JOptionPane.showMessageDialog(AddActors.this, "Added Actor " + name + "!");
                        Actor.setText("");
                    }
                }
            }
        });
        addPanel.add(addActor);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
