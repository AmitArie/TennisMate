
module.exports = class Time{

    constructor(){
        this.date = new Date();
    }

    toString(){
        return this.date.getFullYear() + "/" +
            (this.date.getMonth() + 1) + "/" +
            (this.date.getDate()) + " " +
            (this.date.getHours()) + "/" +
            (this.date.getMinutes()) + "/" +
            (this.date.getSeconds()) + "/";

    }
}