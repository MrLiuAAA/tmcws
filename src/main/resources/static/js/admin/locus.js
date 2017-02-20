/**
 * Created by 夜郎人 on 2017/2/10.
 */
/*公共js计算页面的宽度*/
$(document).ready(function(){
    $(".content-wrapper").css({'width':$(document).width()-230,'height':$(document).height()});
    $(window).resize(function () {
        var height=$(document).height();
        var width=$(document.body).width()-230;
        $(".content-wrapper").css({'width':width,'height':height});
        init();
    });
    //点击切换；
    $(".openImg img").on('click',function(){
        $(this).attr('src','../images/pause.png');
    })
})
/*加载地图*/
function init(){
    var map = new AMap.Map('points', {
        center: [117.000923, 36.675807],
        zoom: 6
    });
    map.plugin(["AMap.ToolBar"], function() {
        map.addControl(new AMap.ToolBar());
    });
}
