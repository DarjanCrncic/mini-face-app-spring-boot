$(document).ready(function() {

	showProfilePage();

	$('#showEditInfoModal').click(function() {
		$('#editInfoModal').modal('show');
		$('#editedNameInput').val($('#profileName').text());
		$('#editedSurnameInput').val($('#profileSurname').text());
		$('#editedCountryInput').val($('#profileCountry').text());
		$('#editedCityInput').val($('#profileCity').text());
		$('#editedAgeInput').val($('#profileAge').text());
		$('#editedGenderInput').val($('#profileGender').text());
	});

	$('#saveEditedInfo').click(function() {
		let input =
		{
			name: $('#editedNameInput').val(),
			surname: $('#editedSurnameInput').val(),
			country: $('#editedCountryInput').val(),
			city: $('#editedCityInput').val(),
			age: $('#editedAgeInput').val(),
			gender: $('#editedGenderInput').val()
		}

		$.ajax({
			type: "POST",
			url: 'profile/update',
			dataType: "JSON",
			contentType: "application/json",
			data: JSON.stringify(input),
			success: function(data) {
				$('#editInfoModal').modal('hide');
				showProfilePage();
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	})

	function showProfilePage() {
		$.ajax({
			url: 'profile/info',
			dataType: 'json',
			success: function(data) {
				//$("#profilePicture").attr("src", "data:image/jpg;base64," + data.image);
				$("#profilePictureOnProfile").attr("src", "data:image/jpg;base64," + data.image);
				$('#profileName').text(data.name);
				$('#profileSurname').text(data.surname);
				$('#profileFullName').text(data.name + " " + data.surname);
				$('#profileAge').text((data.age != 0) ? data.age : "");
				$('#profileCity').text(data.city);
				$('#profileCountry').text(data.country);
				$('#profileGender').text(data.gender);
				if (data.notify == true) {
					$('#receiveMailsCheck').prop('checked', true);
				} else {
					$('#receiveMailsCheck').prop('checked', false);
				}

			},
			error: function() {
				alert(data.message);
			}
		});
	}

	$('#receiveMailsCheck').click(function() {
		let notify;
		if ($('#receiveMailsCheck').is(':checked')) {
			notify = true;
		} else {
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