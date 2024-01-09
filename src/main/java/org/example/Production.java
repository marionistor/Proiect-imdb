package org.example;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public abstract class Production implements Comparable<Object> {
    private String title;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String plot;
    private Double averageRating;
    private ImageIcon imageIcon;

    public Production() {
        directors = new ArrayList<>();
        actors = new ArrayList<>();
        genres = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public ImageIcon getImage (String title) {
        String filePath = "src/main/resources/images/" + title + ".jpg";
        Path path = Paths.get(filePath);
        boolean fileExists = Files.exists(path);
        ImageIcon originalImage;
        if (fileExists) {
            originalImage = new ImageIcon(filePath);
        } else {
            originalImage = new ImageIcon("src/main/resources/images/ImageUnavailable.jpg");
        }
        Image img = originalImage.getImage();

        Image resizedImage = img.getScaledInstance(160, 250, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }
    public void setImageIcon(String title) {
        this.imageIcon = getImage(title);
    }
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getPlot() {
        return plot;
    }
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    public Double getAverageRating() {
        return averageRating;
    }
    public void addActor(String actor) {
        actors.add(actor);
    }
    public void removeActor(String actor) {
        actors.remove(actor);
    }
    public boolean isActorAdded(String actor) {
        return actors.contains(actor);
    }
    public void addDirector(String director) {
        directors.add(director);
    }
    public void removeDirector(String director) {
        directors.remove(director);
    }
    public boolean isDirectorAdded(String director) {
        return directors.contains(director);
    }
    public void addGenre(Genre genre) {
        genres.add(genre);
    }
    public void removeGenre(Genre genre) {
        genres.remove(genre);
    }
    public boolean isGenreAdded(Genre genre) {
        return genres.contains(genre);
    }
    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void modifyDirectorName(String oldName, String newName) {
        int index = 0;
        for (String director : directors) {
            if (director.equals(oldName)) {
                break;
            }
            index++;
        }
        directors.set(index, newName);
    }
    public void modifyActorName(String oldName, String newName) {
        int index = 0;
        for (String actor : actors) {
            if (actor.equals(oldName)) {
                break;
            }
            index++;
        }
        actors.set(index, newName);
    }
    public void updateAverageRating() {
        if (ratings.isEmpty()) {
            this.averageRating = 1.;
        } else {
            double gradesSum = 0.;

            for (Rating r : ratings) {
                gradesSum += r.grade;
            }

            this.averageRating = gradesSum / ratings.size();
        }
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<String> getActors() {
        return actors;
    }
    public List<String> getDirectors() {
        return directors;
    }
    public boolean isRated(String username) {
        for (Rating rating : ratings) {
            if (rating.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public Rating getRating(String username) {
        for (Rating rating : ratings) {
            if (rating.getUsername().equals(username)) {
                return rating;
            }
        }
        return null;
    }
    public void removeRating(Rating rating) {
        ratings.remove(rating);
    }
    public abstract void displayInfo();
    public abstract String toString();
}
