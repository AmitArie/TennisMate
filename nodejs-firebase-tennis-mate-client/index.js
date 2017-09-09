

var firebase = require("firebase");
let firebaseConf = require("./config/firebase-config").firebase;
const bridge = require("./bridge/user-context-from-json");
const addUsers = require("./db/add-users");
const fs = require('fs');

let app = firebase.initializeApp(firebaseConf);
let database = firebase.database();


fs.readdir(__dirname + "/db/jsons", (err, dir) => {
    if(err)
        throw err;

    let finishFlag = dir.length;

    dir.forEach( (file) => {

        let fullPath = __dirname + "/db/jsons/" + file;
        console.log("Working on: " + fullPath);

        bridge.userContextFromJson(fullPath)
            .then( (userContext) => {

                addUsers.addUserContext(database, userContext)
                    .then( (res) => {
                        console.log(res);
                        finishFlag = finishFlag - 1;

                        if(  finishFlag == 0  )
                            app.delete();
                    })
                    .catch( (err) => {
                        finishFlag = finishFlag - 1;

                        if(  finishFlag == 0  )
                            app.delete();
                    });
            })
            .catch( (err) => {
                console.log(err);
            });

    });
});





// let app = firebase.initializeApp(firebaseConf);
//
// let database = firebase.database();
// let geoLocationRef = database.ref("/geo_location");


// let geoFire = new GeoFire(geoLocationRef);
//
//
// geoFire.get("ZxQECJWAmhOmQ2QqBMJXhdfhSFU2").then(function(location) {
//     if (location === null) {
//         console.log("Provided key is not in GeoFire");
//     }
//     else {
//         console.log("Provided key has a location of " + location);
//     }
//     app.delete();
// }, function(error) {
//     console.log("Error: " + error);
//     app.delete();
// });


// let geoLocationRef = database.ref("/geo_location").once("value")
//     .then( (snapshot) => {
//         console.log(snapshot.val());
//         app.delete();
//     })
//     .catch( (e) => {
//         throw e;
//     });