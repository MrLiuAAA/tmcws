$(function () {
    $("#weixin").click(function () {

        $.layer({
            type: 1,
            title: false,
            area: ['auto', 'auto'],
            border: [0], //去掉默认边框
            shade: [0.4, '#000'], //去掉遮罩
            closeBtn: [0, true], //去掉默认关闭按钮
            shift: 'left', //从左动画弹出
            page: {
                html: '<div style="width:300px; height:300px; padding:0px; border:1px solid #ccc; background-color:#eee;" align="center"><p style="margin:0px auto;"><img src="images/wx.jpg" width="300px" height="300px" ></p></div>'
            }
        });

    });

});