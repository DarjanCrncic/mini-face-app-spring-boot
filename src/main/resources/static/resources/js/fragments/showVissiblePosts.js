$(document).ready(function() {
	
	////// search configuration, configuring dropdowns on page load plus cofiguring filters and search success function
	
	$("#select-dropdown_0").select2({
		theme: "bootstrap",
		placeholder: "Select Advanced Filters",
		data: [{ id: "all", text: "All" }, { id: "fp.title", text: "Title" }, { id: "fp.body", text: "Body" }, { id: "fu.name+fu.surname", text: "Posted By" }]
	});

	$("#select-logical-operand").select2({
		theme: "bootstrap",
		data: [{ id: "and", text: "AND" }, { id: "or", text: "OR" }]
	});
	
	$("#select-word-position").select2({
		theme: "bootstrap",
		data: [{ id: "both", text: "Every position " }, { id: "front", text: "Starting with" }, { id: "back", text: "Ending with" }]
	});
	
	addFilters([{ id: "all", text: "All" }, { id: "fp.title", text: "Title" }, { id: "fp.body", text: "Body" }, {id: "fu.name+fu.surname", text: "Posted By"}]);
	prepareExecuteSearch(postSuccessFunction, 'ShowPosts');
	
	PostsPageObject.rowNumber = 5;
	searchFunction(postSuccessFunction, 'ShowPosts', 1, PostsPageObject.rowNumber);

	PostsPageObject.createPostScriptInit("user", 0);

	PostsPageObject.editPostScriptInit();
	
	PostsPageObject.pageNumber = 1;
	
	PostsPageObject.initPaginationButtons();
	
	
	///// row number selector configuration
	$("#select-row-number").select2({
		theme: "bootstrap",
		data: [{id:"5", text:"5"}, {id:"10", text:"10"}, {id:"20", text:"20"}]
	});
	
	$("#select-row-number").change(function(){
		PostsPageObject.rowNumber = $("#select-row-number").val();
		PostsPageObject.pageNumber = 1;
		searchFunction(postSuccessFunction, 'ShowPosts', 1, PostsPageObject.rowNumber);
	});
});
