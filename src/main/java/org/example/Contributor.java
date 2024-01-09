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
        if (r.getRequestType() == RequestTypes.OTHERS || r.getRequestType() == RequestTypes.DELETE_ACCOUNT) {
            new Admin.RequestHolder().addTeamRequest(r);
        } else {
            contributor.addIndividualRequest(r);
        }
        addCreatedRequest(r);
    }

    @Override
    public void removeRequest(Request r, Staff contributor) {
        if (r.getRequestType() == RequestTypes.OTHERS || r.getRequestType() == RequestTypes.DELETE_ACCOUNT) {
            new Admin.RequestHolder().removeTeamRequest(r);
        } else {
            contributor.removeIndividualRequest(r);
        }
        removeCreatedRequest(r);
    }
}
