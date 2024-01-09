package org.example;

public interface StaffInterface {
    public void addProductionSystem(Production p);
    public void addActorSystem(Actor a);
    public void removeProductionSystem(String name);
    public void removeActorSystem(String name);
    public void updateProduction(Production p, String title, String plot, int duration, double avgRating, int releaseYear);
    public void updateActor(Actor a, String name, String biography);
}
