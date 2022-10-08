package com.revature.controllers;

import com.revature.models.FlashCard;
import com.revature.models.User;
import com.revature.services.FlashCardService;
import com.revature.services.FlashCardService;
import io.javalin.http.Handler;

public class FlashCardController {

    FlashCardService service;

    public FlashCardController(){
        service = new FlashCardService();
    }
    public FlashCardController(FlashCardService flashCardService){
        this.service = flashCardService;
    }

    //create handlers

    public Handler createNewFlashCards = context -> {

        // grab the object from the request body (postman)
        // send the incoming user to our service, so it can
        // reach out to our repo layer and execute the request

        FlashCard flashCard = context.bodyAsClass(FlashCard.class); //change the jason from postman to an object
        int id = service.createFlashCard(flashCard);

        if(id>0){
            //valid number ( it represents the primary key )
            flashCard.setId(id);
            context.json(flashCard).status(200);
        }else{
            //something went wrong
            context.result("Flashcard not created").status(400);
        }


    };
    //read

    //all
    public Handler getAllFlashCards = context -> {

        context.json(service.getAllFlashCards());

    };

    //by id

    public Handler getFlashCardById = context -> {

        String param = context.pathParam("id");
        //we are going to wrap this logic in a try catch

        try{
            int id = Integer.parseInt(param);
            FlashCard flashCard = service.getFlashCardById(id);

            if(flashCard != null){
                context.json(flashCard).status(202);
            }else {
                context.result("Flashcard not found").status(400);
            }
        }catch(NumberFormatException nFMException){
            System.out.println(nFMException.getMessage());
        }
    };
    //update

    public Handler updateFlashCard = context -> {
        FlashCard flashCard = context.bodyAsClass(FlashCard.class); //model of what is on database
        flashCard = service.updateFlashCard(flashCard);

        if(flashCard != null){
            context.json(flashCard).status(202);
        }else{
            context.result("Could not update user").status(400);
        }
    };

    //delete
    public Handler deleteFlashCard = context -> {
        FlashCard flashCard = context.bodyAsClass(FlashCard.class);
        if(flashCard != null){
            context.status(200).json(service.deleteFlashCard(flashCard));
        }else{
            context.status(400).result("Could not delete flashcard");
        }
    };


}
