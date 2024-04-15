package com.revature;

import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
import com.revature.service.UserService;
import com.revature.utilities.ConnectionUtil;

public class MainDriver {

    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);
    public static int loggedInUserId = 0;
    /*
        An example of how to get started with the registration business and software requirements has been
        provided in this code base. Feel free to use it yourself, or adjust it to better fit your own vision
        of the application. The affected classes are:
            MainDriver
            UserController
            UserService
     */
    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application
        try(Scanner scanner = new Scanner(System.in)) {
            String input = "";
            boolean userIsActive = true;
            while(userIsActive){
                System.out.println("Welcome to the Planetarium!");
                System.out.println("To perform an action, type the corresponding number or type 'quit' to exit: ");
                System.out.println("1 - Register");
                System.out.println("2 - Log In");
                System.out.print("Enter menu option: ");
                
                input = scanner.nextLine();
                
                if(input.equals("quit") || input.equals("Quit")){
                    // User quits
                    System.out.println("Thank you for using the Planetarium!");
                    userIsActive = false;

                } else if(input.equals("1")){
                    // User registers a new account
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
                    // User logs into existing account
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
                        //Logged in user menu
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
                        } else if(input.equals("1")){
                            //Create a planet menu
                            System.out.println("This is the planet creation menu");
                        } else if(input.equals("2")){
                            //Create a moon menu
                            System.out.println("This is the moon creation menu");
                        } else if(input.equals("3")){
                            //Delete a planet menu
                            System.out.println("This is the planet deleting menu");
                        } else if(input.equals("4")){
                            //Delete a moon menu
                            System.out.println("This is the moon deleting menu");
                        } else if(input.equals("5")){
                            //Viewing menu
                            System.out.println("This is the viewing menu");
                        } else {
                            System.out.println("Error: Invalid input, please try again");
                            System.out.println("Press Enter key to continue...");
                            scanner.nextLine();
                        }
                    }

                } else {
                    // User input is invalid
                    System.out.println("Error: Invalid input, please try again");
                    System.out.println("Press Enter key to continue...");
                    scanner.nextLine();
                }
            }
        }
    }
}
