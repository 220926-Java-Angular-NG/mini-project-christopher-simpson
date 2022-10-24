

let currentUser = localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);// making a string back into the json version
console.log(currentUser);

let welcomeLabel = document.getElementById("welcome-label"); // 
welcomeLabel.innerHTML = `Welcome ${currentUser.firstname}! Get your Horoscope today`;

let userdata = document.getElementById("user-status");
userdata.innerHTML = `Current User logged in : ${currentUser.firstname} ${currentUser.lastname} <br> User Email:  ${currentUser.email}`

// let submitButton = document.getElementById("submit-button");

// //get the value from our user input field (ie the text box)
// let inputValue = document.getElementById("").value;

// send a request to the pokemon api
// to do that we have to wrap our code in a try catch
var zod = "";
zod = currentUser.zodiac.toLowerCase();


async function getHoroscopeFromFetch(){
try{

    // this fetch method implicityly returns a Promise
    const raw_response = await fetch(`http://sandipbgt.com/theastrologer/api/horoscope/pisces/today/`);

    if(!raw_response.ok){
      //  throw new Error((raw_response).status)
      alert(`Error Status: ${raw_response.status}`);
    }

    const json_data = await raw_response.json();

    console.log(json_data);
        // here we are calling our 'getHoroscopeFromFetch' async function
        // this helper function is specifically to manipulate our DOM
        // and passing in the 'json-data' we recieve from our api call
       zodGetter(json_data);
     

}catch(error){
    console.log(error);

}
}

getHoroscopeFromFetch();

function zodGetter(json_data){
    let input = document.getElementById("input");
    input.innerHTML = `Horoscope: ${json_data.horoscope}`;
}




let logOutButton = document.getElementById("log-out");

logOutButton.addEventListener('click', (event) =>{
    event.preventDefault();// prevent page from refreshing once clicked
    window.location.replace("index.html")// navigate to register page

});

let button = document.getElementById("mood-button");

//add an event listener to our button

button.addEventListener('click', async(event)=>{
    event.preventDefault();
    //get the value from our json string (ie the api)
    //let inputValue = document.getElementById("mood").value;

    // send a request to the pokemon api
    // to do that we have to wrap our code in a try catch
    try{

        // this fetch method implicitly returns a Promise
        const raw_response = await fetch(`http://sandipbgt.com/theastrologer/api/horoscope/pisces/today`);

        if(!raw_response.ok){
          //  throw new Error((raw_response).status)
          alert(`Error Status: ${raw_response.status}`);
        }

        const json_data = await raw_response.json();

        setMood(json_data.meta.mood);
        alert (`Your Mood is :" ${json_data.meta.mood}`)
        console.log(json_data);
       
            // here we are calling our 'addPokemonToPage' helper function
            // this helper function is specifically to manipulate our DOM
            // and passing in the 'json-data' we recieve from our api call
            // addPokemonToPage(json_data);

    }catch(error){
        console.log(error);

    }
})
// setMood(json_data);
async function setMood(newMood){
    
// creating a JSON Object using the input from api
// note that the keys for our objects must match the
// fields in our Models in the backend

let updateMood = {
    id: currentUser.id,
    mood:newMood
}

// turn our JSON object literal into JSON

let json = JSON.stringify(updateMood);

try {

const raw_response = await fetch(`http://localhost:8080/user`,{
    method:"PUT", // we add the http verb to be executed
    body:json   //
});

if(!raw_response.ok){
    throw new Error(raw_response.status);
}
raw_response.json().then((data)=>{
    let loggedInUser = JSON.stringify(data);
    localStorage.setItem("currentUser", loggedInUser);
    console.log("Updating Mood");
})

}catch(error){
    console.log(error);
}

}


// submitButton.addEventListener('click', async(event) => {
//     event.preventDefault();

//     let questionText = document.getElementById("question").value;
//     let answerText = document.getElementById("answer").value;

//     //console.log(`Question: ${questionText} - Answer: ${answerText}`)

//     let cardObject = {
//         question:questionText,
//         answer:answerText,
//         creatorId:currentUser.id
//     }

//     let json = JSON.stringify(cardObject);
    
//     //send the json string to the backend
//     try{
//         let raw_response = await fetch(`http://localhost:8080/flashcard`,{
//         method:"POST",
//         body:json
//     })

//     if(!raw_response.ok){
//         throw new Error(raw_response.status)
//     }

//     alert("Your card was created!")
//     let questionBox = document.getElementById("question");
//     questionBox.value = "";
//     let answerBox = document.getElementById("answer");
//     answerBox.value = "";


//     }catch(error){
//         //throw new Error()
//         console.log(error)
//     }
// })
// //// How to view all Cards in browser /////////////////////
// let viewAllButton = document.getElementById("view-all");
// viewAllButton.addEventListener('click', async(event) => {

//     event.preventDefault();

//     try{
//         //fetch sends a get request by dfault unless directed to do otherwise
//         let raw_response = await fetch(`http://localhost:8080/flashcards`);

//         if(!raw_response.ok){
//             throw new Error(raw_response.status);
//         }

//         raw_response.json().then( (data) => {

//             // create a helper method that will use the json data from the request to manipulate the dom
//             // console.log(data); //print the array we get back from our http request

//             addFlashcardToPage(data);
//         })
//     }catch (error){
//         console.log(error)
//     }
// })
////////
// function addFlashcardToPage(cardArray){

//     let cardButtonP = document.getElementById("card-button");
//     let br = document.createElement('br');

//     cardButtonP.append(br);
//     cardButtonP.append(br);

//     for(let card of cardArray){
//         let cardElement = document.createElement("h3");
//         cardElement.innerHTML = `Question: ${card.question} - Answer: ${card.answer}`
//         cardButtonP.appendChild(cardElement);
//     }

// }