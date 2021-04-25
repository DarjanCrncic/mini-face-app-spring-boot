MainObject.loadTertiary("html/fragments/chatroom.html");

let $loading = $('.loader').hide();
$(document).ajaxStart(function() {
	$loading.show();
})
$(document).ajaxStop(function() {
	$loading.hide();
});


$('.navigation').click(function(){
	const id = this.id;
	if(history.state.id !== id)
	history.pushState({'id': id}, "", './main?'+id);
});
	

window.addEventListener('popstate', e => {
	if(e.state.id === 'chatroom')
		MainObject.showTertiary();
	else if(e.state.id !== null)
		MainObject.navigationPage(e.state.id);
	else
		window.location.assign("./main");
});


history.replaceState({ 'id': null }, "", './main');

MainObject.navigationPage("homePage");