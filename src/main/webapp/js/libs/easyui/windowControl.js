(function ($) {
	function S4() {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	}
	function CreateIndentityWindowId() {
		return "UUID-" + (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
	}
	function destroy(target) {
		$(target).dialog("destroy");
	}
	function getWindow(target) {
		if (typeof target == "string") {
			return document.getElementById(target);
		} else {
			return $(target).closest(".window-body");
		}
	}
	$.createWin = function (options) {
		if (!options.url && !options.contents) {
			$.messager.alert("提示", "缺少必要参数!(url or contents)");
			return false;
		}
		var windowId = CreateIndentityWindowId();
		if (options.winId) {
			windowId = options.winId;
		} else {
			options.winId = windowId;
		}
		if(options.buttons == 'none') {
			options.buttons = '';
		} else {
			var defaultBtn = [{
					text : '关闭',
					handler : function () {
						$("#" + windowId).dialog("close");
					}
				}
			];
			$.merge(options.buttons || [], defaultBtn);
		}
		
		options = $.extend({}, $.createWin.defaults, options || {});
		if (options.isMax) {
			options.draggable = false;
			options.closed = true;
		}
		var dialog = $('<div/>');
		if (options.target != 'body') {
			options.inline = true;
		}
		dialog.appendTo($(options.target));
		dialog.dialog($.extend({}, options, {
				onClose : function () {
					if (typeof options.onClose == "function") {
						options.onClose.call(dialog,$);
					}
					destroy(this);
				},
				onMove : function (left, top) {
					if (typeof options.onMove == "function") {
						options.onMove.call(dialog,$);
					}
					var o = $.data(this, 'panel').options;
					if (top < 0) {
						$(this).dialog("move", {
							"left" : left,
							"top" : 0
						});
					} else if (o.maximized) {
						$(this).dialog("restore");
						$(this).dialog("move", {
							"left" : left + 100,
							"top" : top
						});
					}
					if (top > ($(o.target).height() - 20)) {
						$(this).dialog("move", {
							"left" : left,
							"top" : ($(o.target).height() - 25)
						});
					}
				}
			}));
		if (options.align) {
			var w = dialog.closest(".window");
			switch (options.align) {
			case "right":
				dialog.dialog("move", {
					left : w.parent().width() - w.width() - 10
				});
				break;
			case "tright":
				dialog.dialog("move", {
					left : w.parent().width() - w.width() - 10,
					top : 0
				});
				break;
			case "bright":
				dialog.dialog("move", {
					left : w.parent().width() - w.width() - 10,
					top : w.parent().height() - w.height() - 10
				});
				break;
			case "left":
				dialog.dialog("move", {
					left : 0
				});
				break;
			case "tleft":
				dialog.dialog("move", {
					left : 0,
					top : 0
				});
				break;
			case "bleft":
				dialog.dialog("move", {
					left : 0,
					top : w.parent().height() - w.height() - 10
				});
				break;
			case "top":
				dialog.dialog("move", {
					top : 0
				});
				break;
			case "bottom":
				dialog.dialog("move", {
					top : w.parent().height() - w.height() - 10
				});
				break;
			}
		}
		if (options.isMax) {
			dialog.dialog("maximize");
			dialog.dialog("open");
		}
		if ($.fn.mask && options.mask)
			dialog.mask();
		if (options.contents) {
			ajaxSuccess(options.contents);
		} else {
			if (!options.isIframe) {
				$.ajax({
					url : options.url,
					type : options.ajaxType || "GET",
					data : options.data == null ? "" : options.data,
					success : function (date) {
						ajaxSuccess(date);
					},
					error : function () {
						//$.messager.alert("提示", "加载失败！", 'error', function(){$("#" + windowId).dialog("close");});
						if ($.fn.mask && options.mask)
							dialog.mask("hide");
					}
				});
			} else {
				ajaxSuccess();
			}
		}
		dialog.attr("id",windowId);
		return dialog;
		function ajaxSuccess(date) {
			if (options.isIframe && !date) {
				dialog.find("div.dialog-content").html('<div style="overflow:hidden;height:'+dialog.find("div.dialog-content").height()+'px"><iframe width="100%" height="100%" frameborder="0" id="'+windowId+'_Iframe" src="' + options.url + '" ></iframe></div>');
			} else {
				dialog.find("div.dialog-content").html(date);
			}
			$.parser.parse(dialog);
			options.onComplete.call(this, dialog,$);
			if ($.fn.mask && options.mask)
				dialog.mask("hide");
		}
	};
	$.fn.destroy = function () {
		destroy(this);
	};
	window.GETWIN = getWindow;
	$.createWin.defaults = $.extend({}, $.fn.dialog.defaults, {
			url : '',
			data : '',
			ajaxType:"GET",
			target : 'body',
			height : 200,
			width : 400,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : true,
			modal : true,
			shadow : false,
			mask : true,
			onComplete : function (dialog,jq) {}
		});
})(jQuery);


/* 
 *  Document   : mask 1.1
 *  Created on : 2011-12-11, 14:37:38
 *  Author     : GodSon
 *  Email      : wmails@126.com
 *  Link       : www.btboys.com 
 *  
 */
 
(function($){
    function init(target,options){
        var wrap = $(target);
		if($("div.datagrid-mask",wrap).length) wrap.mask("hide");
		
        wrap.attr("position",wrap.css("position"));
		wrap.attr("overflow",wrap.css("overflow"));
        wrap.css("position", "relative");
		wrap.css("overflow", "hidden");
        
        var maskCss = {
            /*position:"absolute",
            left:0,
            top:0,
			cursor: "wait",
            background:"#ccc",
            opacity:options.opacity,
            filter:"alpha(opacity="+options.opacity*100+")",
            display:"none"*/
        };
        
        var maskMsgCss = {
            /*position:"absolute",
            width:"auto",
            padding:"10px 20px",
            border:"2px solid #ccc",
            color:"white",
			cursor: "wait",
            display:"none",
            borderRadius:5,
            background:"black",
            opacity:0.6,
            filter:"alpha(opacity=60)"*/
        };
		var width,height,left,top;
		if(target == 'body'){
			width = Math.max(document.documentElement.clientWidth, document.body.clientWidth);
			height = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
		}else{
			width = wrap.outerWidth() || "100%";
			height = wrap.outerHeight() || "100%";
		}
        $('<div class="datagrid-mask"></div>').css($.extend({},maskCss,{
            display : 'block',
            width : width,
            height : height,
            zIndex:options.zIndex
        })).appendTo(wrap);

		var maskm= $('<div class="datagrid-mask-msg"></div>').html(options.maskMsg).appendTo(wrap).css(maskMsgCss);
		
		if(target == 'body'){
			left = (Math.max(document.documentElement.clientWidth,document.body.clientWidth) - $('div.datagrid-mask-msg', wrap).outerWidth())/ 2;
			if(document.documentElement.clientHeight > document.body.clientHeight){
				top = (Math.max(document.documentElement.clientHeight,document.body.clientHeight)  - $('div.datagrid-mask-msg', wrap).outerHeight())/ 2;
			}else{
				top = (Math.min(document.documentElement.clientHeight,document.body.clientHeight)  - $('div.datagrid-mask-msg', wrap).outerHeight())/ 2;
			}
			
		}else{
			left = (wrap.width() - $('div.datagrid-mask-msg', wrap).outerWidth())/ 2;
			top = (wrap.height() - $('div.datagrid-mask-msg', wrap).outerHeight())/ 2;
		}
		
        maskm.css({
            display : 'block',
            zIndex:options.zIndex+1,
            left : left,
            top :  top
        });
        
        setTimeout(function(){
            wrap.mask("hide");
        }, options.timeout);
            
        return wrap;
    }
       
    $.fn.mask = function(options){   
        if (typeof options == 'string'){
            return $.fn.mask.methods[options](this);
        }
        options = $.extend({}, $.fn.mask.defaults,options);
        return init(this,options);
    };
    $.mask = function(options){  
        if (typeof options == 'string'){
            return $.fn.mask.methods[options]("body");
        }
        options = $.extend({}, $.fn.mask.defaults,options);
        return init("body",options);
    };
	
	$.mask.hide = function(){
		$("body").mask("hide");
	};
	
    $.fn.mask.methods = {
        hide : function(jq) {
            return jq.each(function() {
                var wrap = $(this);
                $("div.datagrid-mask",wrap).fadeOut(function(){
                    $(this).remove();
                });
                $("div.datagrid-mask-msg",wrap).fadeOut(function(){
                    $(this).remove();
                    wrap.css("position",  wrap.attr("position"));
					wrap.css("overflow", wrap.attr("overflow"));
                });
            });
        }
    };
    
    $.fn.mask.defaults = {
        maskMsg:'\u52a0\u8f7d……',
        zIndex:100000,
        timeout:30000,
        opacity:0.6
    };
})(jQuery);