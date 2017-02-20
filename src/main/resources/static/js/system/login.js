//刷新验证码
function getVerifyCode() {
    $("#yzmpic").attr("src", "verifyCode/slogin.do?random=" + Math.random());
}

function dialogerror(Str){
    $("#jyErrorStr").empty().append(Str);

    $("#jyError").removeClass('hide').dialog({
        resizable: false,
        dialogClass: "title-no-close",
        modal: true,//设置为true，该dialog将会有遮罩层
        title: "<div class='widget-header'><h4 class='font-bold red' >错误</h4></div>",
        title_html: true,
        show:{effect:"shake",duration: 100},
        hide:{effect:"explode"},
        buttons: [{
            html: "<i class='icon-ok bigger-110'></i>&nbsp;确认","class" : "btn btn-primary btn-xs",
            click: function() {
                $(this).dialog("close");
            }
        }]
    });
};
function dialogloading(){
    $("#jyLoadingStr").empty().append("正在登录 , 请稍后 ...");
    $("#jyLoading").removeClass('hide').dialog({
        dialogClass: "loading-no-close",
        minHeight: 50,
        resizable: false,
        modal: true,
        show:{effect:"fade"},hide:{effect:"fade"}
    });
};
function dialogloadingClose(){
    $("#jyLoading").dialog("close");
};

$(function () {
    getVerifyCode();
    $("#yzmpic").click(function () {
        getVerifyCode();
    });

    //登录
    $("#loginBtn").click(function () {
        if("" == $("#username").val()){
            $("#username").focus();
        }else if("" == $("#password").val()){
            $("#password").focus();
        }else if("" == $("#yzm").val()){
            $("#yzm").focus();
        }else{
            var loginname = $("#username").val();
            var password = $("#password").val();
            var verifyCode=$("#yzm").val();
            //dialogloading();
            var index = layer.load(0, {shade: [0.3,'#fff'] //0.1透明度的白色背景
            }); //0代表加载的风格，支持0-2

            $.ajax({type:'POST',url:'/system_login',data:{'username':loginname,'password':password,'verifyCode':verifyCode},
                dataType:'json',success:function(data, textStatus) {
                    //dialogloadingClose();
                    layer.close(index);
                    var result=data.result;
                    if ("success" != result) {  //如果登录不成功，则再次刷新验证码

                        //loginAlert(result);

                        layer.msg(result, {
                            time: 2000 //2s后自动关闭

                        });

                        getVerifyCode();
                    }else{
                        window.location.href="/backstage/index";
                    }
                }
            });
        }
    });
});