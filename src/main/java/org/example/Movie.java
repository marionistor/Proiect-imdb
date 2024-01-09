package org.example;

import org.jetbrains.annotations.NotNull;

public class Movie extends Production {
    private String duration;
    private int releaseYear;

    public Movie() {
        super();
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getDuration() {
        return duration;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    @Override
    public void displayInfo() {
        System.out.println(this.getTitle());
        System.out.println(duration);
        System.out.println(releaseYear);
        System.out.println(this.getPlot());
        System.out.println(this.getAverageRating());
        System.out.println(this.getActors());
        System.out.println(this.getDirectors());
        System.out.println(this.getGenres());
        System.out.println(this.getRatings());
    }

    public String toString() {
        return "title: " + this.getTitle() + "\nduration: " + duration + "\nreleaseYear: " + releaseYear + ", " + "\nplot: " + this.getPlot() + "\navgRating: "
                + this.getAverageRating() + "\nactors: " + this.getActors() + "\nDirectors: " + this.getDirectors() + "\nGenres: " + this.getGenres() + "\nRatings: " +this.getRatings() + "\n";
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Production) {
            Production p = (Production) o;
            return this.getTitle().compareTo(p.getTitle());
        }

        Actor a = (Actor) o;
        return this.getTitle().compareTo(a.getName());
    }
}
