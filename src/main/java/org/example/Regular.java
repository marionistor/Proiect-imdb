package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Regular extends User implements RequestsManager {
    private List<Request> createdRequests;
    public Regular(String username, String name, String country, int age, char gender,
                   LocalDateTime birthday, Credentials userCredentials) {
        super(username, name, country, age, gender, birthday, userCredentials);
        createdRequests = new ArrayList<>();
    }

    public List<Request> getCreatedRequests() {
        return createdRequests;
    }

    public void addCreatedRequest(Request r) {
        createdRequests.add(r);
    }
    public void removeCreatedRequest(Request r) {
        createdRequests.remove(r);
    }
    public String toString() {
        return "Username: " + this.getUsername() + "\nInfo: " + this.getUserInfo() + "\nType: " + this.getUserType() + "\nExperience: "
                + this.getUserExperience() + "\nuserNotification: " + this.getUserNotifications() + "\nFavorites: " + this.getFavoritesSet() +"\n";
    }
    @Override
    public void createRequest(Request r, Staff contributor) {
        if (r.getRequestType() == RequestTypes.OTHERS || r.getRequestType() == RequestTypes.DELETE_ACCOUNT) {
            Admin.RequestHolder.TeamRequestsList.add(r);
        } else {
            contributor.addIndividualRequest(r);
        }
        addCreatedRequest(r);
    }

    @Override
    public void removeRequest(Request r, Staff contributor) {
        if (r.getRequestType() == RequestTypes.OTHERS || r.getRequestType() == RequestTypes.DELETE_ACCOUNT) {
            Admin.RequestHolder.TeamRequestsList.remove(r);
        } else {
            contributor.removeIndividualRequest(r);
        }
        removeCreatedRequest(r);
    }
}
