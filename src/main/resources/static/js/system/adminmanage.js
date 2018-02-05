$(function() {
	var current_page_num = 1;
	getData(1);

});

function getData(page) {
	current_page_num = page;
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({type:'POST',url:'/backstage/getadminmembers',data:{'page':page},
		dataType:'json',success:function(data, textStatus) {

			layer.close(index);

			var result=data.obj.list;
			var pageInfo=data.obj.pageInfo;
			// / 先清空
			$("#dataList").html("");

			$.each(result, function (i, item) {

				$("#dataList").append(
					'<tr>'+
						'<td width="60%">'+
							'<h3 class="carName">登录名：'+item.loginname+' <br/>  姓名:'+item.name+'</h3>'+
						'</td>'+
						'<td width="40%">'+
							'<div class="manage">'+
								'<span class="delete" onclick="deleteAdmin(\''+item.loginname+'\')"></span>'+
								'<span class="carInfo" onclick="showCarList(\''+item.loginname+'\')"></span>'+
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
			// /// 分页信息
			// $("#pagination").html("");
            //
			// $("#pagination").append(
			// 	'<li><a href="#">共'+pageInfo.pages+'页</a></li>'
			// 	+(pageInfo.hasPreviousPage?'<li><a href="javascript:getData('+pageInfo.prePage+')">上一页</a></li>':'')
			// 	+'<li><a href="#">'+page+'</a></li>'
			// 	+(pageInfo.hasNextPage?'<li><a href="javascript:getData('+pageInfo.nextPage+')">下一页</a></li>':'')
            //
			// );
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
function showCarList(loginname) {
	window.location.href = '/backstage/showcarlist?loginname='+loginname;
}
function deleteAdmin(loginname) {
	layer.confirm('确定删除？', {
		btn: ['确定','取消'] //按钮
	}, function(){
		var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
		}); //0代表加载的风格，支持0-2
		$.ajax({
			type : 'POST',
			url : '/backstage/deleteAdmin',
			data : {
				'loginname' : loginname
			},
			dataType : 'json',
			success : function(data, textStatus) {
				layer.close(index);
				var result = data.result;
				if ("success" != result) { // 失败
					layer.msg(result, {
						time: 2000 //2s后自动关闭

					});
				} else {
					window.location.reload();//刷新当前页面.
				}
			}
		});


	}, function(){
		/// 关闭
		layer.closeAll();

	});
}