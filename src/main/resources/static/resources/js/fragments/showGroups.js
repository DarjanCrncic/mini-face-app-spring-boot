$(document).ready(function() {
	
	$("#select-dropdown_0").select2({
		theme: "bootstrap",
		placeholder: "Select Advanced Filters",
		data: [{ id: "all", text: "All" }, { id: "fg.name", text: "Group Name" }, { id: "fu.name+fu.surname", text: "Group Owner" }]
	});

	$("#select-logical-operand").select2({
		theme: "bootstrap",
		data: [{ id: "and", text: "AND" }, { id: "or", text: "OR" }]
	});

	$("#select-word-position").select2({
		theme: "bootstrap",
		data: [{ id: "both", text: "Every position " }, { id: "front", text: "Starting with" }, { id: "back", text: "Ending with" }]
	});

	addFilters([{ id: "all", text: "All" }, { id: "fg.name", text: "Group Name" }, { id: "fu.name+fu.surname", text: "Group Owner" }]);
	prepareExecuteSearch(groupsSuccessFunction, 'ShowGroups');
	
	GroupsPageObject.showGroupRequests();
	GroupsPageObject.displayGroupsTable();
	GroupsPageObject.newOrEditGroupListener("confirmGroupCreateButton",'create', "");
	
	$('#newGroupModalButton').click(function(){
		$('#newGroupModal').modal('show');
	});

});