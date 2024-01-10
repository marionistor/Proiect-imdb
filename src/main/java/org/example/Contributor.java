package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Contributor extends Staff implements RequestsManager {
    private List<Request> createdRequests;
    public Contributor(String username, String name, String country,
                       int age, char gender, LocalDateTime birthday, Credentials userCredentials) {
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
        createdRequests.add(r);
    }
    @Override
    public void createRequest(Request r, Staff contributor) {
        User<?> creatorUser = IMDB.getInstance().getUser(r.getCreator());
        if (r.getRequestType() == RequestTypes.OTHERS || r.getRequestType() == RequestTypes.DELETE_ACCOUNT) {
            new Admin.RequestHolder().addTeamRequest(r);
            for (Admin admin : IMDB.getInstance().getAdmins()) {
                creatorUser.notifyUser(admin, Event.ADMIN_RECEIVED_REQUESTS, r.getDescription());
            }
        } else {
            contributor.addIndividualRequest(r);
            User<?> solverUser = IMDB.getInstance().getUser(r.getSolver());
            creatorUser.notifyUser(solverUser, Event.RECEIVED_REQUEST, r.getDescription());
        }
        addCreatedRequest(r);
    }

    @Override
    public void removeRequest(Request r, Staff contributor) {
        String notification;
        if (r.getRequestType() == RequestTypes.OTHERS || r.getRequestType() == RequestTypes.DELETE_ACCOUNT) {
            new Admin.RequestHolder().removeTeamRequest(r);
            for (Admin admin : IMDB.getInstance().getAdmins()) {
                notification = "Cerere noua pentru echipa de admini de la \"" + r.getCreator() + "\": " + r.getDescription();
                admin.removeNotifications(notification);
            }
        } else {
            contributor.removeIndividualRequest(r);
            notification = "Cerere noua de la \"" + r.getCreator() + "\": " +r.getDescription();
            User<?> solverUser = IMDB.getInstance().getUser(r.getSolver());
            solverUser.removeNotifications(notification);
        }
        removeCreatedRequest(r);
    }
}
