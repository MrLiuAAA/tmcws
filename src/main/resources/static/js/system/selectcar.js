$(function() {
	/**
	 * 获取用户的车辆
	 */
	getData(1);
	document.onkeydown = function(e){
		var ev = document.all?window.event:e;
		if(ev.keyCode==13) {
			getData(1);
		}
	};

	var current_page_num = 1;
});

function getData(page) {
	current_page_num = page;
	var keyword = $("#keyword").val();
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({type:'POST',url:'/backstage/getmycarsinfo',data:{'page':page,'keyword':keyword},
		dataType:'json',success:function(data, textStatus) {

			layer.close(index);

			var isDelete = data.obj.isDelete;
			var result=data.obj.list;
			var pageInfo=data.obj.pageInfo;
			// / 先清空
			$("#dataList").html("");

			$.each(result, function (i, item) {

				$("#dataList").append(
					'<tr>'+
					'<td width="20%">'+
						'<h3 class="carName">'+item.name+'</h3>'+
						'<p>'+item.sn+'</p>'+
					'</td>'+
					'<td width="30%">'+
						'<p>状态：'+item.status+' '+item.power+'</p>'+
						//'<p><a onclick="openMap(\''+item.sn+'\')">位置：维度'+item.latitude+' 经度'+item.longitude+' 点击查看>> </a></p>'+
					'</td>'+
					'<td width="20%">'+
						'<p>速度：'+item.speed+'km/h</p>'+
						'<p>当日里程：'+item.todaymileage+'km</p>'+
						'<p>总里程：'+item.totalmileage+'km</p>'+
					'</td>'+
					'<td width="30%">'+
						'<div class="open">'+
						'<span onclick="select(\''+item.sn+'\',\''+item.name+'\')">选择</span>'+
						'</div>'+
					'</td>'+
					'</tr>'
				);

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

function searchCar() {
	getData(1);
}

///  关闭当前页面
function select(sn,name) {

	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);


	parent.setCar(sn,name);
}