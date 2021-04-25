
function sendMessage(websocket, sender, receiver, init) {
	// basic json message structure, can contain additional fields like onlineUsers
	let input = {
		content: $('#messageText').val(),
		sender: sender,
		receiver: receiver,
		init: init
	}
	websocket.send(JSON.stringify(input));
	
	// after sending a message to a specific user, clear the input hide all other chat windows and open a chat window made for that user
	$('#messageText').val("");
	$('.chatWindows').hide();
	
	// hidden input is used to save username of the user client is currently chatting with 
	$('#hiddenInput').val(receiver);
	$('#' + receiver).show();
	$('#' + receiver).append('<p style="color: black" class="chatline">You: '+input.content + '</p>');
}

$.ajax({
	url: 'HomePage',
	dataType: 'json',
	success: function(data) {
		if (data.status == 'success') {
			
			// on login connect the user to the websocket endpoint on server
			var websocket = new WebSocket("ws://localhost:8080/MiniFaceApp/chat");
			
			websocket.onmessage = function processMessage(message) {
				
				// message that contains no content, used only for notifying other users of a new user on server
				if (JSON.parse(message.data).init == "true") {
					$('#usersDiv').html("");
					const userArr = Array.from(JSON.parse(message.data).onlineUsers);
					
					// for each new user from current online users create a new chat window with the new users title, 
					// hide it and add a button listener that will be used to show that users chat window and change the activly chatting user to selected user
					for (let i = 0; i < userArr.length; i++) {
						if ($('#' + userArr[i]).length) { } else {
							$('#messageDiv').append('<div id="' + userArr[i] + '" class="chatWindows"><h5>' + userArr[i] + '</h5><hr class="horizontalSep"></div>');
							$('#'+userArr[i]).hide();
						}
						$('#usersDiv').prepend('<button id="switchChat_' + userArr[i] + '" class="btn btn-outline-primary onlineUsersButton">' + userArr[i] + '</button>');

						$('#switchChat_' + userArr[i]).click(function() {
							$('.chatWindows').hide();
							$('#' + userArr[i]).show();
							$('#hiddenInput').val(userArr[i]);
						});
					}
				} 
				
				// regular message, show the apropriate message window and append the message content
				if(JSON.parse(message.data).init == "false"){
					$(('#' + JSON.parse(message.data).sender)).show();
					if(JSON.parse(message.data).sender != JSON.parse(message.data).receiver)
						$(('#' + JSON.parse(message.data).sender)).append('<p class="chatline">' +JSON.parse(message.data).sender+': '+ JSON.parse(message.data).content + '</p>');
					$('#hiddenInput').val(JSON.parse(message.data).sender);
				}
				
				// used when onClose event is fired, receive a message to remove specific user button and chat window
				if(JSON.parse(message.data).init == "delete"){
					$('#switchChat_'+JSON.parse(message.data).content).remove();
					//$('#' + JSON.parse(message.data).content).remove();
				}
			}
			// send notification on creating new websocket
			websocket.onopen = function() {
				sendMessage(websocket, data.data.username, "init", 'true');
			};

			$('#sendMessageButton').click(function() {
				sendMessage(websocket, "", $('#hiddenInput').val(), 'false');
			});
			
			$('#logout').click(function(){
				websocket.close();
			});
		}
	},
	error: function() {
		alert(data.message);
	}
});

