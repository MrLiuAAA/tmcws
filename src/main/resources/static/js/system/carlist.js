$(function() {
	var current_page_num = 1;
	getData(1);
	$("#inputcar_div").on('click',function(){

		$('#importFile').val('');

		layer.open({
			type: 1,
			title:'导入车辆',
			skin: 'layui-layer-rim', //加上边框
			area: ['300px', '260px'], //宽高
			content: $("#import_div")
		});
	});
	$("#import_cansle").on('click',function(){
		layer.closeAll();
	});

	$("#import_submit").on('click',function(){

		var loginname = $.trim($("#loginname").val());
		var ii = layer.load();
		$.ajaxFileUpload({
			url: '/backstage/importExcel',
			type: 'post',
			data : {
				loginname : loginname
			},
			secureuri: false, //一般设置为false
			fileElementId: 'importFile', // 上传文件的id、name属性名
			dataType: 'JSON', //返回值类型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
			success: function(data, status){
				data = $.parseJSON(data.replace(/<.*?>/ig,""));
				layer.close(ii);

				var result = data.result;
				if ("success" != result) { //
					layer.msg(data.msg, {
						time: 2000 //2s后自动关闭

					});
				} else {
					layer.closeAll();
					window.location.reload();//刷新当前页面
				}

			},
			error: function(data, status, e){
				layer.close(ii);
				layer.msg(data.msg, {
					time: 2000 //2s后自动关闭

				});

			}
		});


	});
});

function getData(page) {
	current_page_num = page;
	var keyword = $("#keyword").val();
	var loginname = $("#loginname").val();
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({type:'POST',url:'/backstage/getmycarlistinfo',data:{'page':page,'keyword':keyword,'loginname':loginname},
		dataType:'json',success:function(data, textStatus) {

			layer.close(index);

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
						'<p>位置：维度'+item.latitude+' 经度'+item.longitude+'</p>'+
					'</td>'+
					'<td width="20%">'+
						'<p>速度：'+item.speed+'km/h</p>'+
						'<p>当日里程：'+item.todaymileage+'km</p>'+
						'<p>总里程：'+item.totalmileage+'km</p>'+
					'</td>'+
					'<td width="30%">'+
						'<div class="open">'+
						// '<span class="'+(item.powerflag=='Y'?'battery':'battery-no')+'" onclick="power(\''+item.sn+'\',\''+item.powerflag+'\')"></span>'+
						// '<span class="restar" onclick="restart('+item.sn+')"></span>'+
						// '<span class="delete" onclick="deleteCar('+item.sn+')"></span>'+
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
