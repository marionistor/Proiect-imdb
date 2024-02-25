
# IMDB

An IMDb Clone project implemented in Java. This project replicates key features of the IMDb website, focusing on user authentication, content management, and a Swing-based graphical user interface. Data such as user credentials, productions, reviews, etc. was stored using JSON files. Various design patterns were used in order to make the app efficient and flexible.

# Details of implementation

The IMDB class is the primary class where the program starts by creating a SignInScreen object which represents the sign-in screen. Once inside, the user must enter the email and password to go to the homepage. To hide the entered password is used JPasswordField. If the user enters the wrong email or password, a message will be displayed telling them that the data entered are wrong. 

For parsing information from json files I used the json-simple library, the data being stored in lists declared in IMDB class. A separate method shall be declared for the parsing of each file and methods are called in the run() function of the IMDB class.

Various design patterns were used in the project. The Singleton Pattern ensures one IMDb manager for user and movie data. The Builder Pattern is used for user information. The Factory Pattern is used to create different user types. The Observer Pattern keeps users updated on requests and reviews. Lastly, the Strategy Pattern adjusts user experience based on actions.

After logging in, the login screen closes and displays the main page of the application. It contains a search bar where the user can enter the name of an actor or productions from the system. If the text entered does not correspond to any production/actor, a message telling him no actor/production found. Moreover, the main page contains production recommendations(top 10 productions in the system according to rating). For every displayed production, an image, title (highlighted in yellow for emphasis), production type, release year, duration/number of seasons, rating, genres, and a dedicated button for viewing additional details are provided. The main page also contains a menu from which, depending on user type, different options can be selected. Also, from the menu the user can see profile information and log out, returning to the Log in screen.

When viewing productions, the user can choose filter options from a menu. The user can select a specific genre, displaying productions containing that genre. At the same time, the user can filter productions selecting 2 ratings (integers from 1 to 10), displaying showing the productions that fall within this range. The minimum rating selected mustbe less than or equal to the maximum rating selected, otherwise a message telling them that ratings were   entered incorrectly. For each production, there is a button to display additional information. When pressed, a separate window will open, allowing the user to view, add ratings, and manage their favorites by adding or removing content. If the user tries to add an already added production/actor to his favorites, he will receive a message informing him that the production/actor has already been added to the favorites. Similarly, if they want to add/remove ratings or remove a production/actor from their favourites. 

The actors' viewing page mirrors the productions page, allowing users to sort actors alphabetically or in reverse alphabetical order based on their names.Simultaneously, for each actor, an image is displayed. In both the actors' and productions' pages, the user can return to the initial display after filtering/sorting. Additionally, in the menu, there is an option to view the list of productions/actors added to favorites.

For creating a request, the user can select the type, and based on it, a list of actors/productions will be shown for ACTOR_ISSUE/MOVIE_ISSUE requests, along with a JTextField where the user can provide a request description. Also, the user can view a list of created requests and cancel them by pressing the "cancel" button.

Admins and contributors can see a list of received requests and can mark them as resolved or reject them. When either of these actions is taken, the request will be removed from the list of received requests, and the notification regarding the request will be deleted. The user who created the request will receive a notification about the request status.

The user can view notifications in a separate window, with the option to delete them. A notification will appear when someone rates a production in their favorites, appreciates it, or when it is part of the user's contributions.

To add a production/actor, the user will have a separate window where they will first select what they want to add to the system (actor/movie/series), after which they will need to fill in the specific data for each type. For an actor, entering the name and a production is mandatory. For a movie/series, all information must be entered. For a series, the user will first enter the number of seasons, and then they will need to enter exactly that many seasons (each season must have at least one episode), and the name of each season will be added automatically (in the form of "Season [number]").

To update an actor/production, the user can choose the actor/production from a list of contributions containing names/titles. After selection, the user can add or modify details about the chosen actor/production. For a series, the user can add new seasons or modify, add, or delete episodes from existing seasons.

When adding/updating a production/actor, the user must adhere to the required format for entering data, as all inputs are verified (some fields only accept numbers within certain ranges to avoid entering incorrect data such as a negative number for the year or duration/number of seasons or a value above 10 for the rating).

Admins can choose to add, delete, or update information about users in the system. For entering email, name, country, and date of birth, regex is used to verify their correctness. The user generates the password and username by pressing a button. During the generation of the username and password, as well as when entering the email, it is checked whether they are not already used by another user.








