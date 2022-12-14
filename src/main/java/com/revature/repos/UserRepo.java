package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements  CRUDDaoInterface<User>{


        public Connection conn;

        //need a logger for each class
        private static final Logger LOGGER = LoggerFactory.getLogger(UserRepo.class);

        public UserRepo(){
//
//            String url = "jdbc:postgresql://localhost:5433/postgres?currentSchema=flash-card-demo";
//            String username = "postgres";
//            String password = "1234pass!";



            // Note:certain methods throw errors because there are different instances of what can go wrong.
            // in order to handle those errors in a way that saves the application from crashing
            // we wrap those methods or blocks of code in a "try{}catch(){}" block

            try {
                // this is the code that can throw an error.
                 conn = ConnectionManager.getConnection();

                //System.out.println(conn.getSchema());
            } catch (SQLException sqlException){
                //System.out.println(sqlException.getMessage());
                LOGGER.error(sqlException.getMessage());

            }

        }

        @Override
        public int create(User user) {

            //although it says create, we are inserting into a table that is already created
            // however we are still creating a new row...

            try {
                String sql = "INSERT INTO users (id, first_name, last_name, email, pass_word, zodiac_sign, mood) VALUES (default,?,?,?,?,?,?)";

                //feeds it to the database management system
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, user.getFirstname());
                pstmt.setString(2, user.getLastname());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getPassword());
                pstmt.setString(5, user.getZodiac());
                pstmt.setString(6, user.getMood());

                // this executes the sql statement above
                pstmt.executeUpdate();

                // this gives us a result set of the row that was just created
                ResultSet rs = pstmt.getGeneratedKeys();

                // the cursor is right in front of the first (or only) element in our results set.
                // calling rs.next() iterates us into the first row.
                rs.next();
                return rs.getInt("id");

            }catch(SQLException sqlException){
                System.out.println(sqlException.getMessage());
            }
            return 0;
        }

        @Override
        public List<User> getAll() {

            List<User> users = new ArrayList<>();

            try {

                String sql = "SELECT * FROM users";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFirstname(rs.getString("first_name"));
                    user.setLastname(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("pass_word"));
                    user.setZodiac(rs.getString("zodiac_sign"));
                    user.setMood(rs.getString("mood"));

                    users.add(user);

                }
                return users;

            } catch(SQLException sqlException){
                System.out.println(sqlException.getMessage());
            }

            return null;
        }

        @Override
        public User getById(int id) {

            try{

                String sql = "SELECT * FROM users WHERE id = ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,id);

                ResultSet rs = pstmt.executeQuery();

                // we are returning a user therefore we have to create a new instance of a user from the database
                User user = new User();

                while (rs.next()){
                    user.setId(rs.getInt("id"));
                    user.setFirstname(rs.getString("first_name"));
                    user.setLastname(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("pass_word"));
                    user.setZodiac(rs.getString("zodiac"));
                    user.setMood(rs.getString("mood"));
                }

                return user;

            }catch(SQLException sqlException){
                System.out.println(sqlException.getMessage());
            }

            return null;
        }

        @Override
        public User update(User user) {

            try {
                // we are updating a row at a specific id
                String sql = "UPDATE users SET mood = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1,user.getMood());
                pstmt.setInt(2,user.getId());

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                while(rs.next()){
                    user.setEmail(rs.getString("mood"));
                }
                return user;

            }catch(SQLException sqlException){
                System.out.println(sqlException.getMessage());

            }
            return null;
        }

        @Override
        public boolean delete(User user) {
            //Delete from tablename where id = ?

            try{

                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1,user.getId());

                //pstmt.execute() returns a boolean
                //it returns false if the executed statement returns void

                pstmt.execute();
                return true;
            }catch(SQLException sqlException){

                System.out.println(sqlException.getMessage());
            }
            return false;
        }
        public boolean deleteByUserId(int id){
            try {

                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, id);

                //pstmt.execute() returns a boolean
                // it returns false if the executed statement returns void

                pstmt.execute();
                return true;
            }catch(SQLException sqlException){
                System.out.println(sqlException.getMessage());
            }
            return false;
        }

    public User loginUser(User user){

        try {

            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next() && rs.getString("pass_word").equals(user.getPassword())){

                return new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("pass_word"),
                        rs.getString("zodiac_sign"),
                        rs.getString("mood"));
            }


        }catch(Exception e){
            System.out.println("This is the userDAO: " + e.getMessage());
        }

        return null;
    }



}
