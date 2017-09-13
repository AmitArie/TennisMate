

var firebase = require("firebase");
let firebaseConf = require("./config/firebase-config").firebase;
const bridge = require("./bridge/user-context-from-json");
const addUsers = require("./db/add-users");
const fs = require('fs');

let app = firebase.initializeApp(firebaseConf);
let database = firebase.database();


let lastMessageRef = database.ref("chat").child("lastMessages");

lastMessageRef.on("child_added", (req)=>{

    if( ! req)
        return;

    let key = req.getKey();
    let value = req.value();
    console.log(value);

});




lastMessageRef.on("child_changed", (req) =>{

    if( ! req)
        return;

    let key = req.getKey();
    let value = req.value();
    console.log(value);
});


