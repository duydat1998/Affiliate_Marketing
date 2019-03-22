const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

var admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/promotionCodeTrackings/{id}')
        .onWrite((change, context) => {

        // Grab the current value of what was written to the Realtime Database.
        // Only edit data when it is first created.
              if (change.before.exists()) {
                return null;
              }
              // Exit when the data is deleted.
              if (!change.after.exists()) {
                return null;
              }

        var eventSnapshot = change.after;
        console.log(eventSnapshot);
        var str1 = "Promotion code used is ";
        var str = str1.concat(eventSnapshot.child("promotionCode").val());
        console.log(str);

        var topic = "promotionCodeTracking";
        var payload = {
            data: {
                promotionCode: eventSnapshot.child("promotionCode").val(),
                timeOfUsing: eventSnapshot.child("timeOfUsing").val()
            }
        };

        // Send a message to devices subscribed to the provided topic.
        return admin.messaging().sendToTopic(topic, payload)
            .then(function (response) {
                // See the MessagingTopicResponse reference documentation for the
                // contents of response.
                console.log("Successfully sent message:", response);
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
        });