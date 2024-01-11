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
            setSize(1000, 700);

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
                    type.setForeground(Color.YELLOW);
                    requestPanel.add(type);
                    requestPanel.setLayout(new GridLayout(6, 1));
                }

                date = new JLabel("" + request.getDate());
                date.setForeground(Color.WHITE);
                requestPanel.add(date);

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
                        String notification;
                        if (request.getSolver().equals("ADMIN")) {
                            Admin.RequestHolder.TeamRequestsList.remove(request);
                            for (Admin admin : IMDB.getInstance().getAdmins()) {
                                notification = "Cerere noua pentru echipa de admini de la \"" + request.getCreator() + "\": " + request.getDescription();
                                admin.removeNotifications(notification);
                            }
                        } else {
                            loggedInUser.getIndividualRequestsList().remove(request);
                            notification = "Cerere noua de la \"" + request.getCreator() + "\": " +request.getDescription();
                            User<?> solverUser = IMDB.getInstance().getUserByName(request.getSolver());
                            solverUser.removeNotifications(notification);
                        }
                        User<?> creatorUser = IMDB.getInstance().getUserByName(request.getCreator());
                        request.addObserver(creatorUser, Event.REJECTED_REQUEST);
                        request.notifyObserver(Event.REJECTED_REQUEST, null, loggedInUser.getUsername(), 0, null);
                        request.removeObserver(creatorUser, Event.REJECTED_REQUEST);
                        if (creatorUser instanceof Regular) {
                            ((Regular) creatorUser).getCreatedRequests().remove(request);
                        }
                        if (creatorUser instanceof Contributor) {
                            ((Contributor) creatorUser).getCreatedRequests().remove(request);
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
                        if (request.getCreator() != null && request.getRequestType() != RequestTypes.DELETE_ACCOUNT && request.getRequestType() != RequestTypes.OTHERS) {
                            User<?> creatorUser = IMDB.getInstance().getUserByName(request.getCreator());
                            creatorUser.updateExperience(new SolvedRequestStrategy());
                        }
                        String notification;
                        if (request.getSolver().equals("ADMIN")) {
                            Admin.RequestHolder.TeamRequestsList.remove(request);
                            for (Admin admin : IMDB.getInstance().getAdmins()) {
                                notification = "Cerere noua pentru echipa de admini de la \"" + request.getCreator() + "\": " + request.getDescription();
                                admin.removeNotifications(notification);
                            }
                        } else {
                            loggedInUser.getIndividualRequestsList().remove(request);
                            notification = "Cerere noua de la \"" + request.getCreator() + "\": " +request.getDescription();
                            User<?> solverUser = IMDB.getInstance().getUserByName(request.getSolver());
                            solverUser.removeNotifications(notification);
                        }
                        User<?> creatorUser = IMDB.getInstance().getUserByName(request.getCreator());
                        if (request.getRequestType() != RequestTypes.DELETE_ACCOUNT) {
                            request.addObserver(creatorUser, Event.SOLVED_REQUEST);
                            request.notifyObserver(Event.SOLVED_REQUEST, null, loggedInUser.getUsername(), 0, null);
                            request.removeObserver(creatorUser, Event.SOLVED_REQUEST);
                        }
                        if (creatorUser instanceof Regular) {
                            ((Regular) creatorUser).getCreatedRequests().remove(request);
                        }
                        if (creatorUser instanceof Contributor) {
                            ((Contributor) creatorUser).getCreatedRequests().remove(request);
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
