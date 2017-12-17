//刷新验证码
function getVerifyCode() {
    $("#yzmpic").attr("src", "verifyCode/slogin.do?random=" + Math.random());
}
function loginSys() {
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
}



$(function () {
    getVerifyCode();
    $("#yzmpic").click(function () {
        getVerifyCode();
    });
    ///  回车事件
    document.onkeydown = function(e){
        var ev = document.all?window.event:e;
        if(ev.keyCode==13) {
            loginSys();
        }
    }


    //登录
    $("#loginBtn").click(function () {
            loginSys();
    });
});