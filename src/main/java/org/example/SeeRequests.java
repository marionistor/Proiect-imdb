package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class SeeRequests extends JFrame {
    private List<Request> requestList;
    private JLabel type;
    private JLabel date;
    private JLabel titleName;
    private JLabel description;
    private JLabel solver;
    private JScrollPane scroll;
    private Staff contributor;

    public SeeRequests(User<?> loggedInUser) {
        super("Created Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (loggedInUser instanceof Regular) {
            requestList = ((Regular) loggedInUser).getCreatedRequests();
        }
        if (loggedInUser instanceof Contributor) {
            requestList = ((Contributor) loggedInUser).getCreatedRequests();
        }

        JPanel requestsPanel = new JPanel();
        requestsPanel.setBackground(Color.darkGray);

        if(requestList.isEmpty()) {
            setSize(600, 300);
            JLabel noRequestsMsg = new JLabel("You didn't create any requests!");
            noRequestsMsg.setForeground(Color.YELLOW);
            requestsPanel.add(noRequestsMsg);
            getContentPane().add(requestsPanel);
        } else {
            setSize(800, 400);

            requestsPanel.setLayout(new GridLayout(requestList.size(), 1));

            for (Request request : requestList) {
                JPanel requestPanel = new JPanel();
                requestPanel.setBackground(Color.darkGray);
                type = new JLabel("" + request.getRequestType());
                if (request.getRequestType() == RequestTypes.MOVIE_ISSUE || request.getRequestType() == RequestTypes.ACTOR_ISSUE) {
                    requestPanel.setLayout(new GridLayout(7, 1));
                    titleName = new JLabel(request.getTitleName());
                    titleName.setForeground(Color.WHITE);
                    type.setForeground(Color.YELLOW);
                    requestPanel.add(type);
                    requestPanel.add(titleName);
                } else {
                    requestPanel.setLayout(new GridLayout(6, 1));
                    type.setForeground(Color.YELLOW);
                    requestPanel.add(type);
                }

                date = new JLabel("" + request.getDate());
                date.setForeground(Color.WHITE);
                requestPanel.add(date);

                description = new JLabel(request.getDescription());
                description.setForeground(Color.WHITE);
                requestPanel.add(description);

                solver = new JLabel("To " + request.getSolver());
                solver.setForeground(Color.WHITE);
                requestPanel.add(solver);

                JButton cancelRequest = new JButton("Cancel Request");
                cancelRequest.setBackground(Color.RED);
                cancelRequest.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (request.getRequestType() == RequestTypes.MOVIE_ISSUE || request.getRequestType() == RequestTypes.ACTOR_ISSUE) {
                            contributor = IMDB.getInstance().contributorUser(request.getTitleName());
                        }
                        if (loggedInUser instanceof Regular) {
                            ((Regular) loggedInUser).removeRequest(request, contributor);
                        }
                        if (loggedInUser instanceof Contributor) {
                            ((Contributor) loggedInUser).removeRequest(request, contributor);
                        }
                        new SeeRequests(loggedInUser);
                        dispose();
                    }
                });
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                buttonPanel.setBackground(Color.darkGray);
                buttonPanel.add(cancelRequest);

                requestPanel.add(buttonPanel);
                requestPanel.add(new JLabel());
                requestsPanel.add(requestPanel);
            }

            scroll = new JScrollPane(requestsPanel);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            getContentPane().add(scroll);
        }

        setVisible(true);
    }
}
