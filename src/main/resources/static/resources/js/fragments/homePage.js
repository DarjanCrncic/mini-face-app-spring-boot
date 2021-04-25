/**
 * 
 */

$(document).ready(function() {

		$.ajax({
			url: 'HomePage',
			dataType: 'json',
			success: function(data) {
				$('#ajaxShowGreeting').append('<h1>Hey there ' + data.data.name + ' ' + data.data.surname + '</h1>');	
				$('#nameDropdown').text(data.data.name + ' ' + data.data.surname);	
			},
			error: function() {
				alert(data.message);
			}
		});
		
		$.ajax({
			url: 'ProfilePage',
			dataType: 'json',
			success: function(data) {
				$("#profilePicture").attr("src","data:image/jpg;base64,"+data.data.IMAGE);
				$("#profilePictureOnProfile").attr("src","data:image/jpg;base64,"+data.data.IMAGE);
			},
			error: function() {
				alert(data.message);
			}
		});

});
