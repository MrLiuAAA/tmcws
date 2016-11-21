var showToolPic=function(){
    xOffset = 10;
    yOffset = 30;
    $("a.pictooltip").mouseover(function (e) {
        this.myTitle = this.title;
        this.title = "";
        var imgTitle = this.myTitle ? "<br/>" + this.myTitle : "";
        var tooltip = "<div id='screenshot'><img width='250px' height='250px' src='" + this.href + "' alt='图片预览'/>" + imgTitle + "<\/div>"; //创建 div 元素
        $("body").append(tooltip); //把它追加到文档中
        $("#screenshot")
.css({
    "top": (e.pageY - xOffset) + "px",
    "left": (e.pageX + yOffset) + "px"
}).show("fast"); //设置x坐标和y坐标，并且显示
    }).mouseout(function () {
        this.title = this.myTitle;
        $("#screenshot").remove(); //移除
    }).mousemove(function (e) {
        $("#screenshot")
.css({
    "top": (e.pageY - xOffset) + "px",
    "left": (e.pageX + yOffset) + "px"
});
    });
};

var showToolText = function () {
    xOffset = 10;
    yOffset = 30;
    $(".tooltipText").mouseover(function (e) {
        this.myTitle = this.title;
        this.title = "";
        var imgTitle = this.myTitle ? "<br/>" + this.myTitle : "";
        var tooltip = "<div id='screenshot' style='width:300px;'>" + $(this).attr("vtext") + "<\/div>"; //创建 div 元素
        $("body").append(tooltip); //把它追加到文档中
        $("#screenshot")
.css({
    "top": (e.pageY - xOffset) + "px",
    "left": (e.pageX + yOffset) + "px"
}).show("fast"); //设置x坐标和y坐标，并且显示
    }).mouseout(function () {
        this.title = this.myTitle;
        $("#screenshot").remove(); //移除
    }).mousemove(function (e) {
        $("#screenshot")
.css({
    "top": (e.pageY - xOffset) + "px",
    "left": (e.pageX + yOffset) + "px"
});
    });
};

function setCookie(name, value) { var Days = 10; var exp = new Date(); exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000); document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString(); } 

function getCookie(name) {
     var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)"); 
     if(arr=document.cookie.match(reg)) 
     return unescape(arr[2]);
     else
     return null;
} 

function delCookie(name) { var exp = new Date(); exp.setTime(exp.getTime() - 1); var cval=getCookie(name); if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString(); } 