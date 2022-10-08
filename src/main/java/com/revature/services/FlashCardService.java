package com.revature.services;

import com.revature.models.FlashCard;
import com.revature.repos.FlashCardRepo;


import java.util.List;

public class FlashCardService {

    private FlashCardRepo flashCardRepo;

    //creating a new instance of our UserRepo
    public FlashCardService(){
        flashCardRepo = new FlashCardRepo();
    }

    // here we are passing in an existing UserRepo
    public FlashCardService(FlashCardRepo flashCardRepo){
        this.flashCardRepo = flashCardRepo;
    }

    // create

    public int createFlashCard(FlashCard flashCard){
        return flashCardRepo.create(flashCard);
    }

    // read all
    public List<FlashCard> getAllFlashCards(){
        return flashCardRepo.getAll();
    }

    // readById
    public FlashCard getFlashCardById(int id){
        return flashCardRepo.getById(id);
    }

    // update
    public FlashCard updateFlashCard(FlashCard flashCard){
        return flashCardRepo.update(flashCard);
    }

    // delete
    public boolean deleteFlashCard(FlashCard flashCard){
        return flashCardRepo.delete(flashCard);
    }







}
