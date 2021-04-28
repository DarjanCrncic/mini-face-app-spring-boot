
const FriendsPageObject = {

	/////////////////// add friend request listener
	addFriendRequestListener: function(data, operation, buttonID, url) {
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
				error: function(data) {
					//alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// show pending requests function
	showPendingRequests: function() {

		$.ajax({
			url: 'CRUDRequest',
			dataType: 'json',
			data: { type: 'friend' },
			success: function(data) {
				if (data.data.length > 0) {
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
			},
			error: function(jqXHR, textStatus, errorThrown) {
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
			error: function(jqXHR, textStatus, errorThrown) {
				//alert('Something went wrong, try again later');
			}
		});
	}
}