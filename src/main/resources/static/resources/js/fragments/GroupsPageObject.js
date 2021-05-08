const GroupsPageObject = {

	///// show all groups in table
	displayGroupsTable: function() {
		$.ajax({
			url: 'groups/get',
			contentType: 'application/json',
			dataType: 'json',
			success: function(data) {
				$('#groupsTable').html("");
				$('#groupsTable').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 50%">Description</th > <th style="width: 20%">Owner</th><th style="width: 10%"></th></tr>');
				for (var i = 0; i < data.length; i++) {
					var row = $('<tr class="groups-table-row"><td>' + data[i].name + '</td><td>' + data[i].description + '</td><td>' + data[i].owner.name + " " + data[i].owner.surname + '</td>\
						<td style="padding: 0.3rem;"><button id="view_' + data[i].id + '" class="btn btn-outline-secondary view-button">View</button></td></tr>');
					$('#groupsTable').append(row);
					//GroupsPageObject.openGroupOnClick(data.data[i], data.userID);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		});
	},

	/// open group window when clicking on view
	openGroupOnClick: function(data, userID) {
		$('#view_' + data.ID).click(function() {
			MainObject.loadSecondary('html/fragments/viewGroup.html', true, function() {
				$('#viewGroupInside').append('<h2 id="groupName" class="blue-titles">' + data.NAME + '</h2>');
				$('#viewGroupInside').append('<p id="groupDescription">' + data.DESCRIPTION + '</p>');
				GroupsPageObject.showGroupMembers(data.ID);
				if (userID == data.OWNER_ID) {
					$('#viewGroupRightColumn').append('<button class="btn btn-primary addMembersClass" id="addMembers_' + data.ID + '">Add Members</button>');
					$('#viewGroupRightColumn').append('<button class="btn btn-primary editGroupInfo" id="editGroupInfoButton">Edit Group</button>');
					GroupsPageObject.newOrEditGroupListener("confirmGroupEditButton", 'edit', data.ID);
					GroupsPageObject.initiateBootTable(data);
				}

				PostsPageObject.showAllVissiblePosts("group", data.ID);

				PostsPageObject.createPostScriptInit("group", data.ID);

				PostsPageObject.editPostScriptInit();
			});
		});
	},

	/// list all group members
	showGroupMembers: function(groupID) {
		$.ajax({
			url: 'ShowGroups',
			dataType: 'json',
			data: {
				option: "members",
				groupID: groupID
			},
			success: function(data) {
				if (data.status == "success") {
					$('#ajaxShowGroupMembers').append('<tr class="mainFriendsTableRow"> <th style="width: 20%">Name</th> <th style="width: 20%">Last Name</th > <th style="width: 20%">Username</th> </tr>');
					for (var i = 0; i < data.data.length; i++) {
						var row = $('<tr><td>' + data.data[i].NAME + '</td><td>' + data.data[i].SURNAME + '</td><td>' + data.data[i].USERNAME + '</td></tr>');
						$('#ajaxShowGroupMembers').append(row);
					}
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Something went wrong, try again later');
			}
		});
	},

	/////////////////// add group request listener
	addGroupRequestListener: function(data, operation, buttonID) {
		$('#' + buttonID + '_' + data.ID).click(function() {

			let input = {
				operation: operation,
				groupID: data.ID,
				type: 'group'
			}

			$.ajax({
				type: "POST",
				url: 'CRUDRequest',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						if (buttonID == "acceptRequest") {
							$('#requestDiv_' + input.groupID).empty();
							$('#requestDiv_' + input.groupID).remove();
							$('#groupsTable').html("");
							GroupsPageObject.displayGroupsTable();
						}
						if (buttonID == "declineRequest") {
							$('#requestDiv_' + input.groupID).remove();
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

	///////////////////// new or edit group button on click action
	newOrEditGroupListener: function(buttonID, operation, groupID) {
		$('#' + buttonID).on("click", function(e) {
			e.preventDefault();
			let name = $('#newGroupName').val();
			let description = $('#newGroupDescription').val();

			if (description == "" || name == "") {
				alert("Invalid data for creating group");
			} else {
				let input = {
					name: name,
					description: description,
					id: groupID
				}

				$.ajax({
					type: "POST",
					url: 'CRUDGroup',
					dataType: 'json',
					data: input,
					success: function(data) {
						if (operation == "create") {
							$('#newGroupModal').modal('hide');
							GroupsPageObject.displayGroupsTable();
						}
						if (operation == "edit") {
							$('#editGroupModal').modal('hide');
							$('#groupName').text(input.name);
							$('#groupDescription').text(input.description);
						}
					},
					error: function(data) {
						alert("Something went wrong, try again later");
					}
				})
			}
		});
	},

	///////////////////// new or edit group button on click action
	newGroupListener: function(buttonID, operation, groupID) {
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
					url: 'groups/new',
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
			url: 'CRUDRequest',
			dataType: 'json',
			data: { type: 'group' },
			success: function(data) {
				if (data.status == 'success') {
					if (data.data.length > 0) {
						$('#ajaxShowGroupRequests').append('<h2 id="#ajaxShowGroupRequests" class="blue-titles">Group Requests:</h2>');
					}
					for (var i = 0; i < data.data.length; i++) {
						$('#ajaxShowGroupRequests').append('<div class="friendReqDiv" id="requestDiv_' + data.data[i].ID + '">\
					<div class="requestMessage">'+ data.data[i].OWNER + ' wants you to join his group ' + data.data[i].NAME + '!</div><div class="groupReqButtonsDiv">\
					<button class="btn btn-outline-secondary" id="acceptRequest_'+ data.data[i].ID + '">Accept</button>\
					<button class="btn btn-outline-danger" id="declineRequest_'+ data.data[i].ID + '">Decline</button>\
					</div></div>');
						GroupsPageObject.addGroupRequestListener(data.data[i], 'accept', 'acceptRequest');
						GroupsPageObject.addGroupRequestListener(data.data[i], 'decline', 'declineRequest');
					}
				} else {
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
					receiverID: row.ID,
					groupID: data.ID,
					operation: 'create',
					type: 'group'
				}

				$.ajax({
					type: "POST",
					url: 'CRUDRequest',
					dataType: 'json',
					data: input,
					success: function(data) {
						if (data.status == 'success') {
							alert("Request sent!");
						} else {
							alert(data.message);
						}
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
			url: 'ShowGroups?option=notMembers&groupID=' + data.ID,
			dataField: 'data',
			pagination: true,
			pageSize: '5',
			search: true,
			columns: [{
				field: 'ID',
				title: 'ID',
				visible: false
			}, {
				field: 'NAME',
				title: 'First Name'
			}, {
				field: 'SURNAME',
				title: 'Last Name'
			}, {
				field: 'USERNAME',
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