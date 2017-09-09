
module.exports = class BasicUser {

    constructor (userId, firstName, lastName, email, level, photoUrl) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.level = level;
        this.photoUrl = photoUrl;
    }

}