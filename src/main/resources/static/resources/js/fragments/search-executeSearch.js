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
				contentType: "application/json",
				dataType: "json",
				data: JSON.stringify(searchInput),
				success: function(data) {
					successFunction(data);
				},
				error: function() {
					//alert("Something went wrong, try again later");
				}
			});
		}
}


function postSuccessFunction(data, groupId) {
	
	$('#ajaxShowVissiblePosts').html("");
	
	const limit = (data.length >= PostsPageObject.rowNumber) ? PostsPageObject.rowNumber : data.length; 
	
	for (var i = 0; i < limit; i++) {
		if(data[i].type.id == 2) {
			data[i].likes = data[i].likes.length;
		}
		
		if (MainObject.user.id == data[i].creator.id) {

			$('#ajaxShowVissiblePosts').append(PostsPageObject.createPostHtml(data[i]));
			PostsPageObject.addEditPostButtonListener(data[i]);
			if(data[i].type.id == 1) {
				PostsPageObject.addDeletePostButtonListener(data[i]);
			} else {
				PostsPageObject.addDeleteGroupPostButtonListener(data[i], groupId);
			}
			
		} else {
			$('#ajaxShowVissiblePosts').append(PostsPageObject.createPostHtmlNotUser(data[i]));
		}
		postIdToCreator.set(data[i].id, data[i].creator.id);
			
		PostsPageObject.addLikePostListener(data[i]);
		PostsPageObject.addCommentListener(data[i]);
		PostsPageObject.viewCommentListener(data[i]);
		PostsPageObject.getComments(data[i]);
				
	}
	PostsPageObject.paginationButtonsInit(data.length);
}

function friendSuccessFunction(data) {
	$('#ajaxShowSearchedPeople').html("");
	$('#ajaxShowSearchedPeople').append('<h2 id="searchResults" class="blue-titles">Search Results:</h2>');
	$('#ajaxShowSearchedPeople').append('<tr class="mainFriendsTableRow"> <th style="width: 28%">Name</th> <th style="width: 28%">Last Name</th> <th style="width: 28%">Username</th><th style="width: 16%"></th></tr>')
	for (var i = 0; i < data.length; i++) {
		var row = $('<tr><td>' + data[i].name + '</td><td>' + data[i].surname + '</td>\
		<td>' + data[i].username + '</td>\
		<td style="padding: 0.3rem;"><button class="btn btn-outline-secondary request-button" id="sendRequest_'+ data[i].id + '">Send Request</button></td></tr>');
		$('#ajaxShowSearchedPeople').append(row);	
		FriendsPageObject.addFriendRequestListener(data[i], 'sendRequest', 'friends/send');
		
	}
}

function groupsSuccessFunction(data) {
	$('#ajaxShowSearchedGroups').html("");
	$('#ajaxShowSearchedGroups').append('<h2 id="searchResults" class="blue-titles">Search Results:</h2>');
	$('#ajaxShowSearchedGroups').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 50%">Description</th > <th style="width: 20%">Owner</th><th style="width: 10%"></th></tr>');
	for (var i = 0; i < data.length; i++) {
		var row = $('<tr class="groups-table-row"><td>' + data[i].name + '</td><td>' + data[i].description + '</td><td>' + data[i].owner.name + " " + data[i].owner.surname + '</td>\
			<td style="padding: 0.3rem;"><button id="view_' + data[i].id + '" class="btn btn-outline-secondary view-button">View</button></td></tr>');
		$('#ajaxShowSearchedGroups').append(row);
		GroupsPageObject.openGroupOnClick(data[i]);
	}
}




