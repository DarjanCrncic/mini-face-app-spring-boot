$(document).ready(function() {

	$('.addMembersClass').click(function() {
		$('#addMembersModal').modal('show');
	});
	
	$('.editGroupInfo').click(function(e){
		e.preventDefault();
		$('#editGroupModal').modal('show');
		$('#editGroupName').val($('#groupName').text());
		$('#editGroupDescription').val($('#groupDescription').text());
	});
	
	$('#returnButton').click(function(){
		MainObject.unloadSecondary(true);
	});

});