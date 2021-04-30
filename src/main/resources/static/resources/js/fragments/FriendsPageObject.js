
const FriendsPageObject = {

	/////////////////// add friend request listener
	addFriendRequestListener: function(data, buttonID, url) {
		$('#' + buttonID + '_' + data.id).on('click', function() {
			let input = {
				faceFriendId: data.id,
			}

			$.ajax({
				type: "POST",
				url: url,
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(data) {
					if (buttonID == "acceptRequest") {
						$('#requestDiv_' + input.faceFriendId).empty();
						$('#requestDiv_' + input.faceFriendId).remove();
						$('#friendsTable').html("");
						FriendsPageObject.displayFriendsTable();
					}
					if (buttonID == "sendRequest") {
						$('#sendRequest_' + data.faceFriendId).remove();
					}
					if (buttonID == "declineRequest") {
						$('#requestDiv_' + input.faceFriendId).remove();
					}
				},
				error: function() {
					//alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// show pending requests function
	showPendingRequests: function() {

		$.ajax({
			url: 'friends/requests',
			dataType: 'json',
			contentType: "application/json",
			success: function(data) {
				if (data.length > 0) {
					$('#ajaxShowPendingRequests').append('<h2 id="#friendRequestTitle" class="blue-titles">Friend Requests:</h2>');
				}
				for (var i = 0; i < data.length; i++) {
					$('#ajaxShowPendingRequests').append('<div class="friendReqDiv" id="requestDiv_' + data[i].id + '">\
					<div class="requestMessage">'+ data[i].name + ' wants to be your friend!</div><div class="friendReqButtonsDiv">\
					<button class="btn btn-outline-secondary" id="acceptRequest_'+ data[i].id + '">Accept</button>\
					<button class="btn btn-outline-danger" id="declineRequest_'+ data[i].id + '">Decline</button>\
					</div></div>');
					FriendsPageObject.addFriendRequestListener(data[i], 'acceptRequest');
					FriendsPageObject.addFriendRequestListener(data[i], 'declineRequest');
				}
			},
			error: function() {
				//alert('Something went wrong, try again later');
			}
		})
	},

	///// show all friends in table
	displayFriendsTable: function() {
		$.ajax({
			url: 'friends/find',
			dataType: 'json',
			contentType: "application/json",
			success: function(data) {
				$('#friendsTable').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 20%">Last Name</th > <th style="width: 20%">Username</th> </tr>');
				for (var i = 0; i < data.length; i++) {
					var row = $('<tr><td>' + data[i].name + '</td><td>' + data[i].surname + '</td><td>' + data[i].username + '</td></tr>');
					$('#friendsTable').append(row);
				}
			},
			error: function() {
				//alert('Something went wrong, try again later');
			}
		});
	}
}