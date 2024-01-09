package org.example;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Actor implements Comparable {
    private String name;
    private Map<String, String> performances;
    private String biography;
    private ImageIcon imageIcon;
    public ImageIcon getImage (String name) {
        String filePath = "src/main/resources/images/" + name + ".jpg";
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
    public void setImageIcon(String name) {
        this.imageIcon = getImage(name);
    }
    public ImageIcon getImageIcon() {
        return imageIcon;
    }


    public Actor() {
        performances = new HashMap<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public Map<String, String> getPerformances() {
        return performances;
    }

    public void addPerformance(String title, String type) {
        performances.put(title, type);
    }

    public boolean isPerformanceAdded(String title, String type) {
        if (performances.containsKey(title)) {
            return performances.get(title).equals(type);
        }
        return false;
    }
    public void modifyPerformance(String oldTitle, String newTitle, String oldType, String newType) {
        for (Map.Entry<String, String> entry : performances.entrySet()) {
            if (entry.getKey().equals(oldTitle) && entry.getValue().equals(oldType)) {
                performances.remove(oldTitle, oldType);
                performances.put(newTitle, newType);
            }
        }
    }
    public void removePerformance(String title, String type) {
        performances.remove(title, type);
    }
    public String toString() {
        StringBuilder actorInfo = new StringBuilder();
        actorInfo.append("Name: ").append(name).append("\nPerformances:\n");

        for(String title : performances.keySet()) {
            actorInfo.append("title: ").append(title).append(", type: ").append(performances.get(title)).append("\n");
        }

        if(biography != null)
            actorInfo.append("Biography: ").append(biography).append("\n");

        return actorInfo.toString();
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Actor) {
            Actor a = (Actor) o;
            return this.name.compareTo(a.getName());
        }

        Production prod = (Production) o;
        return this.name.compareTo(prod.getTitle());
    }
}
