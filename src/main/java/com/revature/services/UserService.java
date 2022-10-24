package com.revature.services;



import com.revature.models.User;

import com.revature.repos.UserRepo;



import java.util.List;



public class UserService {



    private UserRepo userRepo;



    // Constructors

    public UserService() {

        userRepo = new UserRepo();

    }



    public UserService(UserRepo userRepo) {

        this.userRepo = userRepo;

    }



    // Methods

    // Create a user

    public int createUser(User user){

        return userRepo.create(user);

    }



    // Read All users

    public List<User> getAllUsers(){

        return userRepo.getAll();

    }



    // Read a user by ID

    public User getUserById(int id){

        return userRepo.getById(id);

    }



    // Update a user

    public User updateUser(User user){

        return userRepo.update(user);

    }



    // Delete a user

    public boolean deleteUser(User user){

        return userRepo.delete(user);

    }



    public boolean deleteUserById(int id){

        return userRepo.deleteByUserId(id);

    }


    public User loginUser(User user){
        return userRepo.loginUser(user);
    }
}