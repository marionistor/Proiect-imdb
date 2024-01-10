package org.example;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IMDB {
    private List<User<?>> usersList;
    private List<Actor> actorsList;
    private List<Request> requestsList;
    private List<Production> productionsList;

    private static IMDB imdbObj = null;

    private IMDB() {
        usersList = new ArrayList<>();
        actorsList = new ArrayList<>();
        requestsList = new ArrayList<>();
        productionsList = new ArrayList<>();
    }

    public static IMDB getInstance() {
        if (imdbObj == null)
            imdbObj = new IMDB();
        return imdbObj;
    }

    public List<Actor> getActorsList() {
        return actorsList;
    }

    public List<Production> getProductionsList() {
        return productionsList;
    }
    public void addProduction(Production production) {
        productionsList.add(production);
    }
    public List<User<?>> getUsersList() {
        return usersList;
    }
    public void addUser(User<?> user) {
        usersList.add(user);
    }
    public void removeUser(User<?> user) {
        usersList.remove(user);
    }
    public void removeProduction(Production production) {
        productionsList.remove(production);
    }
    public void addActor(Actor a) {
        actorsList.add(a);
    }
    public void removeActor(Actor actor) {
        actorsList.remove(actor);
    }
    public void removeFromUsersFavorites(Object o) {
        for (User<?> user : usersList) {
            user.getFavoritesSet().remove(o);
        }
    }
    public User<?> getUserByName(String username) {
        for(User<?> user : usersList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public String[] getProductionsNames(User<?> user) {
        String[] prodNames = new String[productionsList.size()];
        int index = 0;
        for (Production production : productionsList) {
            if (user instanceof Contributor) {
                if (!((Contributor) user).getContributions().contains(production)) {
                    prodNames[index++] = production.getTitle();
                }
            } else {
                prodNames[index++] = production.getTitle();
            }
        }
        return prodNames;
    }
    public String[] getActorsNames(User<?> user) {
        String[] actorsNames = new String[actorsList.size()];
        int index = 0;
        for (Actor actor : actorsList) {
            if (user instanceof Contributor) {
                if (!((Contributor) user).getContributions().contains(actor)) {
                    actorsNames[index++] = actor.getName();
                }
            }
            actorsNames[index++] = actor.getName();
        }
        return actorsNames;
    }
    public boolean searchActorName(String actorName) {
        for (Actor actor : actorsList) {
            if (actor.getName().equals(actorName)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchProductionTitle(String productionTitle) {
        for (Production production : productionsList) {
            if (production.getTitle().equals(productionTitle)) {
                return true;
            }
        }
        return false;
    }

    public Production getProduction(String productionName) {
        for (Production production : productionsList) {
            if (production.getTitle().equals(productionName)) {
                return production;
            }
        }
        return null;
    }
    public List<Admin> getAdmins() {
        List<Admin> admins = new ArrayList<>();
        for (User<?> user : usersList) {
            if (user instanceof Admin) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }
    public Actor getActor(String actorName) {
        for (Actor actor : actorsList) {
            if (actor.getName().equals(actorName)) {
                return actor;
            }
        }
        return null;
    }
    public Staff contributorUser(String titleName) {
        for (User<?> user : usersList) {
            if (user instanceof Staff) {
                for (Object contribution : ((Staff) user).getContributions()) {
                    if (contribution instanceof Actor) {
                        Actor actor = (Actor) contribution;
                        if (actor.getName().equals(titleName)) {
                            return (Staff) user;
                        }
                    } else {
                        Production production = (Production) contribution;
                        if (production.getTitle().equals(titleName)) {
                            return (Staff) user;
                        }
                    }
                }
            }
        }
        return null;
    }
    public boolean searchCredentials(String email, String password) {
        for (User<?> user : usersList) {
            if (user.getUserInfo().getUserCredentials().getEmail().equals(email) &&
                user.getUserInfo().getUserCredentials().getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public User<?> getUser(String email) {
        for (User<?> user: usersList) {
            if (user.getUserInfo().getUserCredentials().getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public List<Production> getTopProductions() {
        List<Production> CopyList = new ArrayList<>(productionsList);

        CopyList.sort(new RatingComparator());
        List<Production> TopProductionsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TopProductionsList.add(CopyList.get(i));
        }
        return TopProductionsList;
    }
    public List<Production> getFilteredProductions(Double minRating, Double maxRating) {
        List<Production> filtered = new ArrayList<>();
        for (Production production : productionsList) {
            if (production.getAverageRating() >= minRating && production.getAverageRating() <= maxRating) {
                filtered.add(production);
            }
        }
        return filtered;
    }
    public List<Production> getGenreProductions(Genre genre) {
        List<Production> filtered = new ArrayList<>();
        for (Production production : productionsList) {
            if (production.getGenres().contains(genre)) {
                filtered.add(production);
            }
        }
        return filtered;
    }

    public List<Actor> sortActorsAlphabetically() {
        List<Actor> actorsAlphabeticList = new ArrayList<>(actorsList);
        actorsAlphabeticList.sort(new ActorComparator());
        return actorsAlphabeticList;
    }
    public List<Actor> sortActorsReverseAlphabetically() {
        List<Actor> actorsAlphabeticList = sortActorsAlphabetically();
        Collections.reverse(actorsAlphabeticList);
        return actorsAlphabeticList;
    }
    public void parseActors() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/input/actors.json"));

            for (Object obj : jsonArray) {
                Actor newActor = new Actor();
                JSONObject object = (JSONObject) obj;

                Object name = object.get("name");
                newActor.setName(name.toString());
                newActor.setImageIcon(name.toString());

                Object biography = object.get("biography");
                if (biography != null) {
                    newActor.setBiography(biography.toString());
                }

                JSONArray performances = (JSONArray) object.get("performances");
                for (Object performance : performances) {
                    JSONObject performanceField = (JSONObject) performance;
                    Object title = performanceField.get("title");
                    Object type = performanceField.get("type");
                    newActor.addPerformance(title.toString(), type.toString());
                }

                actorsList.add(newActor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseProduction() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/input/production.json"));

            for (Object obj : jsonArray) {
                JSONObject object = (JSONObject) obj;
                Production newProduction;

                String type = object.get("type").toString();

                if (type.equals("Movie")) {
                    newProduction = new Movie();

                    Object duration = object.get("duration");
                    ((Movie) newProduction).setDuration(duration.toString());

                    Object releaseYearObj = object.get("releaseYear");
                    if (releaseYearObj != null) {
                        Long releaseYearLong = (Long) releaseYearObj;
                        int releaseYear = releaseYearLong.intValue();
                        ((Movie) newProduction).setReleaseYear(releaseYear);
                    }
                } else {
                    newProduction = new Series();

                    Object releaseYearObj = object.get("releaseYear");
                    if (releaseYearObj != null) {
                        Long releaseYearLong = (Long) releaseYearObj;
                        int releaseYear = releaseYearLong.intValue();
                        ((Series) newProduction).setReleaseYear(releaseYear);
                    }

                    Object numSeasonsObj = object.get("numSeasons");
                    if (numSeasonsObj != null) {
                        Long numSeasonsLong = (Long) numSeasonsObj;
                        int numSeasons = numSeasonsLong.intValue();
                        ((Series) newProduction).setNumSeasons(numSeasons);
                    }

                    JSONObject seasons = (JSONObject) object.get("seasons");
                    for (Object season : seasons.keySet()) {
                        String seasonName = season.toString();

                        List<Episode> episodesList = new ArrayList<>();
                        JSONArray episodes = (JSONArray) seasons.get(seasonName);
                        for (Object episode : episodes) {
                            JSONObject episodeField = (JSONObject) episode;
                            Object episodeName = episodeField.get("episodeName");
                            Object duration = episodeField.get("duration");
                            Episode episodeObject = new Episode(episodeName.toString(), duration.toString());
                            episodesList.add(episodeObject);
                        }
                        ((Series) newProduction).addSeason(seasonName, episodesList);
                    }
                }

                Object title = object.get("title");
                newProduction.setTitle(title.toString());
                newProduction.setImageIcon(title.toString());

                Object plot = object.get("plot");
                newProduction.setPlot(plot.toString());

                Object averageRating = object.get("averageRating");
                newProduction.setAverageRating((Double) averageRating);

                JSONArray directors = (JSONArray) object.get("directors");
                for (Object director : directors) {
                    newProduction.addDirector(director.toString());
                }

                JSONArray genres = (JSONArray) object.get("genres");
                for (Object genre : genres) {
                    newProduction.addGenre(Genre.valueOf(genre.toString()));
                }

                JSONArray ratings = (JSONArray) object.get("ratings");
                for (Object rating : ratings) {
                    JSONObject ratingField = (JSONObject) rating;
                    Rating r = new Rating();
                    Object username = ratingField.get("username");
                    if (username != null) {
                        r.setUsername(username.toString());
                    }
                    Object grade = ratingField.get("rating");
                    if (grade != null) {
                        Long gradeLong = (Long) grade;
                        int gradeInt = gradeLong.intValue();
                        r.setGrade(gradeInt);
                    }
                    Object comment = ratingField.get("comment");
                    if (comment != null) {
                        r.setComment(comment.toString());
                    }
                    newProduction.addRating(r);
                }

                JSONArray actors = (JSONArray) object.get("actors");
                for (Object actor : actors) {
                    if (!searchActorName(actor.toString())) {
                        Request request = new Request();
                        request.setRequestType(RequestTypes.OTHERS);
                        request.setSolver("ADMIN");
                        String description = "Please add the actor " + actor +" with performance "  + title + " and type " + type + ".";
                        request.setDescription(description);
                        request.setTitleName(actor.toString());
                        ZonedDateTime date = ZonedDateTime.now();
                        DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        String dateStr = date.format(DTFormatter);
                        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DTFormatter);
                        request.setDate(dateTime);
                        requestsList.add(request);
                        Admin.RequestHolder.TeamRequestsList.add(request);
                    }
                    newProduction.addActor(actor.toString());
                }
                productionsList.add(newProduction);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void parseAccounts() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/input/accounts.json"));

            for (Object obj : jsonArray) {
                JSONObject object = (JSONObject) obj;

                String username = object.get("username").toString();

                JSONObject information = (JSONObject) object.get("information");
                JSONObject credentials = (JSONObject) information.get("credentials");

                String email = credentials.get("email").toString();
                String password = credentials.get("password").toString();

                Credentials newCredentials = new Credentials(email, password);

                String name = information.get("name").toString();
                String country = information.get("country").toString();
                String birthday = information.get("birthDate").toString();
                DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(birthday, DTFormatter);
                Object age = information.get("age");
                Long ageLong = (Long) age;
                int ageInt = ageLong.intValue();
                String gender = information.get("gender").toString();
                char genderChar = gender.charAt(0);

                Object experience = object.get("experience");
                int experienceInt;
                if (experience != null) {
                    experienceInt = Integer.parseInt((experience.toString()));
                } else {
                    experienceInt = 0;
                }

                String type = object.get("userType").toString();
                User<?> newUser = UserFactory.factory(AccountType.valueOf(type.toUpperCase()), username, name, country, ageInt, genderChar, date.atStartOfDay(), newCredentials);

                if (!type.equals("Regular")) {
                    JSONArray productionsContributions = (JSONArray) object.get("productionsContribution");
                    if (productionsContributions != null) {
                        for (Object productionContribution : productionsContributions) {
                            Production production = getProduction(productionContribution.toString());
                            if (newUser != null) {
                                ((Staff) newUser).addContribution(production);
                            }
                        }
                    }

                    JSONArray actorsContributions = (JSONArray) object.get("actorsContribution");
                    if (actorsContributions != null) {
                        for (Object actorsContribution : actorsContributions) {
                            Actor actor = getActor(actorsContribution.toString());
                            if (newUser != null) {
                                ((Staff) newUser).addContribution(actor);
                            }
                        }
                    }
                }
                if (newUser != null) {
                    newUser.setUserExperience(experienceInt);
                }
                newUser.setAccountType(AccountType.valueOf(type.toUpperCase()));
                JSONArray favoriteProductions = (JSONArray) object.get("favoriteProductions");
                if (favoriteProductions != null) {
                    for (Object favoriteProduction : favoriteProductions) {
                        Production production = getProduction(favoriteProduction.toString());
                        newUser.addFavorite(production);
                    }
                }

                JSONArray favoriteActors = (JSONArray) object.get("favoriteActors");
                if (favoriteProductions != null) {
                    for (Object favoriteActor : favoriteActors) {
                        Actor actor = getActor(favoriteActor.toString());
                        newUser.addFavorite(actor);
                    }
                }

                JSONArray notifications = (JSONArray) object.get("notifications");
                if (notifications != null) {
                    for (Object notification : notifications) {
                        newUser.update(notification.toString());
                    }
                }

                usersList.add(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseRequests() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/input/requests.json"));

            for (Object obj : jsonArray) {
                Request newRequest = new Request();
                JSONObject object = (JSONObject) obj;

                String type = object.get("type").toString();
                newRequest.setRequestType(RequestTypes.valueOf(type));

                String createdDate = object.get("createdDate").toString();
                DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(createdDate, DTFormatter);
                newRequest.setDate(dateTime);

                String username = object.get("username").toString();
                newRequest.setCreator(username);

                String to = object.get("to").toString();
                newRequest.setSolver(to);

                String description = object.get("description").toString();
                newRequest.setDescription(description);

                if (type.equals("ACTOR_ISSUE")) {
                    String actorName = object.get("actorName").toString();
                    newRequest.setTitleName(actorName);
                }

                if (type.equals("MOVIE_ISSUE")) {
                    String movieTitle = object.get("movieTitle").toString();
                    newRequest.setTitleName(movieTitle);
                }

                requestsList.add(newRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObservers() {
        for (User<?> user : usersList) {
            if (user instanceof Staff) {
                for (Object contribution : ((Staff) user).getContributions()) {
                    if (contribution instanceof Production) {
                        ((Production) contribution).addObserver(user, Event.ADDED_PRODUCTION_REVIEW);
                    }
                }
            }
            for (Object favorite : user.getFavoritesSet()) {
                if (favorite instanceof Production) {
                    ((Production) favorite).addObserver(user, Event.FAVORITE_PRODUCTION_REVIEW);
                }
            }
        }

        for (Production production : productionsList) {
            for (Rating rating : production.getRatings()) {
                User<?> user = getUserByName(rating.getUsername());
                production.addObserver(user, Event.RATED_PRODUCTION_REVIEW);
            }
        }
    }

    public void run() {
        parseActors();
        parseProduction();
        parseAccounts();
        parseRequests();

        addObservers();

        // notify user about parsed requests
        for (Request request : requestsList) {
            User<?> creatorUser = getUserByName(request.getCreator());
            if (request.getSolver().equals("ADMIN")) {
                Admin.RequestHolder.TeamRequestsList.add(request);
                for (Admin admin : getAdmins()) {
                    creatorUser.notifyUser(admin, Event.ADMIN_RECEIVED_REQUESTS, request.getDescription());
                }
            } else {
                User<?> solverUser = getUserByName(request.getSolver());
                ((Staff) solverUser).getIndividualRequestsList().add(request);
                creatorUser.notifyUser(solverUser, Event.RECEIVED_REQUEST, request.getDescription());
            }
        }

        new SignInScreen();
    }

    public static void main(String[] args) {
        IMDB.getInstance().run();
    }
}
