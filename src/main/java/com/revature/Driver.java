package com.revature;
import com.revature.controllers.FlashCardController;
import com.revature.controllers.UserController;
import com.revature.services.FlashCardService;
import com.revature.services.UserService;
import io.javalin.Javalin;

public class Driver {

    public static void main(String[] args){

        Javalin app = Javalin.create( config -> {
            config.enableCorsForAllOrigins();
        }).start(8080);

        UserService userService = new UserService();
        UserController userController = new UserController(userService);

        app.get("/users", userController.getAllUsers);
        app.get("/user/{id}", userController.getUserById);
        app.post("/user", userController.createNewUser);
        app.put("/user",userController.updateUser);
        app.delete("/user", userController.deleteUser);
        app.delete("/user/{id}", userController.deleteUserById);
        app.post("/login", userController.loginUser);

        FlashCardService flashCardService = new FlashCardService();
        FlashCardController flashCardController = new FlashCardController(flashCardService);

        app.get("/flashcards", flashCardController.getAllFlashCards);
        app.get("/flashcard/{id}", flashCardController.getFlashCardById);
        app.post("/flashcard", flashCardController.createNewFlashCards);
        app.put("/flashcard", flashCardController.updateFlashCard);
        app.delete("/flashcard", flashCardController.deleteFlashCard);

        /*
        public: Available anywhere
        protected: Access within the same package and the class' subclasses
        default: Access within the same package
        private: Access only within the same class
         */

        //create a user
 //       User user1 = new User("Christopher", "Simpson", "christopher738@revature.net", "nahnahnha");
 //   User user2 = new User("Brandon", "Pinkerton", "brandon.p@rev.com","password");

        // add the user to the database
//        int user2Id = userRepo.create(user2);
    //    System.out.println(user2Id);

//        List<User> dbUsers = userRepo.getAll();
//
//        for(User user:dbUsers){
//            System.out.println(user.getFirstname());
////            System.out.println(user.toString());
//        }
//        User user1fromDB = userRepo.getById(1);
//        System.out.println(user1fromDB.getId());
//
//        user1fromDB.setEmail("christopher738@revature.net");
//        User updatedUser = userRepo.update(user1fromDB);
//        System.out.println("this is the updated email -" + updatedUser.getEmail());


    }
}
