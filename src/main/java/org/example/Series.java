package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Series extends Production {

    private int releaseYear;
    private int numSeasons;
    private int addedSeasons;
    private int seasonIndex;
    private Map<String, List<Episode>> seasons;

    public Series() {
        super();
        seasons = new TreeMap<>();
        addedSeasons = 0;
        seasonIndex = 1;
    }
    public void setNumSeasons(int numSeasons) {
        this.numSeasons = numSeasons;
    }
    public int getNumSeasons() {
        return numSeasons;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public int getReleaseYear() {
        return releaseYear;
    }

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }

    public void setSeasons(HashMap<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    public void addSeason(String seasonName, List<Episode> episodesList) {
        seasons.put(seasonName, episodesList);
    }

    public void incrementAddedSeasons() {
        addedSeasons++;
    }
    public void incrementSeasons() {
        numSeasons++;
    }
    public void incrementSeasonIndex() {
        seasonIndex++;
    }

    public int getSeasonIndex() {
        return seasonIndex;
    }

    public int getAddedSeasons() {
        return addedSeasons;
    }
    public void modifyEpisode(String seasonName, String oldEpName, String newEpName, int oldDuration, int newDuration) {
        List<Episode> episodeList = seasons.get(seasonName);
        if (episodeList != null) {
            for (Episode episode : episodeList) {
                if (episode.getEpisodeName().equals(oldEpName) && episode.getDuration().equals(oldDuration + " minutes")) {
                    episode.setEpisodeName(newEpName);
                    episode.setDuration(newDuration + " minutes");
                    break;
                }
            }
        }
    }
    public void deleteEpisode(String seasonName, String EpName, int duration) {
        if (seasons.containsKey(seasonName)) {
            List<Episode> listToRemoveFrom = seasons.get(seasonName);
            listToRemoveFrom.removeIf(episode -> episode.getEpisodeName().equals(EpName) && episode.getDuration().equals(duration + " minutes"));
        }
    }
    @Override
    public void displayInfo() {
        System.out.println(this.getTitle());
        System.out.println(releaseYear);
        System.out.println(numSeasons);
        System.out.println(seasons);
        System.out.println(this.getPlot());
        System.out.println(this.getAverageRating());
        System.out.println(this.getActors());
        System.out.println(this.getDirectors());
        System.out.println(this.getGenres());
        System.out.println(this.getRatings());
    }

    public String toString() {
        return "title: " + this.getTitle() + "\nnumSeasons: " + numSeasons + "\nreleaseYear: " + releaseYear + "\nseasons: " + seasons + "\nplot: " + this.getPlot() + "\navgRating: " + this.getAverageRating() + "\nactors: " + this.getActors() + "\nDirectors: " + this.getDirectors() + "\nGenres: " + this.getGenres() + "\nRatings: " +this.getRatings() + "\n";
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
