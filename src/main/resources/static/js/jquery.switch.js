/*
ios switch button
version:v0.1
EditDate:2016-10-29



*/

(function ($) {
    $.fn.SwitchButton = function (options) {
        var defaults = {
            width: 90,
            Height: 40
        };
        var opts = $.extend(defaults, options);
        var checkbox = this;
        var CheckState = checkbox.prop("checked");
        var $swBtn = $("<div class='switch-btn'><div class='switch-inner-btn'></div></div>");
        //$swBtn.offset({ left: checkbox.offset().left, top: checkbox.offset().top });
        $swBtn.appendTo(checkbox.parent());

        checkbox.hide();
        


        var _btn = $swBtn.find(".switch-inner-btn");

        //初始化
        if (CheckState == false) {
            _btn.stop().animate({ "left": 0 }, 500);
            $swBtn.removeClass("switch-on");
            $swBtn.addClass("switch-off");
        }
        else {
            _btn.stop().animate({ "left": $(".switch-btn").width() - 40 }, 500);
            $swBtn.removeClass("switch-off");
            $swBtn.addClass("switch-on");

        }

        $swBtn.val(CheckState);
		
        _btn.click(function() {
            if (CheckState == true) {
                $(this).stop().animate({ "left": 0 }, 500);
                $(this).removeAttr("right");
                $swBtn.removeClass("switch-on");
                $swBtn.addClass("switch-off");
                CheckState = false;
                checkbox.prop("checked",false);
            }
            else {
                $(this).stop().animate({ "left": $(".switch-btn").width() - 40 }, 500);
                $swBtn.removeClass("switch-off");
                $swBtn.addClass("switch-on");
                CheckState = true;
                checkbox.prop("checked",true);
            }
			$swBtn.val(CheckState);
        });
		
		$swBtn.bind("click");
		  
       
        return $swBtn;
    };
})(jQuery);