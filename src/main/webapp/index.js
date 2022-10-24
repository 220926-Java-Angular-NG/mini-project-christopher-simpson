//access  my sign up button
//change the window to point at the register.html page for a user to sign up

let signUpButton = document.getElementById("sign-up");

signUpButton.addEventListener('click', (event) =>{
    event.preventDefault();// prevent page from refreshing once clicked
    window.location.replace("register.html")// navigate to register page

});

//we are taking in the email and passwrod
// sending a requeset to our api (localhost)
// then save our current user to our localStorage
// tell our webpage to point to our home.html page

let loginButton = document.getElementById("login");

loginButton.addEventListener('click', async(event) =>{
event.preventDefault();

let email1 = document.getElementById("email-sign-in").value;// gets the email string
let password1 = document.getElementById("password-sign-in").value;// gets the password string


// creating a JSON Object using the input from the user
// note that the keys for our objects must match the
// fields in our Models in the backend

let loginInfo = {
    email: email1,
    password:password1

}

// turn our JSON object literal into JSON

let json = JSON.stringify(loginInfo);


try {

const raw_response = await fetch(`http://localhost:8080/login`,{
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
})

setTimeout(()=>{

    window.location.replace("home.html")// navigate to home page after 3 sec
}, 3000)

}catch(error){
    console.log(error);
}

})