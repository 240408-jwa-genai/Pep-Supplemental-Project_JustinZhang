package com.revature.controller;

import java.util.List;

import com.revature.MainDriver;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void authenticate(UsernamePasswordAuthentication loginRequestData) {
		// since we are checking the credentials against the database we can simply pass them to the service layer
		User possibleUser = userService.authenticate(loginRequestData);
		if (possibleUser.getId() != 0){
			MainDriver.loggedInUserId = possibleUser.getId();
			System.out.println(String.format("Hello %s! Welcome to the Planetarium", possibleUser.getUsername()));
			System.out.println("-------------------------------------------------------------------------------------------");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}

	public void register(User registerRequestData) {
		User userResponse = userService.register(registerRequestData);
		if (userResponse.getId() != 0){
			System.out.println("Registration successful! Enjoy using the Planetarium!");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}

	public void logout() {
		System.out.println("Thank you for using the Planetarium, please come again!");
		System.out.println("-------------------------------------------------------------------------------------------");
		MainDriver.loggedInUserId = 0;
	}
	
	public boolean checkAuthorization(int userId) {	
		// TODO: implement
		return false;
	}

	public void viewAllPlanetsAndMoons(int currentUserId){
		List<Planet> planets = userService.getPlanets(currentUserId);
		
        for (Planet planet : planets) {
            List<Moon> moons = userService.getMoons(planet.getId());
            System.out.println(planet.toString());
            for (Moon moon : moons) {
                System.out.println(moon.toString());
            }
            System.out.println("-------------------------------");
        }
	}
}
