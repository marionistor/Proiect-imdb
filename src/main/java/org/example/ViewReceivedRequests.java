package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewReceivedRequests extends JFrame {
    private List<Request> requestList;
    private JLabel type;
    private JLabel date;
    private JLabel titleName;
    private JLabel description;
    private JLabel creator;
    private JScrollPane scroll;

    public ViewReceivedRequests(Staff loggedInUser) {
        super("View Receieved Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        requestList = new ArrayList<>();
        requestList.addAll(loggedInUser.getIndividualRequestsList());
        if (loggedInUser instanceof Admin) {
            requestList.addAll(Admin.RequestHolder.TeamRequestsList);
        }

        JPanel requestsPanel = new JPanel();
        requestsPanel.setBackground(Color.darkGray);

        if (requestList.isEmpty()) {
            setSize(600, 300);
            JLabel noRequestsMsg = new JLabel("You don't have any requests!");
            noRequestsMsg.setForeground(Color.YELLOW);
            requestsPanel.add(noRequestsMsg);
            getContentPane().add(requestsPanel);
        } else {
            setSize(400, 700);

            requestsPanel.setLayout(new GridLayout(requestList.size(), 1));

            for (Request request : requestList) {
                JPanel requestPanel = new JPanel();
                requestPanel.setBackground(Color.darkGray);
                type = new JLabel("" + request.getRequestType());
                if (type.equals("MOVIE_ISSUE") || type.equals("ACTOR_ISSUE")) {
                    requestPanel.setLayout(new GridLayout(7, 1));
                    titleName = new JLabel(request.getTitleName());
                    titleName.setForeground(Color.WHITE);
                } else {
                    requestPanel.setLayout(new GridLayout(6, 1));
                }

                type.setForeground(Color.YELLOW);
                requestPanel.add(type);

                date = new JLabel("" + request.getDate());
                date.setForeground(Color.WHITE);
                requestPanel.add(date);

                if (titleName != null) {
                    requestPanel.add(titleName);
                }

                description = new JLabel(request.getDescription());
                description.setForeground(Color.WHITE);
                requestPanel.add(description);

                if (request.getCreator() != null) {
                    creator = new JLabel("From " + request.getCreator());
                } else {
                    creator = new JLabel("Creator not specified");
                }
                creator.setForeground(Color.WHITE);
                requestPanel.add(creator);


                JButton rejectRequest = new JButton("Reject Request");
                rejectRequest.setBackground(Color.RED);
                rejectRequest.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (request.getSolver().equals("ADMIN")) {
                            Admin.RequestHolder.TeamRequestsList.remove(request);
                        } else {
                            loggedInUser.getIndividualRequestsList().remove(request);
                        }
                        if (request.getCreator() != null) {
                            User<?> creatorUser = IMDB.getInstance().getUser(request.getCreator());
                            creatorUser.notifyUser(loggedInUser, Event.REJECTED_REQUEST, null);
                        }
                        new ViewReceivedRequests(loggedInUser);
                        dispose();
                    }
                });

                JButton markSolved = new JButton("Mark as solved");
                markSolved.setBackground(Color.GREEN);
                markSolved.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (request.getSolver().equals("ADMIN")) {
                            Admin.RequestHolder.TeamRequestsList.remove(request);
                        } else {
                            loggedInUser.getIndividualRequestsList().remove(request);
                        }
                        if (request.getCreator() != null) {
                            User<?> creatorUser = IMDB.getInstance().getUser(request.getCreator());
                            creatorUser.notifyUser(loggedInUser, Event.SOLVED_REQUEST, null);
                        }
                        new ViewReceivedRequests(loggedInUser);
                        dispose();
                    }
                });

                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setBackground(Color.darkGray);
                buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                buttonsPanel.add(markSolved);
                buttonsPanel.add(rejectRequest);

                requestPanel.add(buttonsPanel);
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
