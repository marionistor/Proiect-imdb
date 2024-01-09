package org.example;

import java.time.LocalDateTime;

public class Request {
    private RequestTypes requestType;
    private LocalDateTime date;
    private String titleName;
    private String description;
    private String creator;
    private String solver;

    public Request() {}

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
}
