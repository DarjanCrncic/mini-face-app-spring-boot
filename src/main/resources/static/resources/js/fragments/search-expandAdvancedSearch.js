function addFilters(data){

	$("#addFilters").on('click', function() {

		let total = MainObject.findMaxIdInClass("additional-filters") + 1;

		let uniqueButtonId = "addFilters_" + total;
		let uniqueSearchBarId = "searchItem_" + total;
		let uniqueSelectorId = "select-dropdown_" + total;

		let row = ('<div><button class="btn btn-outline-danger search-button additional-filters" id="' + uniqueButtonId + '" name="addFilters">Remove</button><!--\
		--><select class="select2-div" id="' + uniqueSelectorId + '" style="width: 20%"></select><!--\
		--><input class="form-control mr-sm-2 search-bar" type="text" placeholder="Search" id="' + uniqueSearchBarId + '"></div>');

		$("#searchFormDiv").append(row);

		$('#'+uniqueSelectorId).select2({
			theme: "bootstrap",
			placeholder: "Select Advanced Filters",
			data: data
		});

		$('#' + uniqueButtonId).click(function() {
			const IdNumber = this.id.replace(/[^0-9]/g, '');
			$("#addFilters_" + IdNumber).remove();
			$("#searchItem_" + IdNumber).remove();
			$("#select-dropdown_" + IdNumber).select2('destroy');
			$("#select-dropdown_" + IdNumber).remove();
		});
	});

};
