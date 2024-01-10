package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class Staff extends User implements StaffInterface {
    private List<Request> IndividualRequestsList;
    private SortedSet<Object> Contributions;

    public Staff(String username, String name, String country, int age, char gender, LocalDateTime birthday, Credentials userCredentials) {
        super(username, name, country, age, gender, birthday, userCredentials);
        IndividualRequestsList = new ArrayList<>();
        Contributions = new TreeSet<>();
    }

    public void addContribution(Object contribution) {
        Contributions.add(contribution);
    }

    public SortedSet<Object> getContributions() {
        return Contributions;
    }

    public void addIndividualRequest(Request r) {
        IndividualRequestsList.add(r);
    }

    public List<Request> getIndividualRequestsList() {
        return IndividualRequestsList;
    }
    public void removeIndividualRequest(Request r) {
        IndividualRequestsList.remove(r);
    }

    public String toString() {
        return "Username: " + this.getUsername() + "\nInfo: " + this.getUserInfo() + "\nType: " + this.getUserType() + "\nExperience: "
                + this.getUserExperience() + "\nuserNotification: " + this.getUserNotifications() + "\nFavorites: " + this.getFavoritesSet() + "\nContributions: " + Contributions +"\n";
    }

    // add/delete prod/actor
    @Override
    public void addProductionSystem(Production p) {
        IMDB.getInstance().addProduction(p);
        p.addObserver(this, Event.ADDED_PRODUCTION_REVIEW);
        Contributions.add(p);
    }

    @Override
    public void addActorSystem(Actor a) {
        IMDB.getInstance().addActor(a);
        Contributions.add(a);
    }

    @Override
    public void removeProductionSystem(String name) {
        Production production = IMDB.getInstance().getProduction(name);
        Contributions.remove(production);
        if (this instanceof Admin) {
                Admin.commonContributionsList.remove(production);
        }
        production.removeObserver(this, Event.ADDED_PRODUCTION_REVIEW);

        IMDB.getInstance().removeProduction(production);
        IMDB.getInstance().removeFromUsersFavorites(production);
    }

    @Override
    public void removeActorSystem(String name) {
        Actor actor = IMDB.getInstance().getActor(name);
        Contributions.remove(actor);
        if (this instanceof Admin) {
            Admin.commonContributionsList.remove(actor);
        }
        IMDB.getInstance().removeActor(actor);
        IMDB.getInstance().removeFromUsersFavorites(actor);
    }

    // update info for prod/actor added
    @Override
    public void updateProduction(Production p, String title, String plot, int duration, double avgRating, int releaseYear) {
        if (!p.getTitle().equals(title)) {
            p.setTitle(title);
        }
        if (!p.getPlot().equals(plot)) {
            p.setPlot(plot);
        }
        if (p.getAverageRating() != avgRating) {
            p.setAverageRating(avgRating);
        }
        if (p instanceof Movie) {
            if (!((Movie) p).getDuration().equals(duration + " minutes")) {
                ((Movie) p).setDuration(duration + " minutes");
            }
            if (((Movie) p).getReleaseYear() != releaseYear) {
                ((Movie) p).setReleaseYear(releaseYear);
            }
        }
        if (p instanceof Series) {
            if (((Series) p).getReleaseYear() != releaseYear) {
                ((Series) p).setReleaseYear(releaseYear);
            }
        }
    }

    @Override
    public void updateActor(Actor a, String name, String biography) {
        if (!a.getName().equals(name)) {
            a.setName(name);
        }
        if (!a.getBiography().equals(biography)) {
            a.setBiography(biography);
        }
    }

    // solve requests
}
