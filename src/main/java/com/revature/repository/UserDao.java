package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
