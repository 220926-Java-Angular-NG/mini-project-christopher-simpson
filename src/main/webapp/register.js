let backButton = document.getElementById("back-button");

backButton.addEventListener('click', (event)=> {
    event.preventDefault();// prevent refresh
    window.location.replace("index.html")//navigate to index page

});

let loginButton = document.getElementById("register");

loginButton.addEventListener('click', async(event) =>{
event.preventDefault();

let firstname1 = document.getElementById("firstname-sign-in").value;// gets the first name string
let lastname1 = document.getElementById("lastname-sign-in").value;// gets the last name
let email1 = document.getElementById("email-sign-in").value;// gets the email string
let password1 = document.getElementById("password-sign-in").value;// gets the password string
let zodiac1 = document.getElementById("zodiac-sign-in").value;// gets the zodiac string


// creating a JSON Object using the input from the user
// note that the keys for our objects must match the
// fields in our Models in the backend

let loginInfo = {

    firstname: firstname1,
    lastname: lastname1,
    email: email1,
    password:password1,
    zodiac:zodiac1

}

// turn our JSON object literal into JSON

let json = JSON.stringify(loginInfo);


try {

const raw_response = await fetch(`http://localhost:8080/user`,{
    method:"POST", // we add the http verb to be executed
    body:json   //
});

if(!raw_response.ok){
    throw new Error(raw_response.status);

}

raw_response.json().then( (data) =>{// receive the json data and
    
    let loggedInUser = JSON.stringify(data);

    localStorage.setItem("currentUser", loggedInUser)
    console.log(loggedInUser);
    
    //console.log(data)
});

setTimeout(()=>{

    window.location.replace("home.html")// navigate to home page after 3 sec
}, 3000)

}catch(error){
    console.log(error);
}

})