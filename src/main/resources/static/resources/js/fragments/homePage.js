/**
 * 
 */

$(document).ready(function() {

		$.ajax({
			url: 'profile/info',
			dataType: 'json',
			success: function(data) {
				$('#ajaxShowGreeting').append('<h1>Hey there ' + data.name + ' ' + data.surname + '</h1>');	
				$('#nameDropdown').text(data.name + ' ' + data.surname);	
				MainObject.user = data;
			},
			error: function() {
				alert(data.message);
			}
		});
		
		/*$.ajax({
			url: 'ProfilePage',
			dataType: 'json',
			success: function(data) {
				$("#profilePicture").attr("src","data:image/jpg;base64,"+data.data.IMAGE);
				$("#profilePictureOnProfile").attr("src","data:image/jpg;base64,"+data.data.IMAGE);
			},
			error: function() {
				alert(data.message);
			}
		});*/

});
