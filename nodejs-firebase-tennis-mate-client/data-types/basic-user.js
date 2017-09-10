
module.exports = class BasicUser {

    constructor (uid, firstName, lastName, email, level, photoUrl) {

        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.level = level;
        this.photoUrl = photoUrl;
    }

}
