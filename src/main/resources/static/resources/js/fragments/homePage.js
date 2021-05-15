/**
 * 
 */
var postIdToCreator = new Map();

$(document).ready(function() {

	$.ajax({
		url: 'profile/info',
		dataType: 'json',
		success: function(data) {
			$('#ajaxShowGreeting').append('<h1>Hey there ' + data.name + ' ' + data.surname + '</h1>');
			$('#nameDropdown').text(data.name + ' ' + data.surname);
			MainObject.user = data;
			
			$("#profilePicture").attr("src", "data:image/jpg;base64," + data.image);
			
		},
		error: function() {
			alert(data.message);
		}
	});
});
