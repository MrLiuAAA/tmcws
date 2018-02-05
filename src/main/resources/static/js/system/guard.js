$(function() {


	/**
	 * 获取用户的车辆
	 */

	getData(1);
	var current_page_num = 1;
});

function getData(page) {
	current_page_num = page;
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


			$("#pagination").pagination(pageInfo.pages, {
				prev_text: "« 上一页",
				next_text: "下一页 »",
				num_edge_entries: 2,       //两侧首尾分页条目数
				num_display_entries: 10,    //连续分页主体部分分页条目数
				current_page: current_page_num-1,   //当前页索引
				num_edge_entries: 1, //边缘页数
				num_display_entries: 4, //主体页数
				callback: pageselectCallback,
				items_per_page:1 //每页显示1项
			});

		}
	});
}
function pageselectCallback(page_index, jq){

	if(current_page_num==page_index+1){
		return false;
	}
	getData(page_index+1);
	return false;
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
