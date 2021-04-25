$(document).ready(function() {

	$("#select-dropdown_0").select2({
		theme: "bootstrap",
		placeholder: "Select Advanced Filters",
		data: [{ id: "all", text: "All" }, { id: "fu.name", text: "First Name" }, { id: "fu.surname", text: "Last Name" }, { id: "fu.username", text: "Username" }]
	});

	$("#select-logical-operand").select2({
		theme: "bootstrap",
		data: [{ id: "and", text: "AND" }, { id: "or", text: "OR" }]
	});
	
	$("#select-word-position").select2({
		theme: "bootstrap",
		data: [{ id: "both", text: "Every position " }, { id: "front", text: "Starting with" }, { id: "back", text: "Ending with" }]
	});

	addFilters([{ id: "all", text: "All" }, { id: "fu.name", text: "First Name" }, { id: "fu.surname", text: "Last Name" }, { id: "fu.username", text: "Username" }]);
	prepareExecuteSearch(friendSuccessFunction, 'ShowFriends');

	FriendsPageObject.displayFriendsTable();
	
	FriendsPageObject.showPendingRequests();
	
	
});

