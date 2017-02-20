/*公共js计算页面的宽度*/
$(document).ready(function(){
	$(".content-wrapper").css({'width':$(document).width()-230,'height':$(document).height()});
	$(window).resize(function () {
		var height=$(document).height();
		var width=$(document.body).width()-230;
		$(".content-wrapper").css({'width':width,'height':height});
	})
})
