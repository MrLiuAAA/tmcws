function changeStatus(btn) {

	var id = $(btn).attr('id');

	var status = $(btn).is(':checked');
	if (id == 'zdbj') {
		fieldName = 'shockalert';
	} else if (id == 'jxbj') {
		fieldName = 'cuttinglinealert';
	} else if (id == 'zdsf') {
		fieldName = 'autoalert';
	}
	var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
	}); //0代表加载的风格，支持0-2
	$.ajax({
		type : 'POST',
		url : '/backstage/changeStatus',
		data : {
			'status' : status ? 'Y' : 'N',
			'sn' : $('#sn').val(),
			'fieldName' : fieldName
		},
		dataType : 'json',
		success : function(data, textStatus) {
			layer.close(index);
			var result = data.result;
			if ("success" != result) {
				layer.msg(result, {
					time: 2000 //2s后自动关闭

				});
			} else {
				window.location.reload();//刷新当前页面.
			}
		}
	});
}
