
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
	$('#' + receiver).append('<div style="color: black" class="chatline">You: ' + input.content + '</div>');
}

$.ajax({
	url: 'profile/info',
	dataType: 'json',
	contentType: 'application/json',
	success: function(data) {
		// on login connect the user to the websocket endpoint on server
		var websocket = new WebSocket("ws://localhost:8080/chat");

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
						$('#' + userArr[i]).hide();
					}
					$('#usersDiv').prepend('<button id="switchChat_' + userArr[i] + '" class="btn btn-outline-primary onlineUsersButton">\
						<img class="commentImage" id="chatImage_'+ userArr[i] +'"/>' + userArr[i] + '</button>');
						
					getUserImg(userArr[i]);	
						
					$('#switchChat_' + userArr[i]).click(function() {
						$('.chatWindows').hide();
						$('#' + userArr[i]).show();
						$('#hiddenInput').val(userArr[i]);
					});
				}
			}

			// regular message, show the apropriate message window and append the message content
			if (JSON.parse(message.data).init == "false") {
				
				if(!showing) hideShowChat();
				
				$('.chatWindows').hide();				
				if (JSON.parse(message.data).sender != JSON.parse(message.data).receiver)
					$(('#' + JSON.parse(message.data).sender)).append('<div class="chatline">' + JSON.parse(message.data).sender + ': ' + JSON.parse(message.data).content + '</div>');
				$(('#' + JSON.parse(message.data).sender)).show();
				$('#hiddenInput').val(JSON.parse(message.data).sender);
			}

			// used when onClose event is fired, receive a message to remove specific user button and chat window
			if (JSON.parse(message.data).init == "delete") {
				$('#switchChat_' + JSON.parse(message.data).content).remove();
				//$('#' + JSON.parse(message.data).content).remove();
			}
		}
		// send notification on creating new websocket
		websocket.onopen = function() {
			sendMessage(websocket, data.username, "init", 'true');
		};

		$('#sendMessageButton').click(function() {
			sendMessage(websocket, "", $('#hiddenInput').val(), 'false');
		});

		$('#messageText').keypress(function(e) {
			if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
				sendMessage(websocket, "", $('#hiddenInput').val(), 'false');
			} 
		});

		$('#logout').click(function() {
			websocket.close();
		});
	},
	error: function() {
		alert(data.message);
	}
});

$('#topHolderDiv').css("height", "calc(30rem - " + $('#bottomHolderDiv').height() + "px - 11px) ")
var showing = true;

function hideShowChat() {
	if(showing){
		var height =  -$("#tertiary").height() + $('#hideShowChatButton').height();
		$('#tertiary').css("bottom", height);
		showing = false;
	}else{
		$('#tertiary').css("bottom", 0);
		showing = true;
	}
}


function getUserImg(username) {
	$.ajax({
		url: 'profile/image/username/' + username,
		success: function(data) {
			$("#chatImage_"+ username).attr("src", "data:image/jpg;base64," + data);
		},
		error: function() {
			//alert(data.message);
		}
	});
}

