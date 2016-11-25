$(function() {
	var wd = $(window).width() - 200;
	wd = ((wd - 800) > 0 ? wd : 800);
	$("#div-right").width(wd);
	
	$("#getValue").click(function() {
		alert($("input[type=checkbox]").prop("checked"));
	});
	/**
	 * 获取用户的车辆
	 */
	 $.ajax({type:'POST',url:'/backstage/getmycarstatus',data:{},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.obj;
        	 /* <![CDATA[ */
        	
        	$.each(result, function (i, item) {
				$("<div class=\"GuardItem\" id=\"GuardItem\"><div class=\"ItemLeft\" id=\"ItemLeft\"></div></div>").appendTo($("#GuardContiner"));
				$("<div class=\"ItemLeft-CarName\" id=\"ItemLeft-CarName\">"+item.name+"</div>").appendTo($("#ItemLeft"));
				$("<div class=\"ItemLeft-CarState\" id=\"ItemLeft-CarState\">"+item.status+"</div>").appendTo($("#ItemLeft"));
				$("<div class=\"ItemRight\" id=\"ItemRight\"></div>").appendTo($("#GuardItem"));
				$("<input type=\"checkbox\" id=\"car1\" checked=\"checked\"/>").appendTo($("#ItemRight"));

				var btn = $("#car1").SwitchButton();
				btn.click(function() {
					alert($(this).val());
				});

				$("#ItemLeft").click(function() {
					location.href = "../EditGuard.html";
				});
				
				
			　});
      		}
        	 /* ]]> */
		  
        });
	
});

 