const GroupsPageObject = {

	///// show all groups in table
	displayGroupsTable: function() {
		$.ajax({
			url: 'groups/',
			contentType: 'application/json',
			dataType: 'json',
			success: function(data) {
				$('#groupsTable').html("");
				$('#groupsTable').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 50%">Description</th > <th style="width: 20%">Owner</th><th style="width: 10%"></th></tr>');
				for (var i = 0; i < data.length; i++) {
					var row = $('<tr class="groups-table-row"><td>' + data[i].name + '</td><td>' + data[i].description + '</td><td>' + data[i].owner.name + " " + data[i].owner.surname + '</td>\
						<td style="padding: 0.3rem;"><button id="view_' + data[i].id + '" class="btn btn-outline-secondary view-button">View</button></td></tr>');
					$('#groupsTable').append(row);
					GroupsPageObject.openGroupOnClick(data[i], data[i].owner.id);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		});
	},

	/// open group window when clicking on view
	openGroupOnClick: function(data, userID) {
		$('#view_' + data.id).click(function() {
			MainObject.loadSecondary('resources/html/fragments/viewGroup.html', true, function() {
				$('#viewGroupInside').append('<div class="container"><h2 id="groupName" class="blue-titles" style="display: inline-block;">' + data.name + '</h2>\
				<button id="returnButton" class="btn btn-outline-danger float-right" style="margin-top: 30px">Go Back</button>\
				<p id="groupDescription" >' + data.description + '</p></div>');
				
				GroupsPageObject.showGroupMembers(data.id);

				if (userID == data.owner.id) {
					$('#viewGroupRightColumn').append('<button class="btn btn-primary addMembersClass" id="addMembers_' + data.id + '">Add Members</button>');
					$('#viewGroupRightColumn').append('<button class="btn btn-primary editGroupInfo" id="editGroupInfoButton">Edit Group</button>');
					GroupsPageObject.editGroupListener(data.id);
					GroupsPageObject.initiateBootTable(data);
				}

				PostsPageObject.showAllVissiblePosts(data.id);

				PostsPageObject.createPostScriptInit("group", data.id);

				PostsPageObject.editPostScriptInit(data.id);
			});
		});
	},

	/// list all group members
	showGroupMembers: function(groupID) {
		$.ajax({
			url: 'groups/members/' + groupID,
			dataType: 'json',
			contentType: 'application/json',
			success: function(data) {
				$('#ajaxShowGroupMembers').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 20%">Last Name</th > <th style="width: 20%">Username</th> </tr>');
				for (var i = 0; i < data.length; i++) {
					var row = $('<tr><td>' + data[i].name + '</td><td>' + data[i].surname + '</td><td>' + data[i].username + '</td></tr>');
					$('#ajaxShowGroupMembers').append(row);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		});
	},

	/////////////////// add group request listener
	addAcceptGroupRequestListener: function(data) {
		$('#acceptRequest_' + data.id).click(function() {

			let input = {
				groupId: data.id,
			}

			$.ajax({
				type: "POST",
				url: 'groups/accept',
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(data) {

					$('#requestDiv_' + input.groupId).empty();
					$('#requestDiv_' + input.groupId).remove();
					$('#groupsTable').html("");
					GroupsPageObject.displayGroupsTable();
				},
				error: function(data) {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	addDeclineGroupRequestListener: function(data) {
		$('#declineRequest_' + data.id).click(function() {

			let input = {
				groupId: data.id,
			}

			$.ajax({
				type: "POST",
				url: 'groups/decline',
				dataType: 'json',
				contentType: "application/json",
				data: JSON.stringify(input),
				success: function(response) {
					$('#requestDiv_' + response.groupId).remove();
				},
				error: function(response) {
					alert("Something went wrong, try again later");
				}
			})
		});
	},

	///////////////////// new or edit group button on click action
	editGroupListener: function(groupID) {
		$('#confirmGroupEditButton').on("click", function(e) {
			e.preventDefault();
			let name = $('#editGroupName').val();
			let description = $('#editGroupDescription').val();

			if (description == "" || name == "") {
				alert("Invalid data for editing group");
			} else {
				let input = {
					name: name,
					description: description,
					id: groupID
				}

				$.ajax({
					type: "POST",
					url: 'groups/edit/' + groupID,
					contentType: 'application/json',
					dataType: 'json',
					data: JSON.stringify(input),
					success: function(data) {
						$('#editGroupModal').modal('hide');
						$('#groupName').text(input.name);
						$('#groupDescription').text(input.description);
						GroupsPageObject.displayGroupsTable();
					},
					error: function(data) {
						alert("Something went wrong, try again later");
					}
				})
			}
		});
	},

	///////////////////// new or edit group button on click action
	newGroupListener: function() {
		$('#confirmGroupCreateButton').on("click", function(e) {
			e.preventDefault();
			let name = $('#newGroupName').val();
			let description = $('#newGroupDescription').val();

			if (description == "" || name == "") {
				alert("Invalid data for creating group");
			} else {
				let input = {
					name: name,
					description: description,
				}

				$.ajax({
					type: "POST",
					url: 'groups/',
					contentType: 'application/json',
					dataType: 'json',
					data: JSON.stringify(input),
					success: function(data) {
						$('#newGroupModal').modal('hide');
						GroupsPageObject.displayGroupsTable();
					},
					error: function(data) {
						alert("Something went wrong, try again later");
					}
				})
			}
		});
	},

	/// show all group requests
	showGroupRequests: function() {
		$.ajax({
			url: 'groups/requests',
			dataType: 'json',
			success: function(data) {
				if (data.length > 0) {
					$('#ajaxShowGroupRequests').append('<h2 id="#ajaxShowGroupRequests" class="blue-titles">Group Requests:</h2>');
				}
				for (var i = 0; i < data.length; i++) {
					$('#ajaxShowGroupRequests').append('<div class="friendReqDiv" id="requestDiv_' + data[i].id + '">\
					<div class="requestMessage">'+ data[i].owner.name + ' wants you to join his group ' + data[i].name + '!</div><div class="groupReqButtonsDiv">\
					<button class="btn btn-outline-secondary" id="acceptRequest_'+ data[i].id + '">Accept</button>\
					<button class="btn btn-outline-danger" id="declineRequest_'+ data[i].id + '">Decline</button>\
					</div></div>');
					GroupsPageObject.addAcceptGroupRequestListener(data[i]);
					GroupsPageObject.addDeclineGroupRequestListener(data[i]);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		})
	},

	//////////////// bootstrap table stuff
	initiateBootTable: function(data) {

		var operateEvents = {
			'click .sendGroupRequest': function(e, value, row, index) {
				let input = {
					userId: row.id,
					groupId: data.id,
				}

				$.ajax({
					type: "POST",
					contentType: "application/json",
					url: 'groups/send',
					dataType: 'json',
					data: JSON.stringify(input),
					success: function(data) {
						alert("Request sent!");
					},
					error: function(data) {
						alert("Something went wrong, try again later");
					}
				});
			}
		}

		function operateFormatter(value, row, index) {
			return [
				'<button class="sendGroupRequest btn btn-outline-secondary" href="javascript:void(0)">Send Request</button>'
			].join('')
		}

		$('#newMembersTable').bootstrapTable({
			url: 'groups/non-members/' + data.id,
			dataField: 'data',
			pagination: true,
			pageSize: '5',
			search: true,
			columns: [{
				field: 'id',
				title: 'ID',
				visible: false
			}, {
				field: 'name',
				title: 'First Name'
			}, {
				field: 'surname',
				title: 'Last Name'
			}, {
				field: 'username',
				title: 'Username'
			}, {
				field: 'OPERATE',
				title: 'Action',
				clickToSelect: false,
				events: operateEvents,
				formatter: operateFormatter,
				width: '150'
			}]
		});

		$('#addMembersModal').on('shown.bs.modal', function() {
			$('#newMembersTable').bootstrapTable('resetView');
		});

	}

}