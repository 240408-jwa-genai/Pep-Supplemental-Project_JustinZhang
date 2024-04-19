package com.revature.service;

import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// since this is where the credentials are actually authenticated we can use the username and newly finished
		// dao method to find users by username, and check to see if we get a user back
		User possibleUser = dao.getUserByUsername(loginRequestData.getUsername());
		//Checking that the user exists in the database
		if(possibleUser.getUsername() != null){
			//check if the password is correct
			boolean passwordsMatch = loginRequestData.getPassword().equals(possibleUser.getPassword());
			if (passwordsMatch){
				return possibleUser;
			} else {
				//Password is incorrect
				System.out.println("-------------------------------------------------------------------------------------------");
				System.out.println("Login failed: Password is incorrect, please try again");
			}
		} else {
			//Username doesn't exist
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("Login failed: Username doesn't exist, please try again");
		}

		return new User();
	}

	public User register(User registerRequestData) {
		//Check that both username and password are less than or equal to 30 characters
		if (registerRequestData.getUsername().length() <= 30 && registerRequestData.getPassword().length() <= 30){

			//Find an existing user in the database with the same name if possible
			User databaseUser = dao.getUserByUsername(registerRequestData.getUsername());

			if(databaseUser != null){
				String databaseUsername = databaseUser.getUsername();
				String registerUsername = registerRequestData.getUsername();

				if (!registerUsername.equals(databaseUsername)){
					//The username is not found in the database
					UsernamePasswordAuthentication newUser = new UsernamePasswordAuthentication();
					newUser.setUsername(registerUsername);
					newUser.setPassword(registerRequestData.getPassword());

					return dao.createUser(newUser);
				} else {
					System.out.println("-------------------------------------------------------------------------------------------");
					System.out.println("Registration failed: Username already exists, please try again");
				}
			}
		} else {
			//Either the username or password is too long
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("Registration failed: Username or password is too long, please try again");
		}
		//The registration process failed somewhere
		return new User();
	}

	public boolean getUserById(int loggedInUserId){
		User checkUser = dao.getUserById(loggedInUserId);

		if(checkUser.getId() == loggedInUserId){
			return true;
		}

		return false;
	}

	public List<Planet> getPlanets(int currentUserId){
		return dao.viewPlanets(currentUserId);
	}

	public List<Moon> getMoons(int planetId){
		return dao.viewMoons(planetId);
	}
}
