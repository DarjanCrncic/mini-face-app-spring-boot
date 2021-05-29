MainObject.loadTertiary("resources/html/fragments/chatroom.html");

let $loading = $('.loader').hide();
$(document).ajaxStart(function() {
	$loading.show();
})
$(document).ajaxStop(function() {
	$loading.hide();
});


$('.navigation').click(function(){
	const id = this.id;
	if(history.state != null && history.state.id !== id && id !== 'chatroom')
		history.pushState({'id': id}, "", './main?'+id);
});
	

window.addEventListener('popstate', e => {
	if(e.state != null && e.state.id !== null){
		MainObject.navigationPage(e.state.id);
		$('.nav-item').removeClass('active');
		$('#'+e.state.id).parent().addClass('active');
	} else {
		window.location.assign("./main");
	}
});

history.replaceState({ 'id': null }, "", './main');

$(document).ready(function () {
    $('.navigation').click(function (e) {
        e.preventDefault()
        $('.nav-item').removeClass('active');
        $(this).parent().addClass('active');
    });
});

MainObject.navigationPage("homePage"); 
$('#homePage').parent().addClass('active');