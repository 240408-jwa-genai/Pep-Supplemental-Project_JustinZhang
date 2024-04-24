package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.controller.MoonController;
import com.revature.controller.PlanetController;
import com.revature.controller.UserController;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;
import com.revature.service.UserService;
import com.revature.utilities.ConnectionUtil;

public class MainDriver {

    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);

    public static PlanetDao planetDao = new PlanetDao();
    public static PlanetService planetService = new PlanetService(planetDao);
    public static PlanetController planetController = new PlanetController(planetService);

    public static MoonDao moonDao = new MoonDao();
    public static MoonService moonService = new MoonService(moonDao);
    public static MoonController moonController = new MoonController(moonService);

    public static int loggedInUserId = 0;
    public static boolean validation = false;
    /*
        An example of how to get started with the registration business and software requirements has been
        provided in this code base. Feel free to use it yourself, or adjust it to better fit your own vision
        of the application. The affected classes are:
            MainDriver
            UserController
            UserService
     */
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            String input = "";
            boolean userIsActive = true;
            while(userIsActive){
                System.out.println("Welcome to the Planetarium!");
                System.out.println("To perform an action, type the corresponding number: ");
                System.out.println("1 - Register");
                System.out.println("2 - Log In");
                System.out.println("3 - Quit");
                System.out.print("Enter menu option: ");
                
                input = scanner.nextLine();
                
                if(input.equals("3")){
                    // User quits----------------------------------------------------------------------------------------------------------
                    System.out.println("Thank you for using the Planetarium!");
                    userIsActive = false;

                } else if(input.equals("1")){
                    // User registers a new account----------------------------------------------------------------------------------------
                    System.out.println("Welcome, please create a new username");
                    System.out.println("Usernames should be unique and have 30 or less characters");
                    System.out.print("Create a username: ");
                    String newUsername = scanner.nextLine();
                    System.out.println("Please create a password");
                    System.out.println("Passwords should have 30 or less characters");
                    System.out.print("Create a password: ");
                    String newPassword = scanner.nextLine();
                    User potentialUser = new User();
                    potentialUser.setUsername(newUsername);
                    potentialUser.setPassword(newPassword);

                    // pass the data into the service layer for validation
                    userController.register(potentialUser);

                } else if(input.equals("2")){
                    // User logs into existing account----------------------------------------------------------------------------------------
                    System.out.println("Welcome back, please provide your login information!");
                    System.out.print("Username: ");
                    String existingUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String existingPassword = scanner.nextLine();

                    UsernamePasswordAuthentication potentialUser = new UsernamePasswordAuthentication();
                    potentialUser.setUsername(existingUsername);
                    potentialUser.setPassword(existingPassword);
                    
                    userController.authenticate(potentialUser);

                    while(loggedInUserId > 0){
                        //---------------------------------Logged in user menu-------------------------------------------------------
                        System.out.println("-------------------------------------------------------------------------------------------");
                        System.out.println("Planetarium Menu");
                        System.out.println("To perform an action, type the corresponding number:");
                        System.out.println("1 - Create a Planet");
                        System.out.println("2 - Create a Moon");
                        System.out.println("3 - Delete a Planet");
                        System.out.println("4 - Delete a Moon");
                        System.out.println("5 - View your Planets and Moons");
                        System.out.println("6 - Log Out");
                        System.out.print("Enter menu option: ");
                        
                        input = scanner.nextLine();
                        if(input.equals("6")){
                            userController.logout();
                        } else if(input.equals("1") && userController.checkAuthorization(loggedInUserId)){
                            //Create a planet menu--------------------------------------------------------------------------------------
                            System.out.println("Let's create a new Planet!");
                            System.out.println("Planet names should be unique and have 30 or less characters");
                            System.out.print("Please name the Planet: ");
                            String planetName = scanner.nextLine();
                            
                            Planet potentialPlanet = new Planet();
                            potentialPlanet.setName(planetName);
                            potentialPlanet.setOwnerId(loggedInUserId);

                            planetController.createPlanet(loggedInUserId, potentialPlanet);
                            
                        } else if(input.equals("2") && userController.checkAuthorization(loggedInUserId)){
                            //Create a moon menu----------------------------------------------------------------------------------------
                            System.out.println("Let's create a new Moon!");
                            System.out.println("Please select the Planet you want the Moon to orbit");

                            planetController.createMoonHelper(loggedInUserId);

                            //Planet selection
                            System.out.print("Type the number id next to the Planet: ");
                            String planetId = scanner.nextLine();
                            int pId = 0;
                            try {
                                //Valid planet id recieved 
                                pId = Integer.parseInt(planetId);
                                
                                if(pId > 0){
                                    //Let user return when they have no planets

                                    //Check if planet belongs to current user
                                    planetController.validPlanetByID(loggedInUserId, pId);
                                    if(validation == true){
                                        //Moon naming
                                        validation = false;
                                        System.out.println("Moon names should be unique and have 30 or less characters");
                                        System.out.print("Please name the Moon: ");
                                        String moonName = scanner.nextLine();

                                        Moon newMoon = new Moon();
                                        newMoon.setMyPlanetId(pId);
                                        newMoon.setName(moonName);

                                        moonController.createMoon(loggedInUserId, newMoon);
                                    } else {
                                        System.out.println("-------------------------------------------------------------------------------------------");
                                        System.out.println("This Planet is owned by a different user, please try again");
                                        System.out.println("-------------------------------------------------------------------------------------------");
                                        System.out.println("");
                                    }
                                    
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("-------------------------------------------------------------------------------------------");
                                System.out.println("Invalid input, please try again");
                                System.out.println("-------------------------------------------------------------------------------------------");
                                System.out.println("");
                            }

                        } else if(input.equals("3") && userController.checkAuthorization(loggedInUserId)){
                            //Delete a planet menu----------------------------------------------------------------------------------------
                            System.out.println("Which Planet do you want to delete?");
                            userController.viewAllPlanetsAndMoons(loggedInUserId);
                            System.out.print("Please input the Planet #: ");
                            String planetId = scanner.nextLine();

                            try {
                                //Valid planet id recieved 
                                int pId = Integer.parseInt(planetId);
                                List<Moon> moons = planetController.getMoons(pId);

                                if(planetController.verifyPlanet(loggedInUserId, pId)){
                                    if(moons.isEmpty()){
                                        planetController.deletePlanet(loggedInUserId, pId);
                                    } else {
                                        System.out.println("This Planet has moons orbiting it, deleting the Planet will also delete its moons");
                                        System.out.println("Are you sure?: ");
                                        System.out.println("1. Yes");
                                        System.out.println("2. No");
                                        System.out.print("Input confirmation: ");
                                        String confirm = scanner.nextLine();
                                        confirm = confirm.toLowerCase();
                                        if(confirm.equals("y") || confirm.equals("yes") || confirm.equals("1")){
                                            for (Moon moon : moons) {
                                                moonController.deleteMoon(moon.getId());
                                            }
                                            planetController.deletePlanet(loggedInUserId, pId);
                                        }
                                    }
                                } else {
                                    System.out.println("-------------------------------------------------------------------------------------------");
                                    System.out.println("This Planet is owned by a different user, please try again");
                                    System.out.println("-------------------------------------------------------------------------------------------");
                                    System.out.println("");
                                }
                                
                            } catch (NumberFormatException e) {
                                System.out.println("-------------------------------------------------------------------------------------------");
                                System.out.println("Invalid input, please try again");
                                System.out.println("-------------------------------------------------------------------------------------------");
                                System.out.println("");
                            }
                        } else if(input.equals("4") && userController.checkAuthorization(loggedInUserId)){
                            //Delete a moon menu----------------------------------------------------------------------------------------
                            System.out.println("Which Moon do you want to delete?");
                            System.out.println("Here are your Moons:");
                            System.out.println("-------------------------------------------------------------------------------------------");
                            moonController.viewMoons(loggedInUserId);
                            System.out.println("-------------------------------------------------------------------------------------------");
                            
                            System.out.println("If you don't have any moons, input '0'");
                            System.out.print("Please input the Moon number id listed with the Moons: ");
                            String moonId = scanner.nextLine();

                            try {
                                //Valid planet id recieved 
                                int mId = Integer.parseInt(moonId);

                                if(mId > 0){
                                    //Only enter when user has moons
                                    if(moonController.moonBelongs(mId, loggedInUserId)) {
                                        moonController.deleteMoon(mId);
                                    } else {
                                        System.out.println("-------------------------------------------------------------------------------------------");
                                        System.out.println("Moon Deletion Failed: This moon belongs to another user");
                                        System.out.println("-------------------------------------------------------------------------------------------");
                                        System.out.println("");
                                    }
                                }
                            
                            } catch (NumberFormatException e) {
                                System.out.println("-------------------------------------------------------------------------------------------");
                                System.out.println("Invalid input, please try again");
                                System.out.println("-------------------------------------------------------------------------------------------");
                                System.out.println("");
                            }
                        } else if(input.equals("5") && userController.checkAuthorization(loggedInUserId)){
                            //Viewing menu----------------------------------------------------------------------------------------
                            System.out.println("Here are your Planets and Moons:");
                            System.out.println("-------------------------------");
                            userController.viewAllPlanetsAndMoons(loggedInUserId);

                        } else if(!userController.checkAuthorization(loggedInUserId)){
                            System.out.println("Error: Unauthorized Access");
                            break;
                        } else {
                            System.out.println("Error: Invalid input, please try again");
                            System.out.println("Press Enter key to continue...");
                            scanner.nextLine();
                        }
                    }

                } else {
                    // User input is invalid-----------------------------------------------------------------------------------------
                    System.out.println("Error: Invalid input, please try again");
                    System.out.println("Press Enter key to continue...");
                    scanner.nextLine();
                }
            }
        }
    }
}
