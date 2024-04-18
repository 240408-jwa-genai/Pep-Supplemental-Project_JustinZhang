package com.revature.controller;

import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.service.MoonService;

public class MoonController {
	
	private MoonService moonService;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons(int currentUserId) {
		moonService.getAllMoons();
	}

	public void getMoonByName(int currentUserId, String name) {
		moonService.getMoonByName(currentUserId, name);
	}

	public void getMoonById(int planetId, int id) {
		moonService.getMoonById(planetId, id);
	}

	public void createMoon(int currentUserId, Moon moon) {
		Moon newMoon = moonService.createMoon(moon);
		if(newMoon.getId() != 0){
			System.out.println(String.format("Moon Creation Successful: Keep an eye out for Moon %s!", newMoon.getName()));
			System.out.println("-------------------------------------------------------------------------------------------");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}

	public boolean moonBelongs(int moonId, int userId){
		Planet targetPlanet = moonService.getPlanetByID(moonService.getMoonById(userId, moonId).getMyPlanetId());

		if(targetPlanet.getOwnerId() == userId) return true;

		return false;
	}
	public void deleteMoon(int id) {
		Moon target = moonService.getMoonById(id, id);

		if(moonService.deleteMoonById(id)){
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println(String.format("Moon Deletion Successful: Goodbye %s!", target.getName()));
			System.out.println("-------------------------------------------------------------------------------------------");
		}

	}
	
	public void getPlanetMoons(int myPlanetId) {
		// TODO: implement
	}

	public void viewMoons(int currentUserId){
		List<Planet> planets = moonService.getPlanets(currentUserId);
		
        for (Planet planet : planets) {
            List<Moon> moons = moonService.getMoons(planet.getId());
            for (Moon moon : moons) {
                System.out.println(moon.getId() + ". " + moon.getName() + " orbiting " + planet.getName());
            }
        }
	}
}
