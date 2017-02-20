$(function() {


	/**
	 * 获取用户的车辆
	 */

	getData(1);

});

function getData(page) {

	var keyword = $("#keyword").val();
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({type:'POST',url:'/backstage/getmycarstatus',data:{'page':page,'keyword':keyword},
		dataType:'json',success:function(data, textStatus) {
			layer.close(index);
			var result=data.obj.list;
			var pageInfo=data.obj.pageInfo;
			// / 先清空
			$("#fortifyList").html("");

			$.each(result, function (i, item) {

				$("#fortifyList").append('<li class="clearfix">'+
					'<div class="fortifyL">'+
					'<h3 class="fortifyName"><a href="/backstage/editGuard?sn='+item.sn+'">'+item.name+'</a></h3>'+
					'<p>'+item.status+'</p>'+
					'</div>'+
					'<div class="fortifyR">'+
					'<div class="checkbox checkbox-slider--b-flat checkbox-slider-md">'+
					'<label><input '+((item.status == "设防状态")?'checked="checked"':'')+' type="checkbox" onchange="changeStatus(this,'+item.sn+')"/><span></span></label>'+
					'</div>'+
					'</div>'+
					'</li>');

			});


			/// 分页信息
			$("#pagination").html("");

			$("#pagination").append(
				'<li><a href="#">共'+pageInfo.pages+'页</a></li>'
				+(pageInfo.hasPreviousPage?'<li><a href="javascript:getData('+pageInfo.prePage+')">上一页</a></li>':'')
				+'<li><a href="#">'+page+'</a></li>'
				+(pageInfo.hasNextPage?'<li><a href="javascript:getData('+pageInfo.nextPage+')">下一页</a></li>':'')

			);
		}
	});
}

function changeStatus(btn,sn) {
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({type:'POST',url:'/backstage/changeStatus',data:{'status':$(btn).is(':checked')?'Y':'N','sn':sn,'fieldName':"alertstatus"},
		dataType:'json',success:function(data, textStatus) {
			layer.close(index);
			var result=data.result;
			if ("success" != result) {  // 失败了
				layer.msg(result, {
					time: 2000 //2s后自动关闭

				});
			}else{
				window.location.reload();//刷新当前页面.
			}
		}
	});
}

function searchCar() {
	getData(1);
}
