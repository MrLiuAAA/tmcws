$(function() {
	var wd = $(window).width() - 200;
	wd = ((wd - 800) > 0 ? wd : 800);
	$("#div-right").width(wd);
	
	
	
	/**
	 * 获取用户的车辆
	 */
	 $.ajax({type:'POST',url:'/backstage/getmycarstatus',data:{},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.obj;
        	$.each(result, function (i, item) {
				$("<div class=\"GuardItem\" id=\"GuardItem\"><div class=\"ItemLeft\" id=\"ItemLeft\"></div></div>").appendTo($("#GuardContiner"));
				$("<div class=\"ItemLeft-CarName\" id=\"ItemLeft-CarName\">"+item.name+"</div>").appendTo($("#ItemLeft"));
				$("<div class=\"ItemLeft-CarState\" id=\"ItemLeft-CarState\">"+item.status+"</div>").appendTo($("#ItemLeft"));
				$("<div class=\"ItemRight\" id=\"ItemRight\"></div>").appendTo($("#GuardItem"));
				if(item.status == "设防状态"){
					$("<input type=\"checkbox\" id=\"car1\" checked=\"checked\"/>").appendTo($("#ItemRight"));
				}else{
					$("<input type=\"checkbox\" id=\"car1\" />").appendTo($("#ItemRight"));
				}
				
				var btn = $("#car1").SwitchButton();
				btn.click(function() {
					changeStatus($(this).val(),item.sn);
				});
				
				$(".ItemLeft").click(function() {
					// geteditGuard(item.alertphone,item.shockalert,item.cuttinglinealert,item.autoalert)
					window.location.href="/backstage/editGuard";
				      // "?alertphone:"+item.alertphone+"&shockalert:"+item.shockalert+"&cuttinglinealert:"+item.cuttinglinealert+"&autoalert:"+item.autoalert;
			    });
				
			　});
        	}
        });
	
	
});
function changeStatus(alertstatus,sn) {
	$.ajax({type:'POST',url:'/backstage/changeStatus',data:{'alertstatus':alertstatus,'sn':sn},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.result;
        	if ("success" != result) {  // 如果登录不成功，则再次刷新验证码
				alert(result);
			}else{
				window.location.href="/backstage/setGuard";
			} 
        }
 });
}

function geteditGuard(alertphone,shockalert,cuttinglinealert,autoalert){
	alert("editGuard");
	$.ajax({type:'POST',url:'/backstage/geteditGuard',data:{'alertphone':alertphone,'shockalert':shockalert,'cuttinglinealert':cuttinglinealert,'autoalert':autoalert},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.result;
        	if ("success" != result) {  // 如果登录不成功，则再次刷新验证码
				alert(result);
			}else{
				window.location.href="/backstage/editGuard";
			} 
        }
 });
}
 