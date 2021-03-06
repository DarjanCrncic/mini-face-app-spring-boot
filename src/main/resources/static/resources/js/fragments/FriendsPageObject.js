
const FriendsPageObject = {

	/////////////////// add friend request listener
	addFriendRequestListener: function(data, buttonID, url) {
		$('#' + buttonID + '_' + data.id).on('click', function() {

			let input = {};
			if (buttonID == "sendRequest") {
				input = {
					faceFriendId: data.id
				}
			} else {
				input = {
					faceUserId: data.id
				}
			}

			$.ajax({
				type: "POST",
				url: url,
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(data) {
					if (buttonID == "acceptRequest") {
						$('#requestDiv_' + input.faceUserId).empty();
						$('#requestDiv_' + input.faceUserId).remove();
						$('#friendsTable').html("");
						FriendsPageObject.displayFriendsTable();
					}
					if (buttonID == "sendRequest") {
						$('#sendRequest_' + data.faceFriendId).remove();
					}
					if (buttonID == "declineRequest") {
						$('#requestDiv_' + input.faceUserId).remove();
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
					FriendsPageObject.addFriendRequestListener(data[i], 'acceptRequest', 'friends/accept');
					FriendsPageObject.addFriendRequestListener(data[i], 'declineRequest', 'friends/decline');
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
				$('#friendsTable').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 20%">Last Name</th>\
				 <th style="width: 20%">Username</th><th style="width: 8%;"></th></tr>');

				for (var i = 0; i < data.length; i++) {
					var row = $('<tr><td>' + data[i].name + '</td><td>' + data[i].surname + '</td><td>' + data[i].username + '</td><td>\
					<button class="btn btn-outline-secondary" id="viewProfile_'+ data[i].id + '">View Profile</button></td></tr>');

					$('#friendsTable').append(row);
					FriendsPageObject.viewProfileListener(data[i].id);
				}
			},
			error: function(message) {
				if (message != null) alert(message);
			}
		});
	},

	viewProfileListener: function(id) {
		$('#viewProfile_' + id).click(function() {
			$.ajax({
				url: 'profile/friend/' + id,
				dataType: 'json',
				success: function(data) {
					console.log(data)
					MainObject.loadSecondary("resources/html/fragments/friendPage.html", false, () => {
						MainObject.hidePrimary();

						$('#profilePageTitle').text(data.name + '\'s Profile:')
						$("#profilePictureOnProfile").attr("src", "data:image/jpg;base64," + data.image);
						$('#profileName').text(data.name);
						$('#profileSurname').text(data.surname);
						$('#profileFullName').text(data.name + " " + data.surname);
						$('#profileAge').text((data.age != 0) ? data.age : "");
						$('#profileCity').text(data.city);
						$('#profileCountry').text(data.country);
						$('#profileGender').text(data.gender);
					});
				},
				error: function() {

				}
			});
		})
	}
}