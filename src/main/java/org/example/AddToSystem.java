package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddToSystem extends JFrame {
    private JComboBox<String> addChoice;
    private JLabel addText;
    private JLabel nameLabel;
    private JTextField name;
    private JTextField biography;
    private JLabel biographyLabel;
    private JButton addPerformances;
    private JLabel titleLabel;
    private JTextField title;
    private JButton addDirectors;
    private JButton addActors;
    private JButton addGenres;
    private JLabel plotLabel;
    private JTextField plot;
    private JLabel ratingLabel;
    private JTextField rating;
    private JLabel durationLabel;
    private JTextField duration;
    private JLabel numSeasonsLabel;
    private JTextField numSeasons;
    private JLabel yearLabel;
    private JTextField year;
    private JButton addSeason;

    public JPanel createPanel(JLabel label, JTextField text) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(text);
        return panel;
    }

    public AddToSystem(Staff loggedInUser, JFrame previous) {
        super("Add Production/Actor to System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 1000);

        addText = new JLabel("Select what you want to add: ");
        addText.setForeground(Color.WHITE);

        String[] choices = {"Actor", "Movie", "Series"};
        addChoice = new JComboBox<>(choices);

        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(Color.darkGray);
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.X_AXIS));
        choicePanel.add(addText);
        choicePanel.add(addChoice);

        JPanel addPanel = new JPanel();
        addPanel.setBackground(Color.darkGray);
        addPanel.setLayout(new GridLayout(25, 1));
        addPanel.add(choicePanel);
        addPanel.add(new JLabel());

        addChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPanel.removeAll();
                addPanel.add(choicePanel);
                addPanel.add(new JLabel());
                String type = (String) addChoice.getSelectedItem();

                if (type.equals("Actor")) {
                    name = new JTextField(50);
                    nameLabel = new JLabel("Enter actor name: ");
                    nameLabel.setForeground(Color.WHITE);

                    JPanel namePanel = createPanel(nameLabel, name);
                    addPanel.add(namePanel);
                    addPanel.add(new JLabel());

                    biography = new JTextField(50);
                    biographyLabel = new JLabel("Enter a biography: ");
                    biographyLabel.setForeground(Color.WHITE);

                    JPanel biographyPanel = createPanel(biographyLabel, biography);
                    addPanel.add(biographyPanel);
                    addPanel.add(new JLabel());

                    Actor a = new Actor();
                    addPerformances = new JButton("Add performances");
                    addPerformances.setBackground(Color.YELLOW);
                    addPerformances.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AddPerformances(a);
                        }
                    });
                    addPanel.add(addPerformances);

                    JButton addActor = new JButton("Add Actor");
                    addActor.setBackground(Color.YELLOW);
                    addActor.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nameStr = name.getText();
                            String bioStr = biography.getText();

                            if (!nameStr.isEmpty()) {
                                a.setName(nameStr);
                                a.setImageIcon(nameStr);
                                a.setBiography(bioStr);
                                loggedInUser.addActorSystem(a);
                                dispose();
                                previous.dispose();
                                new OptionsMenu(loggedInUser);
                            } else if(IMDB.getInstance().searchActorName(nameStr)) {
                                JOptionPane.showMessageDialog(AddToSystem.this, nameStr + " is already added!");
                            } else {
                                JOptionPane.showMessageDialog(AddToSystem.this, "Complete name field!");
                            }
                        }
                    });
                    addPanel.add(addActor);
                } else {
                    Production p;
                    if (type.equals("Movie")) {
                        p = new Movie();
                    } else {
                        p = new Series();
                    }

                    title = new JTextField(50);
                    titleLabel = new JLabel("Enter production title: ");
                    titleLabel.setForeground(Color.WHITE);

                    JPanel titlePanel = createPanel(titleLabel, title);
                    addPanel.add(titlePanel);
                    addPanel.add(new JLabel());

                    addDirectors = new JButton("Add director");
                    addDirectors.setBackground(Color.YELLOW);
                    addDirectors.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AddDirectors(p);
                        }
                    });
                    addPanel.add(addDirectors);
                    addPanel.add(new JLabel());

                    addActors = new JButton("Add Actors");
                    addActors.setBackground(Color.YELLOW);
                    addActors.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AddActors(p, type);
                        }
                    });
                    addPanel.add(addActors);
                    addPanel.add(new JLabel());

                    addGenres = new JButton("Add Genres");
                    addGenres.setBackground(Color.YELLOW);
                    addGenres.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AddGenres(p);
                        }
                    });
                    addPanel.add(addGenres);
                    addPanel.add(new JLabel());

                    plot = new JTextField(50);
                    plotLabel = new JLabel("Enter plot: ");
                    plotLabel.setForeground(Color.WHITE);

                    JPanel plotPanel = createPanel(plotLabel, plot);
                    addPanel.add(plotPanel);
                    addPanel.add(new JLabel());

                    rating = new JTextField(10);
                    ratingLabel = new JLabel("Enter rating: ");
                    ratingLabel.setForeground(Color.WHITE);

                    JPanel ratingPanel = createPanel(ratingLabel, rating);
                    addPanel.add(ratingPanel);
                    addPanel.add(new JLabel());

                    year = new JTextField(10);
                    yearLabel = new JLabel("Enter release year: ");
                    yearLabel.setForeground(Color.WHITE);

                    JPanel yearPanel = createPanel(yearLabel, year);
                    addPanel.add(yearPanel);
                    addPanel.add(new JLabel());

                    if (p instanceof Movie) {
                        duration = new JTextField(10);
                        durationLabel = new JLabel("Enter duration(in minutes): ");
                        durationLabel.setForeground(Color.WHITE);

                        JPanel durationPanel = createPanel(durationLabel, duration);
                        addPanel.add(durationPanel);
                        addPanel.add(new JLabel());

                        JButton addMovie = new JButton("Add Movie");
                        addMovie.setBackground(Color.YELLOW);
                        addMovie.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (p.getDirectors().isEmpty() || p.getGenres().isEmpty() || p.getActors().isEmpty()) {
                                    JOptionPane.showMessageDialog(AddToSystem.this, "You must add directors, actors and genres!");
                                } else {
                                    try {
                                        String titleStr = title.getText();
                                        String plotStr = plot.getText();
                                        int durationInt = Integer.parseInt(duration.getText());
                                        double ratingDouble = Double.parseDouble(rating.getText());
                                        int yearInt = Integer.parseInt(year.getText());

                                        if (titleStr.isEmpty() || plotStr.isEmpty()) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, "You must complete all fields!");
                                        } else if(IMDB.getInstance().searchProductionTitle(titleStr)) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, titleStr + " is already added!");
                                        } else if (ratingDouble <= 0 || ratingDouble > 10 || yearInt < 1 || yearInt > 2050 || durationInt <= 0) {
                                                JOptionPane.showMessageDialog(AddToSystem.this, "Rating or year out of bounds!");
                                        }  else {
                                            p.setTitle(titleStr);
                                            p.setImageIcon(titleStr);
                                            p.setPlot(plotStr);
                                            ((Movie) p).setDuration(durationInt + " minutes");
                                            p.setAverageRating(ratingDouble);
                                            ((Movie) p).setReleaseYear(yearInt);
                                            loggedInUser.addProductionSystem(p);
                                            dispose();
                                            previous.dispose();
                                            new OptionsMenu(loggedInUser);
                                        }
                                    } catch (NumberFormatException numberFormatException) {
                                        JOptionPane.showMessageDialog(AddToSystem.this, "Duration and rating must be integers and rating must be double!");
                                    }
                                }
                            }
                        });
                        addPanel.add(addMovie);
                    } else {
                        numSeasons = new JTextField(10);
                        numSeasonsLabel = new JLabel("Enter seasons number: ");
                        numSeasonsLabel.setForeground(Color.WHITE);

                        JPanel numSeasonsPanel = createPanel(numSeasonsLabel, numSeasons);
                        addPanel.add(numSeasonsPanel);
                        addPanel.add(new JLabel());

                        addSeason = new JButton("Add Season");
                        addSeason.setBackground(Color.YELLOW);
                        addSeason.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!numSeasons.getText().isEmpty()) {
                                    try {
                                        int nrSeasons = Integer.parseInt(numSeasons.getText());
                                        if (nrSeasons <= 0) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, "Seasons number out of range!");
                                        } else {
                                            new AddSeason((Series) p, nrSeasons, "Add to system");
                                        }
                                    } catch (NumberFormatException numberFormatException) {
                                        JOptionPane.showMessageDialog(AddToSystem.this, "Seasons number must be an integer!");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(AddToSystem.this, "Enter seasons number first!");
                                }
                            }
                        });
                        addPanel.add(addSeason);
                        addPanel.add(new JLabel());

                        JButton addSeries = new JButton("Add Series");
                        addSeries.setBackground(Color.YELLOW);
                        addSeries.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (p.getDirectors().isEmpty() || p.getGenres().isEmpty() || p.getActors().isEmpty() || ((Series) p).getSeasons().isEmpty()) {
                                    JOptionPane.showMessageDialog(AddToSystem.this, "You must add directors, actors, seasons and genres!");
                                } else {
                                    try {
                                        String titleStr = title.getText();
                                        String plotStr = plot.getText();
                                        int numSeasonsInt = Integer.parseInt(numSeasons.getText());
                                        double ratingDouble = Double.parseDouble(rating.getText());
                                        int yearInt = Integer.parseInt(year.getText());

                                        if (titleStr.isEmpty() || plotStr.isEmpty()) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, "You must complete all fields!");
                                        } else if (IMDB.getInstance().searchProductionTitle(titleStr)) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, titleStr + " is already added!");
                                        } else if (numSeasonsInt <= 0) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, "Seasons number out of range!");
                                        } else if (((Series) p).getAddedSeasons() < numSeasonsInt) {
                                            JOptionPane.showMessageDialog(AddToSystem.this, "You added only " + ((Series) p).getAddedSeasons() +
                                                    " season(s), there must be " + numSeasonsInt + " seasons!");
                                        } else if (ratingDouble <= 0 || ratingDouble > 10 || yearInt < 1 || yearInt > 2050) {
                                                JOptionPane.showMessageDialog(AddToSystem.this, "Rating or year out of bounds!");
                                        } else {
                                            p.setTitle(titleStr);
                                            p.setImageIcon(titleStr);
                                            p.setPlot(plotStr);
                                            ((Series) p).setNumSeasons(numSeasonsInt);
                                            p.setAverageRating(ratingDouble);
                                            ((Series) p).setReleaseYear(yearInt);
                                            loggedInUser.addProductionSystem(p);
                                            dispose();
                                            previous.dispose();
                                            new OptionsMenu(loggedInUser);
                                        }
                                    } catch (NumberFormatException numberFormatException) {
                                        JOptionPane.showMessageDialog(AddToSystem.this, "Seasons number and release year must be integers and rating must be double!");
                                    }
                                }
                            }
                        });
                        addPanel.add(addSeries);
                    }
                }
                revalidate();
                repaint();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(addPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
