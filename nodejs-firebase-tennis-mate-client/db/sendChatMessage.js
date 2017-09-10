
var firebase = require("firebase");
let firebaseConf = require("../config/firebase-config").firebase;


let app = firebase.initializeApp(firebaseConf);
let database = firebase.database();

let messageTime = new Date().getTime();
let chatId = "aJsoLUT5ogVsLCWXMsKFXma8Sas6----aJsoLUT5ogVsLCWXMsKFXma8Sas6";
let message = {
    "message":"Fine!",
    "name":"Nir",
    "timestamp":messageTime,
    "uid":"aJsoLUT5ogVsLCWXMsKFXma8Sas6",
    "urlPict":"http://imageshack.com/a/img922/8400/2NpLrZ.jpg"
};

database.ref("chat")
    .child("lastMessages")
    .child(chatId)
    .set(message)
    .then( ()=> {console.log("success")})
    .catch( ()=>{console.log("failure")});

database.ref("chat")
    .child("messages")
    .child(chatId)
    .child(messageTime)
    .set(message)
    .then( ()=> {console.log("success")})
    .catch( ()=>{console.log("failure")});

