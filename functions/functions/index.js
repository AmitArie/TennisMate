const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);



exports.newChats = functions.database.ref("/chat/lastMessages/{chatId}")
    .onCreate(sendNotification);


exports.existingChats = functions.database.ref("/chat/lastMessages/{chatId}")
    .onUpdate(sendNotification);



function sendNotification ( event ){

    const chatId = event.params.chatId;
    const lastMessageObj  = event.data.val();

    const senderUid = lastMessageObj.uid;
    const receiverUid = extractOtherId(chatId, senderUid);

    /*
     Creating notification payload:
     */

    let payload = {
        notification: {
            title: lastMessageObj.name + "",
            body: lastMessageObj.message,
            icon: 'ic_notification_tennis',
            sound: "default"
        }
    };


    console.log(` the pay load is: ${payload}`);
    // Sending notification to the receiver:

    admin.database().ref(`/accessTok/${receiverUid}`)
        .once('value')
        .then( (dataSnapshot)=>{
            let accessTok = dataSnapshot.val();

            console.log(`access token of the other user: ${accessTok}`);

            admin.messaging().sendToDevice(accessTok, payload)
                .then(function(response) {
                    // See the MessagingDevicesResponse reference documentation for
                    // the contents of response.
                    console.log("Successfully sent message:", response);
                })
                .catch(function(error) {
                    console.log("Error sending message:", error);
                });
        });

}


/**
 *  This functon extracts the other uid fro the chatId.
 * @param chatId String with format: x + separator + y
 * @param uid x xor y.
 * @return
 */
function  extractOtherId (chatId, uid){
    let separator = "----";

    if( chatId.startsWith(uid) ){  // chatId = uid + separator + y
        return chatId.split(separator)[1];
    }
    else{ // chatId = y + separator + uid
        return chatId.split(separator)[0];
    }
}