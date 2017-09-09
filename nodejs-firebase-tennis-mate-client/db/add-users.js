const UserContext = require("./../data-types/user-context");
let GeoFire = require("geofire");



function addUserContext(database, userContext){

    let geoLocationRef  =   database.ref("/geo_location");
    let geoFire = new GeoFire(geoLocationRef);

    let uid = userContext._basicUser.userId;

    return new Promise( (resolve, reject) => {
        let sendRes = {geo_location:false, location_meta:false, users:false};

        geoFire.set(
            uid,
            [
                userContext._userLocation.latitude,
                userContext._userLocation.longitude
            ])
            .then( () =>
            {

                let locationMetaRef =   database.ref("/location_meta/"+uid);
                let usersRef =          database.ref("/users/"+uid);
                locationMetaRef.set(userContext._userLocation);
                usersRef.set(userContext._basicUser);

                resolve({success:true, error:null});
            }, (error) =>
            {
            reject({success:false, error:error});
        });


    });



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
}

module.exports.addUserContext = addUserContext;


