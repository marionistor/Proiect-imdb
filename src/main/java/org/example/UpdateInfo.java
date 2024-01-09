package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateInfo extends JFrame {
    private JLabel choicesLabel;
    private JComboBox<String> choicesComboBox;
    private JLabel nameLabel;
    private JTextField name;
    private JTextField biography;
    private JLabel biographyLabel;
    private JButton addPerformances;
    private JLabel titleLabel;
    private JTextField title;
    private JButton addDirectors;
    private JButton editDirectors;
    private JButton addActors;
    private JButton editActors;
    private JButton addGenres;
    private JButton removeGenres;
    private JLabel plotLabel;
    private JTextField plot;
    private JLabel ratingLabel;
    private JTextField rating;
    private JLabel durationLabel;
    private JTextField duration;
    private JLabel yearLabel;
    private JTextField year;
    private JButton addSeason;
    private JButton editSeasons;
    private JButton editPerformances;

    public JPanel createPanel(JLabel label, JTextField text) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(text);
        return panel;
    }

    public UpdateInfo(Staff loggedInUser, JFrame previous) {
        super("Update Production/Actor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (loggedInUser.getContributions().isEmpty() && Admin.commonContributionsList.isEmpty()) {
            JPanel updatePanel = new JPanel();
            updatePanel.setBackground(Color.darkGray);
            setSize(600, 300);
            JLabel NoFavMessage = new JLabel("You don't have contributions!");
            NoFavMessage.setForeground(Color.YELLOW);
            updatePanel.add(NoFavMessage);
            getContentPane().add(updatePanel);
        } else {
            setSize(1000, 1000);

            choicesLabel = new JLabel("Select contribution: ");
            choicesLabel.setForeground(Color.WHITE);

            String[] contributionsStr;
            if (loggedInUser instanceof Admin) {
                contributionsStr = new String[loggedInUser.getContributions().size() + Admin.commonContributionsList.size()];
            } else {
                contributionsStr = new String[loggedInUser.getContributions().size()];
            }
            int index = 0;
            for (Object contribution : loggedInUser.getContributions()) {
                if (contribution instanceof Actor) {
                    contributionsStr[index++] = ((Actor) contribution).getName();
                } else if (contribution instanceof Production) {
                    contributionsStr[index++] = ((Production) contribution).getTitle();
                }
            }
            if (loggedInUser instanceof Admin) {
                for (Object contribution : Admin.commonContributionsList) {
                    if (contribution instanceof Actor) {
                        contributionsStr[index++] = ((Actor) contribution).getName();
                    } else if (contribution instanceof Production) {
                        contributionsStr[index++] = ((Production) contribution).getTitle();
                    }
                }
            }

            choicesComboBox = new JComboBox<>(contributionsStr);

            JPanel choicePanel = new JPanel();
            choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.X_AXIS));
            choicePanel.setBackground(Color.darkGray);
            choicePanel.add(choicesLabel);
            choicePanel.add(choicesComboBox);

            JPanel updatePanel = new JPanel();
            updatePanel.setBackground(Color.darkGray);
            updatePanel.setLayout(new GridLayout(30, 1));
            updatePanel.add(choicePanel);
            updatePanel.add(new JLabel());

            choicesComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePanel.removeAll();
                    updatePanel.add(choicePanel);
                    updatePanel.add(new JLabel());
                    String titleName = (String) choicesComboBox.getSelectedItem();

                    if (IMDB.getInstance().searchActorName(titleName)) {
                        name = new JTextField(50);
                        name.setText(titleName);
                        nameLabel = new JLabel("Actor name: ");
                        nameLabel.setForeground(Color.WHITE);

                        JPanel namePanel = createPanel(nameLabel, name);
                        updatePanel.add(namePanel);
                        updatePanel.add(new JLabel());

                        biography = new JTextField(50);
                        biography.setText(IMDB.getInstance().getActor(titleName).getBiography());
                        biographyLabel = new JLabel("Biography: ");
                        biographyLabel.setForeground(Color.WHITE);

                        JPanel biographyPanel = createPanel(biographyLabel, biography);
                        updatePanel.add(biographyPanel);
                        updatePanel.add(new JLabel());

                        Actor a = IMDB.getInstance().getActor(titleName);
                        addPerformances = new JButton("Add performance");
                        addPerformances.setBackground(Color.YELLOW);
                        addPerformances.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new AddPerformances(a);
                            }
                        });
                        updatePanel.add(addPerformances);

                        editPerformances = new JButton("Edit performances");
                        editPerformances.setBackground(Color.YELLOW);
                        editPerformances.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new EditPerformances(a);
                            }
                        });
                        updatePanel.add(editPerformances);


                        JButton saveActorInfo = new JButton("Save Actor info");
                        saveActorInfo.setBackground(Color.YELLOW);
                        saveActorInfo.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nameStr = name.getText();
                                String bioStr = biography.getText();

                                if (!nameStr.isEmpty()) {
                                    loggedInUser.updateActor(a, nameStr, bioStr);
                                    dispose();
                                    previous.dispose();
                                    new OptionsMenu(loggedInUser);
                                } else if (IMDB.getInstance().searchActorName(nameStr)) {
                                    JOptionPane.showMessageDialog(UpdateInfo.this, nameStr + " is already added!");
                                } else {
                                    JOptionPane.showMessageDialog(UpdateInfo.this, "Name field can't be empty!");
                                }
                            }
                        });
                        updatePanel.add(saveActorInfo);
                    } else {
                        Production p = IMDB.getInstance().getProduction(titleName);

                        title = new JTextField(50);
                        title.setText(p.getTitle());
                        titleLabel = new JLabel("Production title: ");
                        titleLabel.setForeground(Color.WHITE);

                        JPanel titlePanel = createPanel(titleLabel, title);
                        updatePanel.add(titlePanel);
                        updatePanel.add(new JLabel());

                        addDirectors = new JButton("Add directors");
                        addDirectors.setBackground(Color.YELLOW);
                        addDirectors.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new AddDirectors(p);
                            }
                        });
                        updatePanel.add(addDirectors);
                        updatePanel.add(new JLabel());

                        editDirectors = new JButton("Edit Directors info");
                        editDirectors.setBackground(Color.YELLOW);
                        editDirectors.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new EditDirectors(p);
                            }
                        });
                        updatePanel.add(editDirectors);
                        updatePanel.add(new JLabel());


                        addActors = new JButton("Add Actors");
                        addActors.setBackground(Color.YELLOW);
                        addActors.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (p instanceof Movie) {
                                    new AddActors(p, "Movie");
                                } else {
                                    new AddActors(p, "Series");
                                }

                            }
                        });
                        updatePanel.add(addActors);
                        updatePanel.add(new JLabel());

                        editActors = new JButton("Edit Actors info");
                        editActors.setBackground(Color.YELLOW);
                        editActors.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new EditActors(p);
                            }
                        });
                        updatePanel.add(editActors);
                        updatePanel.add(new JLabel());

                        addGenres = new JButton("Add Genres");
                        addGenres.setBackground(Color.YELLOW);
                        addGenres.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new AddGenres(p);
                            }
                        });
                        updatePanel.add(addGenres);
                        updatePanel.add(new JLabel());

                        removeGenres = new JButton("Remove Genres");
                        removeGenres.setBackground(Color.YELLOW);
                        removeGenres.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new RemoveGenres(p);
                            }
                        });
                        updatePanel.add(removeGenres);
                        updatePanel.add(new JLabel());


                        plot = new JTextField(50);
                        plot.setText(p.getPlot());
                        plotLabel = new JLabel("Plot: ");
                        plotLabel.setForeground(Color.WHITE);

                        JPanel plotPanel = createPanel(plotLabel, plot);
                        updatePanel.add(plotPanel);
                        updatePanel.add(new JLabel());

                        rating = new JTextField(10);
                        rating.setText(p.getAverageRating().toString());
                        ratingLabel = new JLabel("Rating: ");
                        ratingLabel.setForeground(Color.WHITE);

                        JPanel ratingPanel = createPanel(ratingLabel, rating);
                        updatePanel.add(ratingPanel);
                        updatePanel.add(new JLabel());

                        year = new JTextField(10);
                        if (p instanceof Movie) {
                            if (((Movie) p).getReleaseYear() != 0) {
                                year.setText("" + ((Movie) p).getReleaseYear());
                            }
                        }
                        if (p instanceof Series) {
                            if (((Series) p).getReleaseYear() != 0) {
                                year.setText("" + ((Series) p).getReleaseYear());
                            }
                        }
                        yearLabel = new JLabel("Release year: ");
                        yearLabel.setForeground(Color.WHITE);

                        JPanel yearPanel = createPanel(yearLabel, year);
                        updatePanel.add(yearPanel);
                        updatePanel.add(new JLabel());

                        if (p instanceof Movie) {
                            duration = new JTextField(10);
                            String[] min = ((Movie) p).getDuration().split(" ");
                            duration.setText(min[0]);
                            durationLabel = new JLabel("Duration(in minutes): ");
                            durationLabel.setForeground(Color.WHITE);

                            JPanel durationPanel = createPanel(durationLabel, duration);
                            updatePanel.add(durationPanel);
                            updatePanel.add(new JLabel());

                            JButton editMovie = new JButton("Save Movie info");
                            editMovie.setBackground(Color.YELLOW);
                            editMovie.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (p.getDirectors().isEmpty() || p.getGenres().isEmpty() || p.getActors().isEmpty()) {
                                        JOptionPane.showMessageDialog(UpdateInfo.this, "You must add directors, actors and genres!");
                                    } else {
                                        try {
                                            String titleStr = title.getText();
                                            String plotStr = plot.getText();
                                            int durationInt = Integer.parseInt(duration.getText());
                                            double ratingDouble = Double.parseDouble(rating.getText());
                                            int yearInt = Integer.parseInt(year.getText());

                                            if (titleStr.isEmpty() || plotStr.isEmpty()) {
                                                JOptionPane.showMessageDialog(UpdateInfo.this, "You must complete all fields!");
                                            } else if (ratingDouble <= 0 || ratingDouble > 10 || yearInt < 1 || yearInt > 2050 || durationInt <= 0) {
                                                JOptionPane.showMessageDialog(UpdateInfo.this, "Rating, duration or year out of bounds!");
                                            } else {
                                                loggedInUser.updateProduction(p, titleStr, plotStr, durationInt, ratingDouble, yearInt);
                                                dispose();
                                                previous.dispose();
                                                new OptionsMenu(loggedInUser);
                                            }
                                        } catch (NumberFormatException numberFormatException) {
                                            JOptionPane.showMessageDialog(UpdateInfo.this, "Duration and rating must be integers and rating must be double!");
                                        }
                                    }
                                }
                            });
                            updatePanel.add(editMovie);
                        } else {
                            addSeason = new JButton("Add Season");
                            addSeason.setBackground(Color.YELLOW);
                            addSeason.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    new AddSeason((Series) p, ((Series) p).getNumSeasons(), "Update info");

                                }
                            });
                            updatePanel.add(addSeason);
                            updatePanel.add(new JLabel());

                            editSeasons = new JButton("Edit seasons info");
                            editSeasons.setBackground(Color.YELLOW);
                            editSeasons.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (p instanceof Series) {
                                        new EditSeasons((Series) p);
                                    }

                                }
                            });
                            updatePanel.add(editSeasons);
                            updatePanel.add(new JLabel());


                            JButton addSeries = new JButton("Save Series info");
                            addSeries.setBackground(Color.YELLOW);
                            addSeries.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (p.getDirectors().isEmpty() || p.getGenres().isEmpty() || p.getActors().isEmpty() || ((Series) p).getSeasons().isEmpty()) {
                                        JOptionPane.showMessageDialog(UpdateInfo.this, "You must add directors, actors, seasons and genres!");
                                    } else {
                                        try {
                                            String titleStr = title.getText();
                                            String plotStr = plot.getText();
                                            double ratingDouble = Double.parseDouble(rating.getText());
                                            int yearInt = Integer.parseInt(year.getText());

                                            if (titleStr.isEmpty() || plotStr.isEmpty()) {
                                                JOptionPane.showMessageDialog(UpdateInfo.this, "You must complete all fields!");
                                            } else if (ratingDouble <= 0 || ratingDouble > 10 || yearInt < 1 || yearInt > 2050) {
                                                JOptionPane.showMessageDialog(UpdateInfo.this, "Rating or year out of bounds!");
                                            } else {
                                                loggedInUser.updateProduction(p, titleStr, plotStr, 0, ratingDouble, yearInt);
                                                dispose();
                                                previous.dispose();
                                                new OptionsMenu(loggedInUser);
                                            }
                                        } catch (NumberFormatException numberFormatException) {
                                            JOptionPane.showMessageDialog(UpdateInfo.this, "Seasons number and rating must be integers and rating must be double!");
                                        }
                                    }
                                }
                            });
                            updatePanel.add(addSeries);
                        }
                    }
                    revalidate();
                    repaint();
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(Color.darkGray);
            mainPanel.add(updatePanel);

            getContentPane().add(mainPanel);
        }
        setVisible(true);
    }
}
