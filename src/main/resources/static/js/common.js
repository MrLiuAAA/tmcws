$(document).ready(function(){
	$(".content-wrapper").css({'width':$(document.body).width()-230,'height':$(window).height()});
	$(window).resize(function () {
		var height=$(window).height();
		var width=$(document.body).width()-230;
		$(".content-wrapper").css({'width':width,'height':height});
	});
	var myScroll = new IScroll('.content-wrapper', {
	    scrollbars: 'custom',
	    mouseWheel: false,
	    useTransform:true,
	    resizeScrollbars:true,
	    fadeScrollbars:true
   });
}) 