
const FriendsPageObject = {

	/////////////////// add friend request listener
	addFriendRequestListener: function(data, operation, buttonID) {
		$('#' + buttonID + '_' + data.ID).click(function() {
			
			let input = {
				operation: operation,
				friendID: data.ID,
				type: 'friend'
			}

			$.ajax({
				type: "POST",
				url: 'CRUDRequest',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						if (buttonID == "acceptRequest") {
							$('#requestDiv_' + input.friendID).empty();
							$('#requestDiv_' + input.friendID).remove();
							$('#friendsTable').html("");
							FriendsPageObject.displayFriendsTable();
						}
						if (buttonID == "sendRequest" ) {
							$('#sendRequest_' + input.friendID).remove();
						}
						if(buttonID == "declineRequest"){
							$('#requestDiv_' + input.friendID).remove();
						}

					} else {
						alert(data.message);
					}
				},
				error: function(data) {
					alert("Something went wrong, try again later");
				}
			})
		});
	},
	
	///// show pending requests function
	showPendingRequests: function() {
		
		$.ajax({
			url: 'CRUDRequest',
			dataType: 'json',
			data: {type: 'friend'},
			success: function(data) {
				if (data.status == 'success') {
					if(data.data.length > 0){
						$('#ajaxShowPendingRequests').append('<h2 id="#friendRequestTitle" class="blue-titles">Friend Requests:</h2>');
					}
					for (var i = 0; i < data.data.length; i++) {
						$('#ajaxShowPendingRequests').append('<div class="friendReqDiv" id="requestDiv_' + data.data[i].ID + '">\
					<div class="requestMessage">'+ data.data[i].NAME + ' wants to be your friend!</div><div class="friendReqButtonsDiv">\
					<button class="btn btn-outline-secondary" id="acceptRequest_'+ data.data[i].ID + '">Accept</button>\
					<button class="btn btn-outline-danger" id="declineRequest_'+ data.data[i].ID + '">Decline</button>\
					</div></div>');
						FriendsPageObject.addFriendRequestListener(data.data[i], 'accept', 'acceptRequest');
						FriendsPageObject.addFriendRequestListener(data.data[i], 'decline', 'declineRequest');
					}
				} else {
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		})
	},

	///// show all friends in table
	displayFriendsTable : function() {
		$.ajax({
			url: 'ShowFriends',
			dataType: 'json',
			success: function(data) {
				if (data.status == 'success') {
					$('#friendsTable').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 20%">Last Name</th > <th style="width: 20%">Username</th> </tr>');
					for (var i = 0; i < data.data.length; i++) {
						var row = $('<tr><td>' + data.data[i].NAME + '</td><td>' + data.data[i].SURNAME + '</td><td>' + data.data[i].USERNAME + '</td></tr>');
						$('#friendsTable').append(row);
					}
				} else {
					//alert(data.message);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		});
	}
}