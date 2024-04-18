package com.revature.service;

import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets() {
		return dao.getAllPlanets();
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		return dao.getPlanetByName(planetName);
	}

	public Planet getPlanetById(int ownerId, int planetId) {
		return dao.getPlanetById(planetId);
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		String inputName = planet.getName();

		if(inputName.length() <= 30){
			//Check for length restriction of 30 characters

			Planet existingPlanet = dao.getPlanetByName(inputName);

			if(existingPlanet != null){
				String existingName = existingPlanet.getName();

				if(!inputName.equals(existingName)){
					//Name is not used by other planets
					return dao.createPlanet(planet);
				} else {
					System.out.println("-------------------------------------------------------------------------------------------");
					System.out.println("Planet Creation failed: Planet name is already taken, please try again");
				}
			}

		} else {
			//Planet's name is longer than 30 characters
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("Planet Creation failed: Planet name is too long, please try again");
		}
		return new Planet();
	}

	public boolean deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
		return dao.deletePlanetById(planetId);
	}
}
