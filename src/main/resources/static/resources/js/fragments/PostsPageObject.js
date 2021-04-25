
const PostsPageObject = {

	pageNumber: 1,
	rowNumber: 5,

	/////////////////////// show all vissible posts on page load // !!!!!!!!!! NOT USED ANYMORE, EVERYTHING THROUGH SEARCH !!!! USED IN GROUP POSTS
	showAllVissiblePosts: function(type, groupID) {
		$.ajax({
			url: 'ShowPosts',
			dataType: 'json',
			data: { type: type, groupID: groupID, pageNumber: PostsPageObject.pageNumber },
			type: "GET",
			success: function(data) {
				if (data.status == 'success') {
					$('#ajaxShowVissiblePosts').html("");
					for (var i = 0; i < data.data.length; i++) {
						if (data.userID == data.data[i].CREATOR_ID) {
							$('#ajaxShowVissiblePosts').append(PostsPageObject.createPostHtml(data.data[i]));

							if (data.data[i].TYPE == "user")
								PostsPageObject.addDeleteEditPostButtonListener(data.data[i], "user");
							if (data.data[i].TYPE == "group")
								PostsPageObject.addDeleteEditPostButtonListener(data.data[i], "group", groupID);

						} else {
							$('#ajaxShowVissiblePosts').append(PostsPageObject.createPostHtmlNotUser(data.data[i]));
						}
						PostsPageObject.addLikePostListener(data.data[i], "post");
						PostsPageObject.addCommentListener(data.data[i]);
						PostsPageObject.viewCommentListener(data.data[i]);
						PostsPageObject.getComments(data.data[i]);
					}
					PostsPageObject.paginationButtonsInit(data.data.length);
				} else {

				}
			},
			error: function() {
				alert('Something went wrong, try again later');
			}
		});
	},

	//////////////////// create post script	
	createPostScriptInit: function(type, groupID) {
		$("#newPostButton").on("click", function() {

			let input = {
				title: $("#newPostTitleInput").val(),
				body: $("#newPostBodyInput").val(),
				operation: "add",
				postId: "",
				type: type,
				groupID: groupID
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						$("#newPostTitleInput").val('');
						$("#newPostBodyInput").val('');
						if(type == "group"){
							PostsPageObject.showAllVissiblePosts("group", groupID);
						}
						if(type == "user"){
							searchFunction(postSuccessFunction, 'ShowPosts', 1, PostsPageObject.rowNumber);
						}						
					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			});
		});
	},
	//////////////////////// on click modal function for confirming post edit
	editPostScriptInit: function() {
		$('#confirmEditButton').click(function(e) {
			e.preventDefault();

			let input = {
				title: $("#editModalPostTitle").val(),
				body: $("#editModalPostBody").val(),
				operation: "edit",
				postId: $("#editPostModalForm").val(),
			}

			$("#editPostModal").modal('hide');


			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {

						$("#title_" + data.data.ID).text(data.data.TITLE);
						$("#body_" + data.data.ID).text(data.data.BODY);

					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			});
		});
	},
	
	//////////////////////// document download
	addCreateWordDocListener:function(data){
		$('#createWord_' + data.ID).click(function(){
			
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

	////////////////////// delete and edit post on click function
	addDeleteEditPostButtonListener: function(data, type, groupID) {
		$('#' + data.ID).click(function() {

			let input = {
				title: data.TITLE,
				body: data.BODY,
				operation: "delete",
				postId: data.ID,
				type: type,
				groupID: groupID
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success' && type == "user" || data.status == 'success' && type == "group" && input.groupID == null) {
						searchFunction(postSuccessFunction, 'ShowPosts', 1, PostsPageObject.rowNumber);
					} else if(data.status == 'success' && type == "group"){
						PostsPageObject.showAllVissiblePosts("group", input.groupID);
					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});

		// configure modal for designated post
		$('#editPost_' + data.ID).click(function() {
			$("#editPostModal").modal('show');
			$("#editModalPostBody").val($("#body_" + data.ID).text());
			$("#editModalPostTitle").val($("#title_" + data.ID).text());
			$("#editPostModalForm").val(data.ID);

		});
	},

	///// listener for adding likes to posts
	addLikePostListener: function(data, type) {
		$('#likePost_' + data.ID).click(function() {

			let input = {
				operation: "likePost",
				postId: data.ID,
				type: type
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						PostsPageObject.getLikes({ ID: input.postId }, "postLikes", "likeCounter");
					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// listener for adding likes to comments
	addLikeCommentListener: function(data, type) {
		$('#likeComment_' + data.ID).click(function() {

			let input = {
				operation: "likeComment",
				commentID: data.ID,
				type: type
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						PostsPageObject.getLikes({ ID: input.commentID }, "commentLikes", "likeCounterComments");
					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	/// listener for adding comments
	addCommentListener: function(data) {
		$('#submitComment_' + data.ID).click(function() {

			let input = {
				operation: "comment",
				postId: data.ID,
				comment: $('#postComment_' + data.ID).val()
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						$('#postComment_' + input.postId).val('');
						PostsPageObject.getComments({ ID: input.postId });
						if ($('#commentsSection_' + input.postId).is(":hidden")) {
							$('#commentsSection_' + input.postId).show();
						}
					} else {
						alert(data.message);
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
		$('#deleteComment_' + data.ID).click(function() {

			let input = {
				operation: "deleteComment",
				commentID: data.ID,
				postId: data.POST_ID
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						$('#postComment_' + input.postId).val('');
						PostsPageObject.getComments({ ID: input.postId });
					} else {
						alert(data.message);
					}
				},
				error: function() {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///// listener for viewing comments
	viewCommentListener: function(data) {
		$('#viewComments_' + data.ID).click(function() {
			if ($('#commentsSection_' + data.ID).is(":hidden")) {
				$('#commentsSection_' + data.ID).show();
			} else {
				$('#commentsSection_' + data.ID).hide();
			}

		});
	},

	// ajax call for comments
	getComments: function(data) {
		let input = {
			postId: data.ID,
			type: "comments"
		}
		$.ajax({
			type: "GET",
			url: 'CRUDPost',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {
					$('#commentsSection_' + input.postId).empty();
					if (data.data.length == 0) {
						$('#commentCounter_' + input.postId).text('(0)');
					} else {
						$('#commentCounter_' + input.postId).text('(' + data.data.length + ')');
					}
					for (var i = 0; i < data.data.length; i++) {
						$('#commentsSection_' + data.data[i].POST_ID).append(PostsPageObject.createCommentHtml(data.data[i], data.userID));
						PostsPageObject.addLikeCommentListener(data.data[i], "comment");
						PostsPageObject.getLikes(data.data[i], "commentLikes", "likeCounterComments");
						PostsPageObject.deleteCommentListener(data.data[i]);
					}

				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	},

	//// ajax call to get likes
	getLikes: function(data, type, counterID) {
		let input = {
			entityID: data.ID,
			type: type
		}

		$.ajax({
			type: "GET",
			url: 'CRUDPost',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {
					$('#' + counterID + '_' + input.entityID).text(data.data.LIKES);
				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});

	},

	//////////////////// create html element for post item, created by current user
	createPostHtml: function(data) {
		return ('<div id="postDiv_' + data.ID + '"><div class="card"><div class="card-header post-card-header"><div class="posterName">' + data.NAME + '</div><div class="datetime">' + data.CREATION_TIME + '</div></div>\
		<div class="card-body">\
		<h5 class="card-title"  id="title_' + data.ID + '">' + data.TITLE + '</h5>\
		<p class="card-text" id="body_'+ data.ID + '">' + data.BODY + '</p>\
		<i class="fas fa-thumbs-up" style="color: #007bff" ></i><p id="likeCounter_'+ data.ID + '" style="display:inline; margin-left: 5px;">' + data.LIKES + '</p>\
		<div class="button-div">\
		<button class="far fa-trash-alt  delete-button" id=' + data.ID + '></button>\
		<button class="fas fa-edit edit-button" id="editPost_'+ data.ID + '"></button>\
		<button class="far fa-thumbs-up like-button" id="likePost_'+ data.ID + '"</button>\
		<button class="fas fa-file-word create-button" id="createWord_'+data.ID+'"></button></div></div>\
		<div class="card-footer">\
		<div class="input-group">\
 		<input type="text" class="form-control" id="postComment_'+ data.ID + '">\
  		<button class="btn btn-outline-secondary" id="submitComment_'+ data.ID + '" type="button">Submit</button></div>\
		<button id="viewComments_'+ data.ID + '" class="viewCommentsButton">View Comments</button><p id="commentCounter_' + data.ID + '" style="display:inline; margin-left: 5px">\
		<div id="commentsSection_' + data.ID + '" class="viewCommentsDiv"></div>\
		</div></div ></div ></div >');
	},
	//////////////////// create html element for post item, not created by current user
	createPostHtmlNotUser: function(data) {
		return ('<div id="postDiv_' + data.ID + '"><div class="card"><div class="card-header post-card-header"><div class="posterName">' + data.NAME + '</div><div class="datetime">' + data.CREATION_TIME + '</div></div>\
		<div class="card-body">\
		<h5 class="card-title"  id="title_' + data.ID + '">' + data.TITLE + '</h5>\
		<p class="card-text" id="body_'+ data.ID + '">' + data.BODY + '</p>\
		<i class="fas fa-thumbs-up" style="color: #007bff" ></i><p id="likeCounter_'+ data.ID + '" style="display:inline; margin-left: 5px">' + data.LIKES + '</p>\
		<div class="button-div">\
		<button class="far fa-thumbs-up like-button" id="likePost_'+ data.ID + '"</button></div></div>\
		<div class="card-footer">\
		<div class="input-group">\
 		<input type="text" class="form-control" id="postComment_'+ data.ID + '">\
  		<button class="btn btn-outline-secondary" id="submitComment_'+ data.ID + '" type="button">Submit</button></div>\
		<button id="viewComments_'+ data.ID + '" class="viewCommentsButton">View Comments</button><p id="commentCounter_' + data.ID + '" style="display:inline; margin-left: 5px">\
		<div id="commentsSection_' + data.ID + '" class="viewCommentsDiv"></div>\
		</div></div ></div ></div > ');
	},

	createCommentHtml: function(data, userID) {
		return ('<hr style="margin:0; padding:0;"><h5 class="commentPoster" style="display:inline;">' + data.NAME + '</h5>\
		<div style="display:inline; float:right;"><i class="fas fa-thumbs-up" style="color: #007bff; display:inline;" ></i><p id="likeCounterComments_'+ data.ID + '" style="display: inline; margin-left: 5px"></p>\
		<button class="far fa-thumbs-up like-button" id="likeComment_'+ data.ID + '"></button>' + deleteOrNot(data, userID)
			+ '</div><h5 class="commentText" >' + data.POST_COMMENT + '</h5>');

		function deleteOrNot(data, userID) {
			if (data.COMMENT_CREATOR_ID == userID || data.POST_CREATOR_ID == userID) {
				return '<button class="far fa-trash-alt delete-button" style="padding:0;" id="deleteComment_' + data.ID + '"></button>';
			} else {
				return '';
			}
		}
	},

	///////////// PAGINATION
	paginationButtonsInit: function(length, more) {
		if (PostsPageObject.pageNumber <= 1) {
			$('#previousButton').click(false);
			$('#previousButtonListItem').addClass("disabled");
		} else {
			$('#previousButtonListItem').removeClass("disabled");
		}
		if (length < PostsPageObject.rowNumber || !more) {
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
			searchFunction(postSuccessFunction, 'ShowPosts', PostsPageObject.pageNumber, PostsPageObject.rowNumber);
		});

		$('#previousButton').click(function() {
			PostsPageObject.pageNumber = PostsPageObject.pageNumber - 1;
			searchFunction(postSuccessFunction, 'ShowPosts', PostsPageObject.pageNumber, PostsPageObject.rowNumber);
		});
	}
}