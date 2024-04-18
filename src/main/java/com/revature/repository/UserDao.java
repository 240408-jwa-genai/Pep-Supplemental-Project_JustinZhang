package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

public class UserDao {
    
    public User getUserByUsername(String username){
        try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to query the username
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            //Executing the statement 
            ResultSet rs = ps.executeQuery();
            User possibleUser = new User();
            //if there is an existing user, put data into the form of a User object
            if(rs.next()){
                possibleUser.setId(rs.getInt("id"));
                possibleUser.setUsername(rs.getString("username"));
                possibleUser.setPassword(rs.getString("password"));
            }

            return possibleUser;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
    }

    public User createUser(UsernamePasswordAuthentication registerRequest) {
        try (Connection connection = ConnectionUtil.createConnection()){
            //Setting up an SQL statement to create a new user
            String sql = "INSERT INTO users (username, password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());
            //Executing the statement 
            ps.executeUpdate();
            //Get the new generated id
            ResultSet rs = ps.getGeneratedKeys();
            //Create new user to be returned
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            if(rs.next()){
                // since we are only returning the generated id, we can get it by referencing column 1
                newUser.setId(rs.getInt(1));
            }
            return newUser;
        } catch (SQLException e) {
            //Handle exception and returns null
            e.printStackTrace();
            return null;
        }
    }

    public List<Moon> viewMoons(int planetId){
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

    public List<Planet> viewPlanets(int ownerId){
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
    public static void main(String[] args) {
        UserDao uDao = new UserDao();
        List<Planet> planets = uDao.viewPlanets(1);

        for (Planet planet : planets) {
            List<Moon> moons = uDao.viewMoons(planet.getId());
            System.out.println(planet.toString());
            for (Moon moon : moons) {
                System.out.println(moon.toString());
            }
            System.out.println("-------------------------");
        }

    }
}
