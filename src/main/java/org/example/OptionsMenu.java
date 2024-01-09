package org.example;

import org.intellij.lang.annotations.Flow;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class OptionsMenu extends JFrame {

    private JLabel welcomeMessage;
    private JLabel suggestionsMessage;
    private JScrollPane scroll;
    private JMenu menu;
    private JMenuItem viewProfile;
    private JMenuItem productionsDetails;
    private JMenuItem actorsDetails;
    private JMenuItem notifications;
    private JMenuItem viewFavorites;
    private JMenuItem addUser;
    private JMenuItem updateUserInfo;
    private JMenuItem deleteUser;
    private JMenuItem addProductionActor;
    private JMenuItem deleteProductionActor;
    private JMenuItem updateActorProductionInfo;
    private JMenuItem createRequest;
    private JMenuItem seeCreatedRequests;
    private JMenuItem solveRequest;
    private JMenuItem logout;
    private JTextField searchBar;
    private JButton search;

    public OptionsMenu(User loggedInUser) {
        super("IMDB Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        menu = new JMenu("Menu");

        welcomeMessage = new JLabel("Welcome back user " + loggedInUser.getUsername() + "!");
        welcomeMessage.setForeground(Color.WHITE);

        suggestionsMessage = new JLabel("Here are some suggestions for you:");
        suggestionsMessage.setForeground(Color.YELLOW);

        searchBar = new JTextField(50);
        search = new JButton("Search Production/Actor");
        search.setBackground(Color.YELLOW);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleName = searchBar.getText();

                if (IMDB.getInstance().searchActorName(titleName)) {
                    new ActorInfo(IMDB.getInstance().getActor(titleName), loggedInUser);
                } else if (IMDB.getInstance().searchProductionTitle(titleName)) {
                    new ProductionInfo(IMDB.getInstance().getProduction(titleName), loggedInUser);
                } else {
                    JOptionPane.showMessageDialog(OptionsMenu.this, "No Production/Actor found!");
                }
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.darkGray);
        searchPanel.add(searchBar);
        searchPanel.add(search);

        // panel for welcome message
        JPanel welcomeMessagePanel = new JPanel();
        welcomeMessagePanel.setBackground(Color.darkGray);
        welcomeMessagePanel.setLayout(new GridLayout(3, 1));
        welcomeMessagePanel.add(welcomeMessage);
        welcomeMessagePanel.add(searchPanel);
        welcomeMessagePanel.add(suggestionsMessage);

        // panel that contains welcome message and suggestions(top 10 productions)
        JPanel pagePanel = new JPanel();
        pagePanel.setLayout(new GridLayout(11, 1));
        pagePanel.setBackground(Color.darkGray);
        pagePanel.add(welcomeMessagePanel);

        // get top 10 productions
        for (Production production : IMDB.getInstance().getTopProductions()) {
            // put each production information in a panel
            JPanel productionPanel = new JPanel();
            productionPanel.setLayout(new GridLayout(8, 1));
            productionPanel.setBackground(Color.darkGray);
            JLabel title = new JLabel(production.getTitle());
            title.setForeground(Color.YELLOW);
            productionPanel.add(title);
            JLabel releaseYear = new JLabel();
            // put duration and movie type as infos if it is a movie
            if (production instanceof Movie) {
                JLabel type = new JLabel("Movie");
                type.setForeground(Color.WHITE);
                releaseYear.setText("" + ((Movie) production).getReleaseYear());
                releaseYear.setForeground(Color.WHITE);
                JLabel duration = new JLabel(((Movie) production).getDuration());
                duration.setForeground(Color.WHITE);
                productionPanel.add(releaseYear);
                productionPanel.add(type);
                productionPanel.add(duration);
            }
            if (production instanceof Series) {
                // put number of seasons and series type as infos if it is a series
                JLabel type = new JLabel("Series");
                type.setForeground(Color.WHITE);
                releaseYear.setText("" + ((Series) production).getReleaseYear());
                releaseYear.setForeground(Color.WHITE);
                JLabel seasonsNum = new JLabel(((Series) production).getNumSeasons() + " seasons");
                seasonsNum.setForeground(Color.WHITE);
                productionPanel.add(releaseYear);
                productionPanel.add(type);
                productionPanel.add(seasonsNum);
            }
            JLabel averageRating = new JLabel(production.getAverageRating() + " \\ 10.0");
            averageRating.setForeground(Color.WHITE);
            productionPanel.add(averageRating);
            JLabel genres = new JLabel("" + production.getGenres());
            genres.setForeground(Color.WHITE);
            productionPanel.add(genres);
            JButton viewMore = new JButton("View More");
            viewMore.setBackground(Color.YELLOW);
            viewMore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ProductionInfo(production, loggedInUser);
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.darkGray);
            buttonPanel.add(viewMore);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            productionPanel.add(buttonPanel);

            JLabel imageLabel = new JLabel(production.getImage(production.getTitle()));
            JPanel imgPanel = new JPanel();
            imgPanel.setBackground(Color.darkGray);
            imgPanel.add(imageLabel);

            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(Color.darkGray);
            imagePanel.setLayout(new BorderLayout());
            imagePanel.add(imgPanel, BorderLayout.WEST);
            imagePanel.add(productionPanel, BorderLayout.CENTER);

            // add to page panel
            pagePanel.add(imagePanel);
        }

        scroll = new JScrollPane(pagePanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        viewProfile = new JMenuItem("Profile Details");
        viewProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new viewProfile(loggedInUser);
            }
        });
        viewProfile.setBackground(Color.YELLOW);

        productionsDetails = new JMenuItem("View productions details");
        productionsDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductionsDetails((ArrayList<Production>) IMDB.getInstance().getProductionsList(), loggedInUser);
            }
        });
        productionsDetails.setBackground(Color.YELLOW);

        actorsDetails = new JMenuItem("View actors details");
        actorsDetails.setBackground(Color.YELLOW);
        actorsDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Actor> actorsAlphabeticOrder = IMDB.getInstance().sortActorsAlphabetically();
                new ActorsDetails((ArrayList<Actor>) actorsAlphabeticOrder, loggedInUser);
            }
        });

        notifications = new JMenuItem("View notifications");
        notifications.setBackground(Color.YELLOW);
        notifications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewNotifications(loggedInUser);
            }
        });

        viewFavorites = new JMenuItem("View favorites");
        viewFavorites.setBackground(Color.YELLOW);
        viewFavorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FavoritesDetails(loggedInUser);
            }
        });

        logout = new JMenuItem("Logout");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignInScreen();
            }
        });
        logout.setBackground(Color.YELLOW);

        // button options for regular user
        if (loggedInUser instanceof Regular) {
            createRequest = new JMenuItem("Create request");
            createRequest.setBackground(Color.YELLOW);
            createRequest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new CreateRequest(loggedInUser);
                }
            });

            seeCreatedRequests = new JMenuItem("See created requests");
            seeCreatedRequests.setBackground(Color.YELLOW);
            seeCreatedRequests.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SeeRequests(loggedInUser);
                }
            });

            menu.add(viewProfile);
            menu.add(productionsDetails);
            menu.add(actorsDetails);
            menu.add(notifications);
            menu.add(viewFavorites);
            menu.add(createRequest);
            menu.add(seeCreatedRequests);
            menu.add(logout);
        // button options for admin/contributor user
        } else {
            addProductionActor = new JMenuItem("Add Movie/Series/Actor from system");
            addProductionActor.setBackground(Color.YELLOW);
            addProductionActor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddToSystem((Staff) loggedInUser, OptionsMenu.this);
                }
            });

            deleteProductionActor = new JMenuItem("Delete Movie/Series/Actor from system");
            deleteProductionActor.setBackground(Color.YELLOW);
            deleteProductionActor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new DeleteFromSystem((Staff) loggedInUser, OptionsMenu.this);
                }
            });

            solveRequest = new JMenuItem("Solve request");
            solveRequest.setBackground(Color.YELLOW);
            solveRequest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewReceivedRequests((Staff) loggedInUser);
                }
            });

            updateActorProductionInfo = new JMenuItem("Update Movie/SeriesActor details");
            updateActorProductionInfo.setBackground(Color.YELLOW);
            updateActorProductionInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UpdateInfo((Staff) loggedInUser, OptionsMenu.this);
                }
            });

            menu.setLayout(new GridLayout(11, 1));
            menu.add(viewProfile);
            menu.add(productionsDetails);
            menu.add(actorsDetails);
            menu.add(notifications);
            menu.add(viewFavorites);

            if (loggedInUser instanceof Contributor) {
                createRequest = new JMenuItem("Create request");
                createRequest.setBackground(Color.YELLOW);
                createRequest.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new CreateRequest(loggedInUser);
                    }
                });

                seeCreatedRequests = new JMenuItem("See created requests");
                seeCreatedRequests.setBackground(Color.YELLOW);
                seeCreatedRequests.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new SeeRequests(loggedInUser);
                    }
                });

                menu.add(createRequest);
                menu.add(seeCreatedRequests);
            } else {
                addUser = new JMenuItem("Add User");
                addUser.setBackground(Color.YELLOW);
                addUser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AddUser((Admin) loggedInUser);
                    }
                });
                menu.add(addUser);

                updateUserInfo = new JMenuItem("Update User Info");
                updateUserInfo.setBackground(Color.YELLOW);
                updateUserInfo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new UpdateUser((Admin) loggedInUser);
                    }
                });
                menu.add(updateUserInfo);

                deleteUser = new JMenuItem("Delete User");
                deleteUser.setBackground(Color.YELLOW);
                deleteUser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new DeleteUser(loggedInUser, OptionsMenu.this);
                    }
                });
                menu.add(deleteUser);
            }
            menu.add(addProductionActor);
            menu.add(deleteProductionActor);
            menu.add(solveRequest);
            menu.add(updateActorProductionInfo);
            menu.add(logout);
        }

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.YELLOW);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        getContentPane().setBackground(Color.darkGray);
        getContentPane().add(scroll);
        setResizable(false);
        setVisible(true);
    }
}
