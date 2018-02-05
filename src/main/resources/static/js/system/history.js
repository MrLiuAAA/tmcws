$(function() {
	
	var marker, lineArr = [];
	
	var marleft = $(window).width() - 310 - 1280;
	if (marleft > 0)
		marleft = marleft / 2;
	else
		marleft = 0;
	$("#div-PlayHistory").offset({
		top : $(parent.window).height() - 210,
		left : marleft
	});

	$(parent.window).scroll(
			function() {
				$("#div-PlayHistory").offset(
						{
							top : $(parent.window).scrollTop()
									+ $(parent.window).height() - 210,
							left : marleft
						});
			});

	$("#Startbtn").click(function() {
		getTrajectory();
	});

	$("#Pausebtn").click(function() {
		marker.stopMove();
		$(this).hide();
		$("#Startbtn").show();
	});

	/**
	 * 获取用户的车辆
	 */
	 $.ajax({type:'POST',url:'/backstage/getmycars',data:{},
        dataType:'json',success:function(data, textStatus) {
        	var result=data.obj;
        	 /*<![CDATA[*/
        	
        	$("#mycar").empty().append("<option value=''>--请选择--</option>");
        	$.each(result, function (i, item) {
      	　　　　　　$("<option></option>").val(item.sn).text(item.name).appendTo($("#mycar"));
      	　　　　});
      		}
        	 /*]]>*/
        	
        });
	
	 	/**
		 *  获取 时间段内的 轨迹
		 */
	 var org_sn,org_startTime,org_endTime;
	 
	function getTrajectory(){
		
		var sn = $("#select_car_id").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		
		if(sn==''){
			alert("请选择车辆");
			return;
		}
		if(startTime==''){
			alert("请选择开始时间");
			return;
		}
		if(endTime==''){
			alert("请选择结束时间");
			return;
		}
		
		if(sn==org_sn
				&&startTime==org_startTime
				&&endTime==org_endTime
				&&lineArr.length>=0){
			/// 三者都一样 不去查询
			///alert('一样');
			marker.moveAlong(lineArr, 100);
			$("#Startbtn").hide();
			$("#Pausebtn").show();
		}else{
			org_sn = sn;
			org_startTime=startTime;
			org_endTime = endTime;
			$.ajax({type:'POST',url:'/backstage/getTrajectory',data:{'sn':sn,'startTime':startTime,'endTime':endTime},
		        dataType:'json',success:function(data, textStatus) {
			        	var result=data.obj;
			        	
			        	var gps = result.gps;
			        	 /*<![CDATA[*/
			        	if(gps==null || gps.length ==0){
			        		alert('该时间段没有数据！请重新选择');
			        		return;
			        	}
			        	
			        	$.each(gps, function (i, item) {
				      	　　　　　　var lngX = item.longitude, latY = item.latitude;
				      			  lineArr.push([ lngX, latY ]);
				      	　　　　});
			        	map.clearMap();
			        	marker = new AMap.Marker({
			    			map : map,
			    			position : lineArr[0],
			    			icon : "http://webapi.amap.com/images/car.png",
			    			offset : new AMap.Pixel(-26, -13),
			    			autoRotation : true
			    		});
			        	// 绘制轨迹
			    		var polyline = new AMap.Polyline({
			    			map : map,
			    			path : lineArr,
			    			strokeColor : "#00A", //线颜色
			    			strokeOpacity : 1, //线透明度
			    			strokeWeight : 3, //线宽
			    			strokeStyle : "solid" //线样式
			    		});
			    		map.setFitView();
			        	
			    		$("#guijigaikuang").html("<p><span>最大速度:</span>"+result.maxspeed+" Km/h </p> "
			    								+"<p><span>最小速度:</span>"+result.minspeed+" Km/h </p> "
			    								+"<p><span>平均速度:</span>"+result.averagespeed+" Km/h </p>"
			    								+"<p><span>行驶总里程:</span>"+result.mileage+" Km </p>"
			    								+"<p><span>行驶总时间:</span>"+result.runningtime+"</p>");

			    		 /*]]>*/
			    		
			    		marker.moveAlong(lineArr, 100);
			    		$("#Startbtn").hide();
						$("#Pausebtn").show();
		      		}
		        	
		        	
		        });
			
		}
		
		
		 
		
	}



});
///  选择车辆 iframe
function selectCar() {
	//iframe窗

	layer.open({
		type: 2,
		title: '选择车辆',
		shadeClose: true,
		shade: false,
		maxmin: false, //开启最大化最小化按钮
		area: ['1000px', '950px'],
		content: ['/backstage/selectcar','no'],

	});




}

function setCar(sn,name){
	$("#select_car_name").html(name+'('+sn+')');
	$("#select_car_id").val(sn);
	$("#select_car_btn").html('重新选择');
}