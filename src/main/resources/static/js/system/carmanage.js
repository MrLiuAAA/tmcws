$(function(){
	
	var wd = $(window).width() - 200;
	wd = ((wd - 800) > 0 ? wd : 800);
	$("#div-right").width(wd);
	/**
	 * 获取用户的车辆信息
	 */
	 $.ajax({type:'POST',url:'/backstage/getmycarManage',data:{},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.obj;
        	$.each(result, function (i, item) {
				$("<div class=\"div-item\" id=\"div-item\"></div>").appendTo($("#div-right"));
				$("<div class=\"div-1\" id=\"div-1\"></div>").appendTo($("#div-item"));
				$("<div class=\"div-2\" id=\"div-2\"></div>").appendTo($("#div-item"));
				$("<div class=\"div-3\" id=\"div-3\"></div>").appendTo($("#div-item"));
				$("<div class=\"div-4\" id=\"div-4\"></div>").appendTo($("#div-item"));
				
				$("<div class=\"ItemTitle\">"+item.name+"</div>").appendTo($("#div-1"));
				$("<div>"+item.sn+"</div>").appendTo($("#div-1"));
				
				$("<div>状态：已失效 已断电</div>").appendTo($("#div-2"));
				$("<div>位置：浙江省杭州市江干区就包三餐西园1506号</div>").appendTo($("#div-2"));
				
				$("<div>速度：15km/h</div>").appendTo($("#div-3"));
				$("<div>当日里程：20km</div>").appendTo($("#div-3"));
				$("<div>总里程：180km</div>").appendTo($("#div-3"));
				
				$("<span></span><a><img src=\"/images/edit.png\" id=\"edit\"/></a> " +
						"<a><img src=\"/images/battery-red.png\" id=\"red\" /></a>" +
						"<a><img src=\"/images/restart.png\" id=\"restart\" /></a> " +
						"<a><img src=\"/images/delete.png\" id=\"delete\" /></a>").appendTo($("#div-4"));
			　});
        	}
        });
});