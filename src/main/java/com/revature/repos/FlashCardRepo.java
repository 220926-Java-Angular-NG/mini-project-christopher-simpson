package com.revature.repos;


import com.revature.models.FlashCard;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FlashCardRepo implements CRUDDaoInterface<FlashCard> {
    public Connection conn;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashCardRepo.class);


    public FlashCardRepo() {
//        String url = "jdbc:postgresql://localhost:5433/postgres?currentSchema=flash-card-demo";
//        String username = "postgres";
//        String password = "1234pass!";

        try {
            conn = ConnectionManager.getConnection();
            System.out.println(conn.getSchema());
        } catch (SQLException sqlException) {

            //System.out.println(sqlException.getMessage());
            LOGGER.error(sqlException.getMessage());
        }

    }


    // tested works fine
    @Override
    public int create(FlashCard flashcard) {
        String sql = "INSERT INTO flashcards (id, question, answer, creator_id) VALUES(default,?,?,?); ";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, flashcard.getQuestion());
            pstmt.setString(2, flashcard.getAnswer());
            pstmt.setInt(3, flashcard.getCreatorId());


            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();

            return rs.getInt("id");

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    // tested works fine
    @Override
    public List<FlashCard> getAll() {

        List<FlashCard> flashcards = new ArrayList<>();

        try {
            String sql = "SELECT * FROM flashcards";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                FlashCard flashcard = new FlashCard();

                flashcard.setId(rs.getInt("id"));
                flashcard.setQuestion(rs.getString("question"));
                flashcard.setAnswer(rs.getString("answer"));
                flashcard.setCreatorId(rs.getInt("creator_id"));

                flashcards.add(flashcard);
            }

            return flashcards;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    // tested works fine
    @Override
    public FlashCard getById(int id) {

        try {
            String sql = "SELECT * FROM flashcards WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            // Returns a flashcard therefore a new instance of a flashcard from database has to be created
            FlashCard flashcard = new FlashCard();

            while (rs.next()){
                flashcard.setId(rs.getInt("id"));
                flashcard.setQuestion(rs.getString("question"));
                flashcard.setAnswer(rs.getString("answer"));

            }

            return flashcard;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }


    //testd works fine
    //THIS ONLY UPDATES THE ANSWER!!!!
    @Override
    public FlashCard update(FlashCard flashCard) {

        try {

            String sql = "UPDATE flashcards SET answer = ? WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, flashCard.getAnswer());
            pstmt.setInt(2, flashCard.getId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            while (rs.next()) {
                flashCard.setAnswer(rs.getString("answer"));
            }

            return flashCard;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());


        }
        return null;
    }


    //returns false??? needs to fix
    @Override
    public boolean delete(FlashCard flashcard) {
        try {
            String sql = "DELETE FROM flashcards WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, flashcard.getId());
            ResultSet rs = pstmt.executeQuery();
            return true;


        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return false;
    }
}



