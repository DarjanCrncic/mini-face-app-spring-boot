$('.datepicker').datepicker({
	format: 'dd/mm/yyyy',
	startDate: '01/02/2021',
	todayHighlight: true,
	autoclose: true,
});
var d = new Date();
var strDate = + d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear();

$('#datepickerEnd').datepicker("setDate", new Date());
$('#datepickerStart').datepicker("setDate", "01/02/2021");

$(".select2-div").select2({
	theme: "bootstrap",
	data: [{ id: ">=", text: ">=" }, { id: "<=", text: "<=" }, { id: ">", text: ">" }, { id: "<", text: "<" }, { id: "=", text: "==" }]
});

$('#createPDFButton').click(function() {

	$('.loader').show();

	let commentOperations = [];
	let commentNumbers = [];
	let likeOperations = [];
	let likeNumbers = [];

	$('.select2-comments').each(function(index, object) {
		const IdNumber = object.id.replace(/[^0-9]/g, '');
		commentOperations.push(object.value);
		commentNumbers.push($("#commentCount_" + IdNumber).val());
	});

	$('.select2-likes').each(function(index, object) {
		const IdNumber = object.id.replace(/[^0-9]/g, '');
		likeOperations.push(object.value);
		likeNumbers.push($("#likeCount_" + IdNumber).val());
	});
	
	var newDate = $('#datepickerEnd').datepicker('getDate');
    if (newDate) { // Not null
        newDate.setDate(newDate.getDate() + 1);
    }

	let input = {
		titleKeyword: $('#title-keyword-input').val(),
		commentNumbers: commentNumbers,
		commentOperations: commentOperations,
		likeNumbers: likeNumbers,
		likeOperations: likeOperations,
		startDate: $('#datepickerStart').datepicker("getDate").toLocaleDateString("en-US"),
		endDate: newDate.toLocaleDateString("en-US"),
	}

	$.ajax({
		type: "POST",
		url: "posts/report",
		dataType: 'json',
		contentType: "application/json",
		data: JSON.stringify(input),
		success: function(response) {
			const linkSource = 'data:application/pdf;base64,' + response.data;
			const downloadLink = document.createElement("a");
			const fileName = "post_report.pdf";

			downloadLink.href = linkSource;
			downloadLink.download = fileName;
			downloadLink.click();
		},
		error: function() {
			alert("Something went wrong, please try again later");
		}
	});
});