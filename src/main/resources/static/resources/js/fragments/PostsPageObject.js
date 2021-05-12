
const PostsPageObject = {

	pageNumber: 1,
	rowNumber: 5,

	/////////////////////// show all vissible posts on page load // !!!!!!!!!! NOT USED ANYMORE, EVERYTHING THROUGH SEARCH !!!! USED IN GROUP POSTS
	showAllVissiblePosts: function(groupId) {
		$.ajax({
			url: 'groups/posts/'+groupId,
			dataType: 'json',
			contentType: "application/json",
			type: "GET",
			success: function(data) {
				postSuccessFunction(data.posts, groupId)
			},
			error: function() {
				alert('Something went wrong, try again later');
			}
		});
	},

	//////////////////// create post script	
	createPostScriptInit: function(type, groupId) {
		$("#newPostButton").on("click", function() {

			let input = {
				title: $("#newPostTitleInput").val(),
				body: $("#newPostBodyInput").val(),
			}
			if (input.title == "" || input.body == "") {
				alert("Post title and body cannot be empty.")
				return;
			}

			$.ajax({
				type: "POST",
				url: 'posts/new/' + type + "/" + groupId,
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(data) {
					$("#newPostTitleInput").val('');
					$("#newPostBodyInput").val('');
					if (type == "group") {
						PostsPageObject.showAllVissiblePosts(groupId);
					}
					if (type == "user") {
						searchFunction(postSuccessFunction, 'posts/search', 1, PostsPageObject.rowNumber);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			});
		});
	},
	//////////////////////// on click modal function for confirming post edit
	editPostScriptInit: function(groupId) {
		$('#confirmEditButton').click(function(e) {
			e.preventDefault();

			let input = {
				title: $("#editModalPostTitle").val(),
				body: $("#editModalPostBody").val(),
				id: $("#editPostModalForm").val(),
			}
			if (input.title == "" || input.body == "") {
				alert("Post title and body cannot be empty.")
				return;
			}


			$("#editPostModal").modal('hide');


			$.ajax({
				type: "POST",
				url: 'posts/edit/' + input.id,
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(input),
				success: function(data) {
					if(data.type.id == 1)
						searchFunction(postSuccessFunction, 'posts/search', 1, PostsPageObject.rowNumber);
					else
						PostsPageObject.showAllVissiblePosts(groupId);								
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			});
		});
	},

	//////////////////////// document download
	addCreateWordDocListener: function(data) {
		$('#createWord_' + data.ID).click(function() {

			let input = {
				postID: data.ID,
			}
			$.ajax({
				type: "POST",
				url: 'WordServlet',
				data: input,
				success: function(data) {
					window.open("data:application/msword;base64, " + data.data, '_blank');
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		})
	},

	////////////////////// edit post on click function
	addEditPostButtonListener: function(data) {

		// configure modal for designated post
		$('#editPost_' + data.id).click(function() {
			$("#editPostModal").modal('show');
			$("#editModalPostBody").val($("#body_" + data.id).text());
			$("#editModalPostTitle").val($("#title_" + data.id).text());
			$("#editPostModalForm").val(data.id);

		});
	},

	///////////////////// delete post listener
	addDeletePostButtonListener: function(data) {
		$('#deletePost_' + data.id).click(function() {

			$.ajax({
				type: "DELETE",
				url: 'posts/delete/' + data.id,
				success: function(data) {
						searchFunction(postSuccessFunction, 'posts/search', 1, PostsPageObject.rowNumber);				
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},
	
	///////////////////// delete post listener
	addDeleteGroupPostButtonListener: function(data, groupId) {
		$('#deletePost_' + data.id).click(function() {

			$.ajax({
				type: "DELETE",
				url: 'posts/delete/group/' + groupId + "/" + data.id,
				success: function(data) {
					   PostsPageObject.showAllVissiblePosts(groupId);					
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// listener for adding likes to posts
	addLikePostListener: function(data, type) {
		$('#likePost_' + data.id).click(function() {

			let input = {
				postId: data.id,
			}

			$.ajax({
				type: "POST",
				url: 'likes/post/new',
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(input),
				success: function(data) {
					PostsPageObject.getPostLikes(input.postId, "likeCounter");
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// listener for adding likes to comments
	addLikeCommentListener: function(data) {
		$('#likeComment_' + data.id).click(function() {

			let input = {
				commentId: data.id,
			}

			$.ajax({
				type: "POST",
				url: 'likes/comment/new',
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(data) {
					PostsPageObject.getCommentLikes(input.commentId, "likeCounterComments");
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	/// listener for adding comments
	addCommentListener: function(data) {
		$('#submitComment_' + data.id).click(function() {

			let input = {
				postId: data.id,
				body: $('#postComment_' + data.id).val()
			}

			if (input.body == "") return;

			$.ajax({
				type: "POST",
				url: 'comments/post/new',
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(data) {
					$('#postComment_' + input.postId).val('');
					PostsPageObject.getComments({ id: input.postId });
					if ($('#commentsSection_' + input.postId).is(":hidden")) {
						$('#commentsSection_' + input.postId).show();
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// delete comments listener
	deleteCommentListener: function(data) {
		$('#deleteComment_' + data.id).click(function() {

			$.ajax({
				type: "DELETE",
				url: 'comments/post/delete/'+data.id,
				success: function() {
					$('#postComment_' + data.postId).val('');
					PostsPageObject.getComments({id: data.postId});
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// listener for viewing comments
	viewCommentListener: function(data) {
		$('#viewComments_' + data.id).click(function() {
			if ($('#commentsSection_' + data.id).is(":hidden")) {
				$('#commentsSection_' + data.id).show();
			} else {
				$('#commentsSection_' + data.id).hide();
			}

		});
	},

	// ajax call for comments
	getComments: function(data) {
		
		let input = {
			postId: data.id,
		}
		$.ajax({
			type: "GET",
			url: 'comments/post/get/' + data.id,
			dataType: 'json',
			contentType: "application/json",
			success: function(data) {

				$('#commentsSection_' + input.postId).empty();
				if (data.length == 0) {
					$('#commentCounter_' + input.postId).text('(0)');
				} else {
					$('#commentCounter_' + input.postId).text('(' + data.length + ')');
				}
				for (var i = 0; i < data.length; i++) {
					$('#commentsSection_' + data[i].postId).append(PostsPageObject.createCommentHtml(data[i]));
					PostsPageObject.addLikeCommentListener(data[i]);
					$('#likeCounterComments_' + data[i].id).text(data[i].likes.length);
					PostsPageObject.deleteCommentListener(data[i]);
				}
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	},

	//// ajax call to get likes
	getPostLikes: function(id, counterID) {
		$.ajax({
			type: "GET",
			url: 'likes/get/' + id,
			success: function(data) {
				$('#' + counterID + '_' + id).text(data);
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	},
	
	getCommentLikes: function(id, counterID) {
		$.ajax({
			type: "GET",
			url: 'likes/comment/get/' + id,
			success: function(data) {
				console.log(data + " " +counterID)
				$('#' + counterID + '_' + id).text(data);
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	},

	//////////////////// create html element for post item, created by current user
	createPostHtml: function(data) {
		return ('<div id="postDiv_' + data.id + '"><div class="card"><div class="card-header post-card-header"><div class="posterName">' + data.creator.name + " " + data.creator.surname + '</div><div class="datetime">' + formatTimestamp(data.creationTime) + '</div></div>\
		<div class="card-body">\
		<h5 class="card-title"  id="title_' + data.id + '">' + data.title + '</h5>\
		<p class="card-text" id="body_'+ data.id + '">' + data.body + '</p>\
		<i class="fas fa-thumbs-up" style="color: #007bff" ></i><p id="likeCounter_'+ data.id + '" style="display:inline; margin-left: 5px;">' + data.likes + '</p>\
		<div class="button-div">\
		<button class="far fa-trash-alt  delete-button" id="deletePost_' + data.id + '"></button>\
		<button class="fas fa-edit edit-button" id="editPost_'+ data.id + '"></button>\
		<button class="far fa-thumbs-up like-button" id="likePost_'+ data.id + '"</button>\
		<button class="fas fa-file-word create-button" id="createWord_'+ data.id + '"></button></div></div>\
		<div class="card-footer">\
		<div class="input-group">\
 		<input type="text" class="form-control" id="postComment_'+ data.id + '">\
  		<button class="btn btn-outline-secondary" id="submitComment_'+ data.id + '" type="button">Submit</button></div>\
		<button id="viewComments_'+ data.id + '" class="viewCommentsButton">View Comments</button><p id="commentCounter_' + data.id + '" style="display:inline; margin-left: 5px">\
		<div id="commentsSection_' + data.id + '" class="viewCommentsDiv"></div>\
		</div></div ></div ></div >');
	},
	//////////////////// create html element for post item, not created by current user
	createPostHtmlNotUser: function(data) {
		return ('<div id="postDiv_' + data.id + '"><div class="card"><div class="card-header post-card-header"><div class="posterName">' + data.creator.name + " " + data.creator.surname + '</div><div class="datetime">' + formatTimestamp(data.creationTime) + '</div></div>\
		<div class="card-body">\
		<h5 class="card-title"  id="title_' + data.id + '">' + data.title + '</h5>\
		<p class="card-text" id="body_'+ data.id + '">' + data.body + '</p>\
		<i class="fas fa-thumbs-up" style="color: #007bff" ></i><p id="likeCounter_'+ data.id + '" style="display:inline; margin-left: 5px">' + data.likes + '</p>\
		<div class="button-div">\
		<button class="far fa-thumbs-up like-button" id="likePost_'+ data.id + '"</button></div></div>\
		<div class="card-footer">\
		<div class="input-group">\
 		<input type="text" class="form-control" id="postComment_'+ data.id + '">\
  		<button class="btn btn-outline-secondary" id="submitComment_'+ data.id + '" type="button">Submit</button></div>\
		<button id="viewComments_'+ data.id + '" class="viewCommentsButton">View Comments</button><p id="commentCounter_' + data.id + '" style="display:inline; margin-left: 5px">\
		<div id="commentsSection_' + data.id + '" class="viewCommentsDiv"></div>\
		</div></div ></div ></div > ');
	},

	createCommentHtml: function(data) {
		return ('<hr style="margin:0; padding:0;"><h5 class="commentPoster" style="display:inline;">' + data.faceUserDTO.name +" "+ data.faceUserDTO.surname + ' </h5></div><h3 class="commentTime" style="display: inline-block" >' + formatTimestamp(data.creationTime) + '</h3>\
		<div style="display:inline; float:right; margin-top: 5%; padding-left: 3%;"><i class="fas fa-thumbs-up" style="color: #007bff; display:inline; " ></i><p id="likeCounterComments_'+ data.id + '" style="display: inline; margin-left: 5px"></p>\
		<button class="far fa-thumbs-up like-button" id="likeComment_'+ data.id + '"></button>' + deleteOrNot(data)
			+ '</div><h5 class="commentText" >' + data.body + '</h5>');

		function deleteOrNot(data) {
			var posterId = postIdToCreator.get(data.postId);
			if (MainObject.user.id == posterId || data.faceUserDTO.id == MainObject.user.id) {
				return '<button class="far fa-trash-alt delete-button" style="padding:0;" id="deleteComment_' + data.id + '"></button>';
			} else {
				return '';
			}
		}
	},

	///////////// PAGINATION
	paginationButtonsInit: function(length) {
		if (PostsPageObject.pageNumber <= 1) {
			$('#previousButton').click(false);
			$('#previousButtonListItem').addClass("disabled");
		} else {
			$('#previousButtonListItem').removeClass("disabled");
		}
		if (length <= PostsPageObject.rowNumber) {
			$('#nextButton').click(false);
			$('#nextButtonListItem').addClass("disabled");
		} else {
			$('#nextButtonListItem').removeClass("disabled");
		}
		$('#pageCounter').text(PostsPageObject.pageNumber);
	},

	initPaginationButtons: function() {
		$('#nextButton').click(function() {
			PostsPageObject.pageNumber = PostsPageObject.pageNumber + 1;
			searchFunction(postSuccessFunction, 'posts/search', PostsPageObject.pageNumber, PostsPageObject.rowNumber);
		});

		$('#previousButton').click(function() {
			PostsPageObject.pageNumber = PostsPageObject.pageNumber - 1;
			searchFunction(postSuccessFunction, 'posts/search', PostsPageObject.pageNumber, PostsPageObject.rowNumber);
		});
	}
}

function formatTimestamp(time){
	var date = new Date(time);
	
	return date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear()+" " 
	+ date.getHours()+ ":" 
	+ (date.getMinutes()<10?'0':'') + date.getMinutes()+":" 
	+ (date.getSeconds()<10?'0':'') + date.getSeconds();
}


