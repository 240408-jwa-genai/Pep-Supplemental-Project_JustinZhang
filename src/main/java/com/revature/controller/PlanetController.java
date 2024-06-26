package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import com.revature.MainDriver;
import com.revature.models.Moon;
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
		planetService.getPlanetByName(currentUserId, name);
	}

	public void getPlanetByID(int currentUserId, int id) {
		planetService.getPlanetById(currentUserId, id);
	}

	public void createPlanet(int currentUserId, Planet planet) {
		Planet newPlanet = planetService.createPlanet(currentUserId, planet);
		if(newPlanet.getId() != 0){
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println(String.format("Planet Creation Successful: Keep an eye out for Planet %s!", newPlanet.getName()));
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}

	public boolean verifyPlanet(int currentUserId, int planetId){
		Planet target = planetService.getPlanetById(currentUserId, planetId);
		int planetOwner = target.getOwnerId();

		if(currentUserId == planetOwner) return true;
		
		return false;
	}
	public void deletePlanet(int currentUserId, int id) {
		Planet target = planetService.getPlanetById(currentUserId, id);
		
		if(planetService.deletePlanetById(id)){
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println(String.format("Planet Deletion Successful: Goodbye %s!", target.getName()));
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}
	

	public List<Moon> getMoons(int planetId){
		return planetService.getMoons(planetId);
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
			System.out.println("");
		} else {
			for (Planet planet : userPlanets) {
				System.out.println(planet.getId() + ". " + planet.getName());	
			}
		}
	}
}
