package com.revature.service;

import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(MoonDao dao) {
		this.dao = dao;
	}

	public List<Moon> getAllMoons() {
		// TODO implement
		return null;
	}

	public Moon getMoonByName(int myPlanetId, String moonName) {
		return dao.getMoonByName(moonName);
	}

	public Moon getMoonById(int myPlanetId, int moonId) {
		return dao.getMoonById(moonId);
	}

	public Moon createMoon(Moon m) {
		String inputName = m.getName();

		if(inputName.length() <= 30){
			//Check for length restriction of 30 characters

			Moon existingMoon = dao.getMoonByName(inputName);

			if(existingMoon != null){
				String existingName = existingMoon.getName();

				if(!inputName.equals(existingName)){
					//Name is not used by other planets
					return dao.createMoon(m);
				} else {
					System.out.println("-------------------------------------------------------------------------------------------");
					System.out.println("Moon Creation failed: Moon name is already taken, please try again");
				}
			}

		} else {
			//Planet's name is longer than 30 characters
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("Moon Creation failed: Moon name is too long, please try again");
		}
		return new Moon();
	}

	public Planet getPlanetByID(int planetId){
		return dao.getPlanetByID(planetId);
	}

	public boolean deleteMoonById(int moonId) {
		return dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int myPlanetId) {
		return getMoonsFromPlanet(myPlanetId);
	}

	public List<Planet> getPlanets(int currentUserId){
		return dao.getPlanets(currentUserId);
	}

	public List<Moon> getMoons(int planetId){
		return dao.getMoons(planetId);
	}
}
