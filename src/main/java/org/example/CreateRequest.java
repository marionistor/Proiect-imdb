package org.example;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class CreateRequest extends JFrame {
    private JLabel requestTypeText;
    private JComboBox<RequestTypes> requestTypesJComboBox;
    private JTextField description;
    private JLabel descriptionLabel;
    private JButton createRequest;
    private JLabel selectNameTitle;
    private JComboBox<String> ProductionsActorsNames;
    private Staff contributor;

    public CreateRequest(User logedInUser) {
        super("Create Request");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        requestTypeText = new JLabel("Choose request type: ");
        requestTypeText.setForeground(Color.WHITE);
        RequestTypes[] requestTypes = {RequestTypes.DELETE_ACCOUNT, RequestTypes.ACTOR_ISSUE, RequestTypes.MOVIE_ISSUE, RequestTypes.OTHERS};
        requestTypesJComboBox = new JComboBox<>(requestTypes);

        // request panel
        JPanel requestTypePanel = new JPanel();
        requestTypePanel.setBackground(Color.darkGray);
        requestTypePanel.setLayout(new BoxLayout(requestTypePanel, BoxLayout.X_AXIS));
        requestTypePanel.add(requestTypeText);
        requestTypePanel.add(requestTypesJComboBox);

        descriptionLabel = new JLabel("Enter request description: ");
        descriptionLabel.setForeground(Color.WHITE);
        description = new JTextField(50);

        // description panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.darkGray);
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(description);

        JPanel requestInfoPanel = new JPanel();
        requestInfoPanel.setBackground(Color.darkGray);
        requestInfoPanel.setLayout(new GridLayout(7, 1));
        requestInfoPanel.add(requestTypePanel);
        requestInfoPanel.add(new JLabel());

        requestTypesJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestInfoPanel.removeAll();
                requestInfoPanel.add(requestTypePanel);
                requestInfoPanel.add(new JLabel());
                try {
                    RequestTypes type = (RequestTypes) requestTypesJComboBox.getSelectedItem();

                    if ((type == RequestTypes.MOVIE_ISSUE || type == RequestTypes.ACTOR_ISSUE)) {
                        if (type == RequestTypes.MOVIE_ISSUE) {
                            ProductionsActorsNames = new JComboBox<>(IMDB.getInstance().getProductionsNames(logedInUser));
                            selectNameTitle = new JLabel("Select Movie/Series title: ");
                        } else {
                            ProductionsActorsNames = new JComboBox<>(IMDB.getInstance().getActorsNames(logedInUser));
                            selectNameTitle = new JLabel("Select Actor name: ");
                        }

                        selectNameTitle.setForeground(Color.WHITE);
                        JPanel ProdActorSelectionPanel = new JPanel();
                        ProdActorSelectionPanel.setBackground(Color.darkGray);
                        ProdActorSelectionPanel.setLayout(new BoxLayout(ProdActorSelectionPanel, BoxLayout.X_AXIS));
                        ProdActorSelectionPanel.add(selectNameTitle);
                        ProdActorSelectionPanel.add(ProductionsActorsNames);

                        requestInfoPanel.add(ProdActorSelectionPanel);
                        requestInfoPanel.add(new JLabel());
                    }

                    requestInfoPanel.add(descriptionPanel);
                    requestInfoPanel.add(new JLabel());

                    createRequest = new JButton("Create Request");
                    createRequest.setBackground(Color.YELLOW);
                    createRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            RequestTypes type = (RequestTypes) requestTypesJComboBox.getSelectedItem();
                            String descriptionStr = description.getText();
                            if (!descriptionStr.isEmpty()) {

                                Request newRequest = new Request();
                                newRequest.setRequestType(type);
                                newRequest.setCreator(logedInUser.getUsername());
                                if (type == RequestTypes.DELETE_ACCOUNT || type == RequestTypes.OTHERS) {
                                    newRequest.setSolver("ADMIN");
                                } else {
                                    String titleName = (String) ProductionsActorsNames.getSelectedItem();
                                    newRequest.setTitleName(titleName);
                                    contributor = IMDB.getInstance().contributorUser(titleName);
                                    String solver = contributor.getUsername();
                                    newRequest.setSolver(solver);
                                }
                                newRequest.setDescription(descriptionStr);
                                ZonedDateTime date = ZonedDateTime.now();
                                DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                                String dateStr = date.format(DTFormatter);
                                LocalDateTime dateTime = LocalDateTime.parse(dateStr, DTFormatter);
                                newRequest.setDate(dateTime);

                                if (logedInUser instanceof Regular) {
                                    ((Regular) logedInUser).createRequest(newRequest, contributor);
                                }
                                if (logedInUser instanceof Contributor) {
                                    ((Contributor) logedInUser).createRequest(newRequest, contributor);
                                }
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(CreateRequest.this, "Complete all fields to create request!");
                            }
                        }
                    });
                    requestInfoPanel.add(createRequest);
                    revalidate();
                    repaint();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(CreateRequest.this, "You must select an option!");
                }
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(requestInfoPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
