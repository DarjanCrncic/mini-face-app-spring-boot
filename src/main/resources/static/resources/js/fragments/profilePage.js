$(document).ready(function() {

	showProfilePage();

	$('#showEditInfoModal').click(function() {
		$('#editInfoModal').modal('show');
		$('#editedUsernameInput').val($('#profileUsername').text());
		$('#editedCountryInput').val($('#profileCountry').text());
		$('#editedCityInput').val($('#profileCity').text());
		$('#editedAgeInput').val($('#profileAge').text());
		$('#editedGenderInput').val($('#profileGender').text());
	});

	$('#saveEditedInfo').click(function() {
		let input = {
			type: "updateInfo",
			username: $('#editedUsernameInput').val(),
			country: $('#editedCountryInput').val(),
			city: $('#editedCityInput').val(),
			age: $('#editedAgeInput').val(),
			gender: $('#editedGenderInput').val()
		}

		$.ajax({
			type: "POST",
			url: 'ProfilePage',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {
					$('#editInfoModal').modal('hide');
					showProfilePage();
				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	})
	
	function showProfilePage() {
		$.ajax({
			url: 'ProfilePage',
			dataType: 'json',
			success: function(data) {
				$("#profilePicture").attr("src", "data:image/jpg;base64," + data.data.IMAGE);
				$("#profilePictureOnProfile").attr("src", "data:image/jpg;base64," + data.data.IMAGE);
				$('#profileUsername').text(data.data.USERNAME);
				$('#profileName').text(data.data.NAME);
				$('#profileAge').text(data.data.AGE);
				$('#profileCity').text(data.data.CITY);
				$('#profileCountry').text(data.data.COUNTRY);
				$('#profileGender').text(data.data.GENDER);
				if(data.data.NOTIFY == true){
					$('#receiveMailsCheck').prop('checked', true);
				}else{
					$('#receiveMailsCheck').prop('checked', false);
				}
				
			},
			error: function() {
				alert(data.message);
			}
		});
	}
	
	$('#receiveMailsCheck').click(function(){
		let notify;
		if($('#receiveMailsCheck').is(':checked')){
			notify = true;
		}else{
			notify = false;
		}
		
		let input = {
			type: "updateNotify",
			notify: notify
		}

		$.ajax({
			type: "POST",
			url: 'ProfilePage',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {

				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	});
	
});