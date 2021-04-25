function prepareExecuteSearch(successFunction, backendURL) {

	$("#search").on('click', function() {
		searchFunction(successFunction, backendURL, 1, PostsPageObject.rowNumber);
		PostsPageObject.pageNumber = 1;
		$('#pageCounter').text(PostsPageObject.pageNumber);
		
	});
};

function searchFunction(successFunction, backendURL, pageNumber, rowNumber){
		let searchParams = [];
		let searchWords = [];
		let error = false;

		$('.select2-div').each(function(index, object) {
			const IdNumber = object.id.replace(/[^0-9]/g, '');

			searchParams.push(object.value);
			searchWords.push($("#searchItem_" + IdNumber).val());

			if ($("#searchItem_" + IdNumber).val() == "" && IdNumber > 0) error = true;
		});

		let selectedOperand = $("#select-logical-operand").val();

		let selectedPosition = $("#select-word-position").val();

		if (error) {
			alert('Invalid search parameters');
		} else {

			let searchInput = {
				searchParams: searchParams,
				searchWords: searchWords,
				logicalOperand: selectedOperand,
				wordPosition: selectedPosition,
				pageNumber: pageNumber,
				rowNumber: rowNumber
			};

			$.ajax({
				type: "POST",
				url: backendURL,
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(searchInput),
				success: function(data) {
					if (data.status == 'success') {
						successFunction(data);
					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			});
		}
}


function postSuccessFunction(data) {
	$('#ajaxShowVissiblePosts').html("");
	for (var i = 0; i < data.data.length; i++) {
		if (data.userID == data.data[i].CREATOR_ID) {

			$('#ajaxShowVissiblePosts').append(PostsPageObject.createPostHtml(data.data[i]));

			if (data.data[i].TYPE == "user")
				PostsPageObject.addDeleteEditPostButtonListener(data.data[i], "user");
			if (data.data[i].TYPE == "group")
				PostsPageObject.addDeleteEditPostButtonListener(data.data[i], "group");
				
			PostsPageObject.addCreateWordDocListener(data.data[i]);	

		} else {
			$('#ajaxShowVissiblePosts').append(PostsPageObject.createPostHtmlNotUser(data.data[i]));
		}
		PostsPageObject.addLikePostListener(data.data[i], "post");
		PostsPageObject.addCommentListener(data.data[i]);
		PostsPageObject.viewCommentListener(data.data[i]);
		PostsPageObject.getComments(data.data[i]);
	}
	PostsPageObject.paginationButtonsInit(data.data.length, data.more);
}

function friendSuccessFunction(data) {
	$('#ajaxShowSearchedPeople').html("");
	$('#ajaxShowSearchedPeople').append('<h2 id="searchResults" class="blue-titles">Search Results:</h2>');
	$('#ajaxShowSearchedPeople').append('<tr class="mainFriendsTableRow"> <th style="width: 28%">Name</th> <th style="width: 28%">Last Name</th> <th style="width: 28%">Username</th><th style="width: 16%"></th></tr>')
	for (var i = 0; i < data.data.length; i++) {
		var row = $('<tr><td>' + data.data[i].NAME + '</td><td>' + data.data[i].SURNAME + '</td>\
		<td>' + data.data[i].USERNAME + '</td>\
		<td style="padding: 0.3rem;"><button class="btn btn-outline-secondary request-button" id="sendRequest_'+ data.data[i].ID + '">Send Request</button></td></tr>');
		$('#ajaxShowSearchedPeople').append(row);
		FriendsPageObject.addFriendRequestListener(data.data[i], 'create', 'sendRequest');
	}
}

function groupsSuccessFunction(data) {
	$('#ajaxShowSearchedGroups').html("");
	$('#ajaxShowSearchedGroups').append('<h2 id="searchResults" class="blue-titles">Search Results:</h2>');
	$('#ajaxShowSearchedGroups').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 50%">Description</th > <th style="width: 20%">Owner</th><th style="width: 10%"></th></tr>');
	for (var i = 0; i < data.data.length; i++) {
		var row = $('<tr class="groups-table-row"><td>' + data.data[i].NAME + '</td><td>' + data.data[i].DESCRIPTION + '</td><td>' + data.data[i].OWNER + '</td>\
			<td style="padding: 0.3rem;"><button id="view_' + data.data[i].ID + '" class="btn btn-outline-secondary view-button">View</button></td></tr>');
		$('#ajaxShowSearchedGroups').append(row);
		GroupsPageObject.openGroupOnClick(data.data[i]);
	}
}




