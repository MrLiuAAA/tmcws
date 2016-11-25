$(function() {
	$("#GuardContiner").click(function() {
		getCar();
	});
	
	
	function getCar(){}
	
	/**
	 * 获取用户的车辆
	 */
	 $.ajax({type:'POST',url:'/backstage/getmycars',data:{},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.obj;
        	 /* <![CDATA[ */
        	
        	$.each(result, function (i, item) {
				$("<div class=\"GuardItem\" id=\"GuardItem\"><div class=\"ItemLeft\" id=\"ItemLeft\"></div></div>").appendTo($("#GuardContiner"));
				$("<div class=\"ItemLeft-CarName\" id=\"ItemLeft-CarName\">"+item.name+"</div>").appendTo($("#ItemLeft"));
				$("<div class=\"ItemLeft-CarState\" id=\"ItemLeft-CarState\">"+item.name+"</div>").appendTo($("#ItemLeft"));
				$("<div class=\"ItemRight\" id=\"ItemRight\"></div>").appendTo($("#GuardItem"));
				$("<input type=\"checkbox\" id=\"car1\" checked=\"checked\"></input>").appendTo($("#ItemRight"));
      	　　　　});
      		}
        	 /* ]]> */
		  
        });
	
});

 