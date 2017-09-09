const Time = require("./time");

module.exports  = class UserLocation{


    constructor(latitude, longitude, country, district, street, mLastUpdatedDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.district = district;
        this.street = street;
        this.lastUpdatedDate = mLastUpdatedDate;
    }


    defaultCtr(){
        this.latitude = 0.1;
        this.longitude = 0.1;
        this.country = "";
        this.district = "";
        this.street = "";
        this.lastUpdatedDate = new Time().toString();
    }
};