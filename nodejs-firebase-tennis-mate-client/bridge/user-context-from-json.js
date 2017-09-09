
let fs = require("fs");
const UserLocation = require("./../data-types/user-location");
const BasicUser    = require("./../data-types/basic-user");
const UserContext  = require("./../data-types/user-context");


function userContextFromJson (path) {

    return new Promise((resolve, reject) => {
        fs.readFile(path, {encoding: 'utf8'}, (err, data) => {
            if (err)
                return reject(err);

            let json;
            try {
                json = JSON.parse(data);
            }

            catch (e) {
                reject(e);
            }

            // building user context:

            let basicUser = new BasicUser(json.uid, json.firstName, json.lastName,
                json.email, json.level, json.photoUrl);

            let userLocation = new UserLocation(json.latitude, json.longitude,
                json.country, json.district, json.street, json.lastUpdatedDate);

            let userContext = new UserContext(basicUser, userLocation);
            resolve(userContext);

        });

    });
}

module.exports.userContextFromJson = userContextFromJson;