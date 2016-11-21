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
		
		var sn = $("#mycar").val();
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
			alert('一样');
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
			        	
			    		$("#guijigaikuang").html("最大速度:"+result.maxspeed+" Km/h <br/> "
			    								+"最小速度:"+result.minspeed+" Km/h <br/> "
			    								+"平均速度:"+result.averagespeed+" Km/h <br/>"
			    								+"行驶总里程:"+result.mileage+" Km <br/>"
			    								+"行驶总时间:"+result.runningtime);
			    		
			    		 /*]]>*/
			    		
			    		marker.moveAlong(lineArr, 100);
			    		$("#Startbtn").hide();
						$("#Pausebtn").show();
		      		}
		        	
		        	
		        });
			
		}
		
		
		 
		
	}
	
	var map = new AMap.Map("container", {
		resizeEnable : true,
		center : [ 116.397428, 39.90923 ],
		zoom : 17
	});
	AMap.plugin(['AMap.ToolBar', 'AMap.Scale', 'AMap.OverView'],
	        function () {
 			map.addControl(new AMap.ToolBar());
 			map.addControl(new AMap.Scale());
 			map.addControl(new AMap.OverView({ isOpen: true }));}
			); 
	map.on("complete", completeEventHandler);
	AMap.event.addDomListener(document.getElementById('start'), 'click',
			function() {
				marker.moveAlong(lineArr, 500);
			}, false);
	AMap.event.addDomListener(document.getElementById('stop'), 'click',
			function() {
				marker.stopMove();
			}, false);

	// 地图图块加载完毕后执行函数
	function completeEventHandler() {
		//$("#guijigaikuang").html("ddddddd");
	}
});
