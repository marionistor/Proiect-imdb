package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Request implements Subject {
    private RequestTypes requestType;
    private LocalDateTime date;
    private String titleName;
    private String description;
    private String creator;
    private String solver;

    private List<User<?>> userObservers;

    public Request() {
        userObservers = new ArrayList<>();
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRequestType(RequestTypes requestType) {
        this.requestType = requestType;
    }
    public void setSolver(String solver) {
        this.solver = solver;
    }
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
    public RequestTypes getRequestType() {
        return requestType;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getCreator() {
        return creator;
    }
    public String getDescription() {
        return description;
    }
    public String getSolver() {
        return solver;
    }
    public String getTitleName() {
        return titleName;
    }

    public String toString() {
        return "request type: " + requestType + ", date: " + date + ", title name: " + titleName + ", description: " + description + ", solver: " + solver + "\n";
    }

    @Override
    public void addObserver(User<?> user, Event event) {
        userObservers.add(user);
    }

    @Override
    public void removeObserver(User<?> user, Event event) {
        userObservers.remove(user);
    }

    @Override
    public void notifyObserver(Event event, String type, String username, int rating, String ratingUsername) {
        String notification;
        for (User<?> user : userObservers) {
            switch (event) {
                case SOLVED_REQUEST:
                    notification = "Cererea trimisa a fost rezolvata de catre utilizatorul \"" + username + "\"";
                    user.update(notification);
                    break;
                case REJECTED_REQUEST:
                    notification = "Cererea trimisa a fost refuzata de catre utilizatorul \"" + username + "\"";
                    user.update(notification);
                    break;
                case RECEIVED_REQUEST:
                    notification = "Cerere noua de la \"" + username + "\": " + description;
                    user.update(notification);
                    break;
                case ADMIN_RECEIVED_REQUESTS:
                    notification = "Cerere noua pentru echipa de admini de la \"" + username + "\": " + description;
                    user.update(notification);
                    break;
                default:
                    break;
            }
        }
    }
}
