package com.revature.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to get all planets
            String sql = "SELECT * FROM planets";
            PreparedStatement ps = connection.prepareStatement(sql);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            List<Planet> planetList = new ArrayList<Planet>();
            
            //if there existing planets, put data into list of planets
            while(rs.next()){
                Planet newPlanet = new Planet();
                newPlanet.setId(rs.getInt("id"));
                newPlanet.setName(rs.getString("name"));
                newPlanet.setOwnerId(rs.getInt("ownerId"));
                planetList.add(newPlanet);
            }

            return planetList;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
	}

	public Planet getPlanetByName(String planetName) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to query the name
            String sql = "SELECT * FROM planets WHERE name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, planetName);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            Planet newPlanet = new Planet();
            //if there is an existing user, put data into the form of a User object
            if(rs.next()){
                newPlanet.setId(rs.getInt("id"));
                newPlanet.setName(rs.getString("name"));
                newPlanet.setOwnerId(rs.getInt("ownerId"));
            }

            return newPlanet;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }	
	}

	public Planet getPlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to query the name
            String sql = "SELECT * FROM planets WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            Planet newPlanet = new Planet();
            //if there is an existing user, put data into the form of a User object
            if(rs.next()){
                newPlanet.setId(rs.getInt("id"));
                newPlanet.setName(rs.getString("name"));
                newPlanet.setOwnerId(rs.getInt("ownerid"));
            }

            return newPlanet;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
	}

	public Planet createPlanet(Planet p) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to create a new planet
            String sql = "INSERT INTO planets (name, ownerId) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getOwnerId());
            //Executing the statement 
            ps.executeUpdate();
            //Get the new generated id
            ResultSet rs = ps.getGeneratedKeys();
            //Create new user to be returned
            Planet newPlanet = new Planet();
            newPlanet.setName(p.getName());
            newPlanet.setOwnerId(p.getOwnerId());
            if(rs.next()){
                // since we are only returning the generated id, we can get it by referencing column 1
                newPlanet.setId(rs.getInt(1));
            }
            return newPlanet;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
	}

	public boolean deletePlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to create a new planet
            String sql = "DELETE FROM planets WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            //Executing the statement 
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return false;
        }
	}

    public List<Moon> getMoons(int planetId){
        try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to get all planets
            String sql = "SELECT * FROM moons WHERE myPlanetId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            List<Moon> moonList = new ArrayList<Moon>();
            
            //if there existing planets, put data into list of planets
            while(rs.next()){
                Moon newMoon = new Moon();
                newMoon.setId(rs.getInt("id"));
                newMoon.setName(rs.getString("name"));
                newMoon.setMyPlanetId(rs.getInt("myPlanetId"));
                moonList.add(newMoon);
            }

            return moonList;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
    }

	public static void main(String[] args) {
		PlanetDao pDao = new PlanetDao();

        List<Planet> planetList = pDao.getAllPlanets();

        List<Moon> moonList = pDao.getMoons(1);
        /*int id = 0;
        int oId = 0;
        String name = "";
        for(int i = 0; i < 10; i++){
            Planet newPlanet = new Planet();
            id++;
            oId+=2;
            name = name + "a";
            newPlanet.setId(id);
            newPlanet.setName(name);
            newPlanet.setOwnerId(oId);
            planetList.add(newPlanet);
        }
		/*Planet test = new Planet();
		test.setName("Test");
		test.setOwnerId(100);
		pDao.createPlanet(test);*/

		//Planet name = pDao.getPlanetByName("planetName");
		//Planet id = pDao.getPlanetById(1);
		//Planet testGet = pDao.getPlanetByName("Test");
        for (Moon moon : moonList) {
            System.out.println("Space");
            System.out.println(moon.toString());
        }
		
	}
}
