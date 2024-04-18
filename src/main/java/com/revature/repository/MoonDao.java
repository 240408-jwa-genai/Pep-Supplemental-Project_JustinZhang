package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to get all planets
            String sql = "SELECT * FROM moons";
            PreparedStatement ps = connection.prepareStatement(sql);
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

	public Moon getMoonByName(String moonName) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to query the name
            String sql = "SELECT * FROM moons WHERE name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, moonName);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            Moon newMoon = new Moon();
            //if there is an existing user, put data into the form of a User object
            if(rs.next()){
                newMoon.setId(rs.getInt("id"));
                newMoon.setName(rs.getString("name"));
                newMoon.setMyPlanetId(rs.getInt("myPlanetId"));
            }

            return newMoon;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
	}

	public Moon getMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to query the name
            String sql = "SELECT * FROM moons WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            Moon newMoon = new Moon();
            //if there is an existing user, put data into the form of a User object
            if(rs.next()){
                newMoon.setId(rs.getInt("id"));
                newMoon.setName(rs.getString("name"));
                newMoon.setMyPlanetId(rs.getInt("myPlanetId"));
            }

            return newMoon;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
	}

	public Moon createMoon(Moon m) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to create a new planet
            String sql = "INSERT INTO moons (name, myPlanetId) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getName());
            ps.setInt(2, m.getMyPlanetId());
            //Executing the statement 
            ps.executeUpdate();
            //Get the new generated id
            ResultSet rs = ps.getGeneratedKeys();
            //Create new user to be returned
            Moon newMoon = new Moon();
            newMoon.setName(m.getName());
            newMoon.setMyPlanetId(m.getMyPlanetId());
            if(rs.next()){
                // since we are only returning the generated id, we can get it by referencing column 1
                newMoon.setId(rs.getInt(1));
            }
            return newMoon;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
	}

    public Planet getPlanetByID(int planetId){
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

	public boolean deleteMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to create a new planet
            String sql = "DELETE FROM moons WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            //Executing the statement 
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return false;
        }
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		return null;
	}
    
    public List<Planet> getPlanets(int ownerId){
        try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to get all moons from a planet
            String sql = "SELECT * FROM planets WHERE ownerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ownerId);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            List<Planet> planetList = new ArrayList<Planet>();
            
            //if there are existing moons, put data into list of moons
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
    
    public List<Moon> getMoons(int planetId){
        try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to get all moons from a planet
            String sql = "SELECT * FROM moons WHERE myPlanetId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            List<Moon> moonList = new ArrayList<Moon>();
            
            //if there are existing moons, put data into list of moons
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
		MoonDao mDao = new MoonDao();

        List<Moon> moonList = mDao.getAllMoons();
        
		
        for (Moon moon : moonList) {
            System.out.println("Space");
            System.out.println(moon.toString());
        }

		Moon test = new Moon();
		test.setMyPlanetId(99);
		test.setName("TestingName");

		mDao.createMoon(test);

		Moon test1 = mDao.getMoonById(1);
		System.out.println(test1.toString());

		Moon test2 = mDao.getMoonByName("TestingName");
		System.out.println(test2.toString());

		
	}
}
