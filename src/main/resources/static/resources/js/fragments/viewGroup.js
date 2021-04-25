$(document).ready(function() {

	$('.addMembersClass').click(function() {
		$('#addMembersModal').modal('show');
	});
	
	$('.editGroupInfo').click(function(){
		$('#editGroupModal').modal('show');
		$('#newGroupName').val($('#groupName').text());
		$('#newGroupDescription').val($('#groupDescription').text());
	});

});