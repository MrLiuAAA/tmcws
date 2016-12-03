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
        	// / 先清空
        	$("#GuardContiner").html("");
        	
        	$.each(result, function (i, item) {
        		
        		$("#GuardContiner").append('<div class="GuardItem"><div class="ItemLeft" id="ItemLeft'+(i+1)+'"><div class="ItemLeft-CarName">'+item.name
        				+'</div><div class="ItemLeft-CarState">'+item.status+'</div></div><div class="ItemRight">'
        				+((item.status == "设防状态")?'<input type="checkbox" id="car'+(i+1)+'" checked="checked" />':'<input type="checkbox" id="car'+(i+1)+'"  />')
        				+'</div></div>');
        		
				var btn = $('#car'+(i+1)).SwitchButton();
				btn.click(function() {
					changeStatus($(this).val(),item.sn);
				});
				
				$("#ItemLeft"+(i+1)).click(function() {
					window.location.href="/backstage/editGuard?alertphone="+item.alertphone+"&shockalert="+item.shockalert+"&cuttinglinealert="+item.cuttinglinealert+"&autoalert="+item.autoalert+"&sn="+item.sn;
			    });
			　});
        	}
        });
});
function changeStatus(status,sn) {
	$.ajax({type:'POST',url:'/backstage/changeStatus',data:{'status':status?'Y':'N','sn':sn,'fieldName':"alertstatus"},
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

