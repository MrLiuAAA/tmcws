$(document).ready(function(){
	$("#user").on('click',function(){
		layer.open({
			  type: 1,
			  title:'上传头像',
			  skin: 'layui-layer-rim', //加上边框
			  area: ['440px', '440px'], //宽高
			  content: $(".avatar")
			});
	});
	upladImg();//图片上传
});
function upladImg(){
	var options = {
		thumbBox: '.thumbBox',
		spinner: '.spinner',
		imgSrc:$('#user')[0].src
	}
	var cropper = $('.imageBox').cropbox(options);
	$('#file').on('change', function() {
		var reader = new FileReader();
		reader.onload = function(e) {
			options.imgSrc = e.target.result;
			cropper = $('.imageBox').cropbox(options);
		}
		reader.readAsDataURL(this.files[0]);
		// this.files[0]= [];
		this.files = [];
	})
	$('#btnCrop').on('click', function() {
		var img = cropper.getDataURL();
		// $('#user').attr({'src':img});
		// layer.closeAll();

		uploadImg(img);




	})
	$('#btnZoomIn').on('click', function() {
		cropper.zoomIn();
	})
	$('#btnZoomOut').on('click', function() {
		cropper.zoomOut();
	})
}

//点击保存到后台 加回显
function uploadImg(imgStr){

	$.ajax({
		type: 'post',
		url: '/backstage/uploadImg',
		dataType: 'json',
		data:{imgStr :imgStr},

		success: function (result) {
			$('#user').attr({'src':imgStr});
			layer.closeAll();
		}
	});
}

// function PreviewImage(){
//
//     //
// 	// var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
// 	// }); //0代表加载的风格，支持0-2
//
// 	$.ajaxFileUpload({
// 		url: '/backstage/uploadImg',
// 		type: 'post',
// 		data : {
// 			keyword : '啊啊啊啊啊'
// 		},
// 		secureuri: false, //一般设置为false
// 		fileElementId: 'file', // 上传文件的id、name属性名
// 		dataType: 'JSON', //返回值类型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
// 		success: function(data, status){
// 			alert(data);
// 		},
// 		error: function(data, status, e){
// 			alert(e);
// 		}
// 	});
//
// }
