"use strict";
document.addEventListener("DOMContentLoaded", function(event) {
    var usernamePage = document.querySelector("#username-page");
    var chatPage = document.querySelector("#chat-page");
// var usernameForm = document.querySelector("#usernameForm");
    var messageForm = document.querySelector("#messageForm");
    var messageInput = document.querySelector("#message");
    var messageArea = document.querySelector("#messageArea");
    var connectingElement = document.querySelector(".connecting");

    var fullNameInput = document.querySelector("#fullName");


    var username = fullNameInput.value;
    var stompClient = null;

    var password = null;

    var colors = [
        "#2196F3",
        "#32c787",
        "#00BCD4",
        "#ff5652",
        "#ffc107",
        "#ff85af",
        "#FF9800",
        "#39bbb0",
        "#fcba03",
        "#fc0303",
        "#de5454",
        "#b9de54",
        "#54ded7",
        "#54ded7",
        "#1358d6",
        "#d611c6",
    ];
    var socket = new SockJS("/coached/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();

    function connect(event) {
        //alert('event here==='+event);
        //if (username) {
        //Enter your password
        //if (password == "hello") {
        // usernamePage.classList.add("hidden");
        // chatPage.classList.remove("hidden");


    }

    function onConnected() {
        // Subscribe to the Public Topic
        console.log(' Suucessfully connected....');
        stompClient.subscribe("/topic/public", onMessageReceived);

        // Tell your username to the server
        stompClient.send(
            "/coached/app/chat.register",
            {},
            JSON.stringify({ sender: username, type: "JOIN" })
        );
        connectingElement.classList.add("hidden");
    }
    function onError(error) {
        console.log(' Error Here.............................'+ error);
        connectingElement.textContent =
            "Could not connect to WebSocket! Please refresh the page and try again or contact your administrator.";
        connectingElement.style.color = "red";
    }

    function send(event) {
        //alert(' Sending in send event..... '+messageInput.value.trim());
        var messageContent = messageInput.value.trim();
        //alert('stompClient==='+ stompClient);
        if (messageContent && stompClient) {
            var chatMessage = {
                sender: username,
                content: messageInput.value,
                type: "CHAT",
            };
            //alert(' chatMessage content =='+chatMessage.content);

            stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
            messageInput.value = "";
        }
        event.preventDefault();
    }

    /**
     * Handles the received message and updates the chat interface accordingly.
     * param {Object} payload - The payload containing the message data.
     */
    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);

        // alert('content of message received ==='+ message.content + ' sender=='+message.sender
        // +' the type=='+message.type);
        //message.sender=[[${}]];

        var messageElement = document.createElement("li");

        if (message.type === "JOIN") {
            messageElement.classList.add("event-message");
            message.content = message.sender + " joined!";
        } else if (message.type === "LEAVE") {
            messageElement.classList.add("event-message");
            message.content = message.sender + " left!";
        } else {
            messageElement.classList.add("chat-message");

            var avatarElement = document.createElement("i");
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style["background-color"] = getAvatarColor(message.sender);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement("span");
            var usernameText = document.createTextNode(message.sender);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
            // * update
            usernameElement.style["color"] = getAvatarColor(message.sender);
            //* update end
        }

        var textElement = document.createElement("p");
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);
        // * update
        if (message.sender === username) {
            // Add a class to float the message to the right
            messageElement.classList.add("own-message");
        } // * update end
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }

    function getAvatarColor(messageSender) {
        var hash = 0;
        for (var i = 0; i < messageSender.length; i++) {
            hash = 31 * hash + messageSender.charCodeAt(i);
        }

        var index = Math.abs(hash % colors.length);
        return colors[index];
    }

//usernameForm.addEventListener("submit", connect, true);
    messageForm.addEventListener("submit", send, true);
});
