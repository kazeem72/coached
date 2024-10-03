document.addEventListener("DOMContentLoaded", function(event) {

    //do work

    var socket = new SockJS("/websocket");
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    function onConnected() {
        // Subscribe to the Public Topic
        //alert(" About to subscribe to initiator")

        stompClient.subscribe("/topic/userTask", onMessageReceived);

        // Tell your username to the server
        stompClient.send(
            "/app/reviewer",
            {},
            JSON.stringify({"sender": "kazeem", "type": "JOIN"})
        );
    }
    function onError() {
        // Subscribe to the Public Topic
        //alert(" About to subscribe to initiator")

        stompClient.subscribe("/topic/initiator", onMessageReceived);

        // Tell your username to the server
        stompClient.send(
            "/topic/handshake/reviewer",
            {},
            JSON.stringify({ "sender": "kazeem", "type": "JOIN" })
        );
    }
    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        //alert(message + " Inside onMessage Received");

        console.log(message);

        const sidebar = document.getElementById("sidebar");

        const card = document.createElement("div");
        card.classList.add("card");
        card.style.width="80rem;"
        card.style.height="10rem";


        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");
        //cardBody.id="card1";

        const bodyTitle = document.createElement("h5");
        bodyTitle.classList.add("card-title");
        bodyTitle.innerText=message.id;

        const bodyParagraph = document.createElement("p");
        bodyParagraph.classList.add("card-text");
        bodyParagraph.innerText="Review Asset";

        cardBody.appendChild(bodyTitle);
        cardBody.appendChild(bodyParagraph);
        card.appendChild(cardBody)
        sidebar.appendChild(card);

    }

});
