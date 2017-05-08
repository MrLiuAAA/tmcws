$(function() {


	/**
	 * 获取消息
	 */

	getData(1);

});

function getData(page) {

	// var keyword = $("#keyword").val();
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({type:'POST',url:'/backstage/getPushNews',data:{'page':page},
		dataType:'json',success:function(data, textStatus) {

			layer.close(index);


			var result=data.obj.list;
			var pageInfo=data.obj.pageInfo;
			// / 先清空
			$("#dataList").html("");

			$.each(result, function (i, item) {

				$("#dataList").append(
					'<tr>'+
						'<td width="70%">'+
							'<h3 class="name">'+item.title+'</h3>'+

						'</td>'+
						'<td width="20%">'+
							'<p>'+item.createTime+'</p>'+

						'</td>'+
						'<td width="10%">'+
							'<div class="open">'+
							'<span class="delete" onclick="deletePushNews(\''+item.id+'\')"></span>'+
							'</div>'+
						'</td>'+
					'</tr>'
				);

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


function searchCar() {
	getData(1);
}


/**
 *  删除车辆
 * @param sn
 */
function  deletePushNews(id) {

	layer.confirm('确定删除？', {
		btn: ['确定','取消'] //按钮
	}, function(){
		var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
		}); //0代表加载的风格，支持0-2
		$.ajax({
			type : 'POST',
			url : '/backstage/deletePushNews',
			data : {
				'id' : id
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