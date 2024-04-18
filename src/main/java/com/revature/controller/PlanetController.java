package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import com.revature.MainDriver;
import com.revature.models.Planet;
import com.revature.service.PlanetService;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(int currentUserId) {
		List<Planet> allPlanets = planetService.getAllPlanets();
		List<Planet> userPlanets = new ArrayList<Planet>();

		//FIlter current user's planets from all planets
		for (Planet planet : allPlanets) {
				if(planet.getOwnerId() == currentUserId){
					userPlanets.add(planet);
				}
		}
	}

	public void getPlanetByName(int currentUserId, String name) {
		// TODO: implement
	}

	public void getPlanetByID(int currentUserId, int id) {
		// TODO: implement
	}

	public void createPlanet(int currentUserId, Planet planet) {
		Planet newPlanet = planetService.createPlanet(currentUserId, planet);
		if(newPlanet.getId() != 0){
			System.out.println(String.format("Planet Creation Successful: Keep an eye out for Planet %s!", newPlanet.getName()));
			System.out.println("-------------------------------------------------------------------------------------------");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}

	public void deletePlanet(int currentUserId, int id) {
		Planet target = planetService.getPlanetById(currentUserId, id);
		int planetOwner = target.getOwnerId();

		if(currentUserId == planetOwner){
			if(planetService.deletePlanetById(id)){
				System.out.println("-------------------------------------------------------------------------------------------");
				System.out.println(String.format("Planet Deletion Successful: Goodbye %s!", target.getName()));
				System.out.println("-------------------------------------------------------------------------------------------");
			} else {
				System.out.println("-------------------------------------------------------------------------------------------");
			}
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("Planet Deletion Failed: You do not own a planet with this id");
			System.out.println("-------------------------------------------------------------------------------------------");
		}
		
		// TODO: implement
	}

	//Used to check if planet belongs to user
	public void validPlanetByID(int currentUserId, int id) {
		if(planetService.getPlanetById(currentUserId, id).getOwnerId() == currentUserId){
			MainDriver.validation = true;
		} else {
			MainDriver.validation = false;
		}

		//System.out.println("User ID: " + currentUserId);
		//System.out.println("Planet Owner: " + planetService.getPlanetById(currentUserId, id).getOwnerId());
	}

	public void createMoonHelper(int currentUserId) {
		List<Planet> allPlanets = planetService.getAllPlanets();
		List<Planet> userPlanets = new ArrayList<Planet>();

		//FIlter current user's planets from all planets
		for (Planet planet : allPlanets) {
				if(planet.getOwnerId() == currentUserId){
					userPlanets.add(planet);
				}
		}

		System.out.println("Here are your planets:");

		if(userPlanets.isEmpty()){
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("You have no planets yet, please input 0 to return to the menu");
			System.out.println("-------------------------------------------------------------------------------------------");
		} else {
			for (Planet planet : userPlanets) {
				System.out.println(planet.getId() + ". " + planet.getName());	
			}
		}
	}
}
