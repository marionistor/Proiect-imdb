package org.example;

public interface RequestsManager {
    public void createRequest(Request r, Staff contributor);
    public void removeRequest(Request r, Staff contributor);
}
