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
						'<p><a onclick="openMap(\''+item.sn+'\')">位置：维度'+item.latitude+' 经度'+item.longitude+' 点击查看>> </a></p>'+
					'</td>'+
					'<td width="20%">'+
						'<p>速度：'+item.speed+'km/h</p>'+
						'<p>当日里程：'+item.todaymileage+'km</p>'+
						'<p>总里程：'+item.totalmileage+'km</p>'+
					'</td>'+
					'<td width="30%">'+
						'<div class="open">'+
						'<span class="'+(item.powerflag=='Y'?'battery':'battery-no')+'" onclick="power(\''+item.sn+'\',\''+item.powerflag+'\')"></span>'+
						'<span class="restar" onclick="restart(\''+item.sn+'\')"></span>'+
					((isDelete=='N')?'':'<span class="delete" onclick="deleteCar(\''+item.sn+'\')"></span>')+
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

///  打开地图查看，车辆位置
function openMap(sn) {
	//iframe窗

	layer.open({
		type: 2,
		title: '查看车辆位置',
		shadeClose: true,
		shade: false,
		maxmin: false, //开启最大化最小化按钮
		area: ['1400px', '960px'],
		content: ['/backstage/openmap?sn='+sn,'no']
	});


}

/**
 * 添加车辆
 */
function addCar() {

}

/**
 * 通电： 恢复通电或者断电
 * @param sn
 * @param powerflag
 */
function power(sn,powerflag){

	var title = '';
	if(powerflag=='Y'){
		// 当前是通电状态， 点击表示要进行断电操作
		title = '确定进行断电操作？';
	}else{
		//  点击表示 恢复通电操作
		title = '确定恢复通电操作？';
	}

	layer.confirm(title, {
			btn: ['确定','取消'] //按钮
		},
		function(){
			var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
			}); //0代表加载的风格，支持0-2
			$.ajax({
				type : 'POST',
				url : '/backstage/changeStatus',
				data : {
					'status' :(powerflag=='Y'?'N':'Y'),
					'sn' : sn,
					'fieldName' : 'power'
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
		},
		function(){
			/// 关闭
			layer.closeAll();
		});
}
/**
 *  重启
 * @param sn
 */
function restart(sn) {


	layer.confirm('确定重启？', {
		btn: ['确定','取消'] //按钮
	}, function(){
		var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
									}); //0代表加载的风格，支持0-2
		$.ajax({
			type : 'POST',
			url : '/backstage/changeStatus',
			data : {
				'status' :'Y',
				'sn' : sn,
				'fieldName' : 'restartflag'
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
		//layer.msg('的确很重要', {icon: 1});
	}, function(){
		/// 关闭
		layer.closeAll();

	});



}
/**
 *  删除车辆
 * @param sn
 */
function  deleteCar(sn) {

	layer.confirm('确定删除？', {
		btn: ['确定','取消'] //按钮
	}, function(){
		var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
		}); //0代表加载的风格，支持0-2
		$.ajax({
			type : 'POST',
			url : '/backstage/deleteCar',
			data : {
				'sn' : sn
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