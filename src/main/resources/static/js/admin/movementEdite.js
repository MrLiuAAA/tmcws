/**
 * Created by 夜郎人 on 2017/1/20.
 */
$(document).ready(function(){
    var um = UM.getEditor('myEditor',{
        readonly : false
    });
    um.ready(function() {
        editor.execCommand('redo');
    })
})
