//全局变量
var GV = {
	VERSION:"1.0.20131226",
	MESSAGER_TITLE:'信息提示',
	URL : {
		LOGIN:'' //登录页面地址
	}
};

var IdCard = {
	Wi : [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ],
	ValideCode : [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ],
	IdCardValidate : function(idCard) {
		idCard = $.trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
	    if (idCard.length == 15) {   
	        return this.isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
	    } else if (idCard.length == 18) {   
	        var a_idCard = idCard.split("");                // 得到身份证数组   
	        if(this.isValidityBrithBy18IdCard(idCard) && this.isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
	            return true;   
	        }else {   
	            return false;   
	        }
	    } else {   
	        return false;   
	    }
	},
	/**  
	 * 判断身份证号码为18位时最后的验证位是否正确  
	 * @param a_idCard 身份证号码数组  
	 * @return  
	 */ 
	isTrueValidateCodeBy18IdCard : function(a_idCard) {
		var sum = 0;                             // 声明加权求和变量   
	    if (a_idCard[17].toLowerCase() == 'x') {   
	        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
	    }   
	    for ( var i = 0; i < 17; i++) {   
	        sum += this.Wi[i] * a_idCard[i];            // 加权求和   
	    }   
	    var valCodePosition = sum % 11;                // 得到验证码所位置   
	    if (a_idCard[17] == this.ValideCode[valCodePosition]) {   
	        return true;   
	    } else {   
	        return false;   
	    }
	},
	/**  
	  * 验证18位数身份证号码中的生日是否是有效生日  
	  * @param idCard 18位书身份证字符串  
	  * @return  
	  */  
	isValidityBrithBy18IdCard : function(idCard18) {
		var year =  idCard18.substring(6,10);   
	    var month = idCard18.substring(10,12);   
	    var day = idCard18.substring(12,14);   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)   
	          ||temp_date.getMonth()!=parseFloat(month)-1   
	          ||temp_date.getDate()!=parseFloat(day)){   
	            return false;   
	    }else{   
	        return true;   
	    }
	},
	/**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */
	isValidityBrithBy15IdCard : function(idCard15) {
		var year =  idCard15.substring(6,8);   
		var month = idCard15.substring(8,10);   
		var day = idCard15.substring(10,12);   
		var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
		// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
		if(temp_date.getYear()!=parseFloat(year)   
			||temp_date.getMonth()!=parseFloat(month)-1   
			||temp_date.getDate()!=parseFloat(day)){   
			return false;   
	    } else {   
	        return true;   
	    }
	}
};


/*try {
	var _a = top.location.href;
	GV.top$ = top.$;
	GV.topWindow = top.window;
} catch(err) {
	GV.top$ = $;
	GV.topWindow = window;
}*/
GV.top$ = $;
GV.topWindow = window;

;(function(){
	$.ajaxSetup({
		cache:false, //禁用cache
		data:{}
	})
	
	//初始化easyui默认属性
	$.extend($.fn.datagrid.defaults, {
		striped:true,
		pageSize:20,
		pageList:[10,20,50,80],
		pagination:true,
		fitColumns:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		fit:true,
		onLoadError:function(data){
			/*if(data.status>=200 && data.status<300) {
				if(data.responseText.substr(0,1) == '{') {
					var responseText = eval('('+data.responseText+')');
				
					if(responseText.status!=undefined) {
						Cmessager.alert(GV.MESSAGER_TITLE, responseText.message, 'error');
					}
				}
			}*/
		}
	});
	/**
	* easyui自带下面的验证
	* email：匹配E-Mail的正则表达式规则。
	* url：匹配URL的正则表达式规则。
	* length[0,100]：允许在x到x之间个字符。
	* remote['http://.../action.do','paramName']：发送ajax请求需要验证的值，当成功时返回true。
	*/
	$.extend($.fn.validatebox.defaults.rules, {
		minLength : { // 判断最小长度
			validator : function(value, param) {
				return value.length >= param[0];
			},
			message : "最少输入 {0} 个字符。"
		},
		maxLength : { // 判断最大长度
			validator : function(value, param) {
				return value.length <= param[0];
			},
			message : "最多输入 {0} 个字符。"
		},
		phone : {// 验证电话号码
			validator : function(value) {
				return /^(((d{2,3}))|(d{3}-))?((0d{2,3})|0d{2,3}-)?[1-9]d{6,7}(-d{1,4})?$/i.test(value);
			},
			message : "格式不正确,请使用下面格式:020-88888888"
		},
		mobile : {// 验证手机号码
			validator : function(value) {
				return /^(13|15|18)d{9}$/i.test(value);
			},
			message : "手机号码格式不正确"
		},
		idcard : {// 验证身份证
			validator : function(value, param) {
				if(param.length > 0) {
					if($(param[0]).combobox('getValue') == '境内居民身份证') {
						return IdCard.IdCardValidate(value);
					}
				}
				return true;
				//return /^d{15}(d{2}[A-Za-z0-9])?$/i.test(value);
				
			},
			message : "身份证号码不正确"
		},
		intOrFloat : {// 验证整数或小数
			validator : function(value) {
				return /^d+(.d+)?$/i.test(value);
			},
			message : "请输入数字，并确保格式正确"
		},
		currency : {// 验证货币
			validator : function(value) {
				return /^d+(.d+)?$/i.test(value);
			},
			message : "货币格式不正确"
		},
		qq : {// 验证QQ,从10000开始
			validator : function(value) {
				return /^[1-9]d{4,9}$/i.test(value);
			},
			message : "QQ号码格式不正确"
		},
		integer : {// 验证整数
			validator : function(value) {
				return /^[+]?[1-9]+d*$/i.test(value);
			},
			message : "请输入整数"
		},
		chinese : {// 验证中文
			validator : function(value) {
				return /^[u0391-uFFE5]+$/i.test(value);
			},
			message : "请输入中文"
		},
		english : {// 验证英语
			validator : function(value) {
				return /^[A-Za-z]+$/i.test(value);
			},
			message : "请输入英文"
		},
		unnormal : {// 验证是否包含空格和非法字符
			validator : function(value) {
				return /.+/i.test(value);
			},
			message : "输入值不能为空和包含其他非法字符"
		},
		faxno : {// 验证传真
			validator : function(value) {
	//            return /^[+]{0,1}(d){1,3}[ ]?([-]?((d)|[ ]){1,12})+$/i.test(value);
				return /^(((d{2,3}))|(d{3}-))?((0d{2,3})|0d{2,3}-)?[1-9]d{6,7}(-d{1,4})?$/i.test(value);
			},
			message : "传真号码不正确"
		},
		zip : {// 验证邮政编码
			validator : function(value) {
				return /^[1-9]d{5}$/i.test(value);
			},
			message : "邮政编码格式不正确"
		},
		ip : {// 验证IP地址
			validator : function(value) {
				return /d+.d+.d+.d+/i.test(value);
			},
			message : "IP地址格式不正确"
		},
		same:{
			validator : function(value, param){
				if($("#"+param[0]).val() != "" && value != ""){
					return $("#"+param[0]).val() == value;
				} else {
					return true;
				}
			},
			message : "两次输入的密码不一致！"   
		}
	});
	
	/*
	 * 防止浏览器不支持console报错
	 */
	if(!window.console) {
		window.console = {};
		var funs = ["profiles", "memory", "_commandLineAPI", "debug", "error", "info", "log", "warn", "dir", "dirxml", "trace", "assert", "count", "markTimeline", "profile", "profileEnd", "time", "timeEnd", "timeStamp", "group", "groupCollapsed", "groupEnd"];
		for(var i = 0;i < funs.length; i++) {
			console[funs[i]] = function() {};
		}
	}
	
	//全局ajax处理
	$(document).ajaxError(function(event, request, settings, thrownError) {
		//关闭弹出层
		var _window = GV.top$('body').children('.window');
		if(_window.length > 0) {
			_window.each(function(index, domEle) {
				var window_body = GV.top$(this).children('.window-body');
				if(window_body.length) {
					var winId = GV.top$(window_body[0]).attr('id');
					if(winId.indexOf('UUID-') === 0) {
						GV.top$('#'+winId).dialog('close');
					}
				}
			});
		}
		
		enableBtn();
		//请求失败处理
		if(request.status == 418){
			Cmessager.alert(GV.MESSAGER_TITLE, '用户操作超时,请重新登录！', 'error',function(){
				GV.topWindow.location.href = GV.topWindow.basePath+"index.jsp";
			});
		} else if(request.status == 308){
			Cmessager.alert(GV.MESSAGER_TITLE, '数据处理中或已保存,请勿重复提交！', 'error');
		} else {
			var result = parseReturn(request.responseText);
			if(!result) {
				Cmessager.alert(GV.MESSAGER_TITLE, request.status+ ' ' + (thrownError ? thrownError : '操作失败'), 'error');
			} else {
				Cmessager.alert(GV.MESSAGER_TITLE, request.status+ ' ' + (result.message ? result.message : '操作失败'), 'error');
			}
			
		}
	});
	
	/*if($.browser.msie) {
		//ie 都不缓存
		$.ajaxSetup({
			cache : false
		});
	}*/
	
	//iframe页面f5刷新
	$(document).on('keydown', function(event){
		var e = window.event || event;
		if(e.keyCode == 116) {
			e.keyCode = 0;
			var tab = GV.top$('#tt').tabs('getSelected');  // 获取选择的面板
			tab.panel('refresh');
			//!ie
			return false;
		}
	});
})();

$(function(){
	/*
	*解决ie6下不支持背景缓存
	*/
	if (!+'\v1' && !('maxHeight' in document.body.style)) {
		try{
			document.execCommand("BackgroundImageCache", false, true);
		}catch(e){}
	}
	
	/*自动提示，如输入123，则会按提供的rules提示，“权0000123”
	*<input type="text" class="easyui-prefix" value="" name="" data-options="rules:[{ID:'1',REGIONCODE:'510100',PREFIXTYPE:'查封业务件号',PREFIXLEN:'7',WIDTHFILL:'0',HEADFILL:'封,权,监证',FUNCTYPE:'字头'},{ID:'2',REGIONCODE:'510100',PREFIXTYPE:'查封业务件号',PREFIXLEN:'7',WIDTHFILL:'x',HEADFILL:'查',FUNCTYPE:'字头'}]" />
	*/
	if($('.easyui-autoprefix').length > 0) {
		$('.easyui-autoprefix').combobox({
			valueField:'text',
			panelHeight:'auto',
			filter:function(){return true},
			onChange:function(newValue, oldValue){
				_comElem = $(this);
				var opts = _comElem.combobox('options');
				var text = _comElem.combobox('getText');
				var _data = [];
				if(!isNaN(text) && opts.rules) {
					var index = 0;
					for(var i in opts.rules) {
						var rule = opts.rules[i];
						var HEADFILL_arr = rule['HEADFILL'].split(',');
						for(var j in HEADFILL_arr) {
							if(text.length < rule['PREFIXLEN']) {
								_data[index] = {
									"id":index,
									"text":""   
								}
								_data[index]['text'] = HEADFILL_arr[j];
								var k=0;
								for(;k<rule['PREFIXLEN']-text.length;k++) {
									_data[index]['text'] += rule['WIDTHFILL'];
								}
								_data[index]['text'] += text;
								index++;
							}
						}
					}
				}
				_comElem.combobox('loadData', _data);
			}
		});
	}
})

/**
 * easyui tree 第一样标题右边的刷新图标，单机事件处理函数
 * @param obj
 */
function reloadTree(obj) {
	preventBubble();
	var _ul = $(obj).parent().parent().parent();
	if(_ul && _ul.hasClass('tree')) {
		_ul.tree('reload');
	}
}

/** 通用函数库****************************/
/**
* 阻止事件冒泡
*/
function preventBubble(){
	if($.browser.mozilla){
		var $E = function(){var c=$E.caller; while(c.caller)c=c.caller; return c.arguments[0]};
		__defineGetter__("event", $E);
	}
	if (window.event) {
		event.cancelBubble = true; 
	}else if (evt){
		event.stopPropagation();
	} 
}

/**
* 将html节点元素的data-options转换为对象
*/
function parseOptions(obj) {
	var _8={};
	if(typeof obj == 'object') {
		var t = $(obj);
		var s=$.trim(t.attr("data-options"));
		if(s){
			if(s.substring(0,1)!="{"){
				s="{"+s+"}";
			}
			_8=(new Function("return "+s))();
		}
	} else if(obj.length>0) {
		if(obj.substring(0,1)!="{"){
			s="{"+obj+"}";
		}
		_8=(new Function("return "+s))();
	}
	return _8;
}

/*弹出层封装*/
var Cmessager = {
	show : function(options) {
		GV.top$.messager.show(options);
	},
	alert : function(title, msg, icon, fn) {
		if(!title) title = GV.MESSAGER_TITLE;
		if(!icon) icon = 'error';
		GV.top$.messager.alert(title, msg, icon, fn);
	},
	confirm : function(title, msg, fn) {
		if(!title) title = GV.MESSAGER_TITLE;
		GV.top$.messager.confirm(title, msg, fn);
	},
	prompt : function(title, msg, fn) {
		GV.top$.messager.prompt(title, msg, fn);
	},
	progress : function(optionsOrMethod) {
		GV.top$.messager.progress(optionsOrMethod);
	}
}
function EmessagerShow(title, msg, timeout) {
	title = title?title:GV.MESSAGER_TITLE;
	timeout = timeout?timeout:1500;
	GV.top$.messager.show({
		title:title,
		msg:msg,
		timeout:timeout
	});
}
function EmessagerAlert(title, msg, type) {
	title = title?title:GV.MESSAGER_TITLE;
	type = type?type:'error';
	GV.top$.messager.alert(title, msg, type);
}

/**
* 弹出层
*/
function openDialog(elem) {
	var _options = parseOptions(elem);
	
	var _Clist = {};
	if(_options.ClistId) {
		_Clist = Clist[_options.ClistId];
	} else if ($(this).parent().hasClass('datagrid-cell')) {
		_Clist = doBeforeDatagrid(0, this);
	}

	//定义了datagrid的id
	if(_options.datagridId) {
		_Clist.id = _options.datagridId;
	}
	
	//如果没有定义buttons则默认显示保存按钮
	if(_options.buttons == undefined) {
		_options.buttons = 'formSubmit';
	}

	//默认弹出层标题
	if(_options.title == undefined || !_options.title) {
		_options.title = '信息';
	}
	
	//判断是否设置弹出层高度和高度
	if(!_options.width) {
		_options.width = (_Clist.dialogWidth?_Clist.dialogWidth:'600');
	}
	if(!_options.height) {
		_options.height = (_Clist.dialogHeight?_Clist.dialogHeight:'400');
	}
	
	_options._Clist = _Clist;
	_createWin(_options);
	
	return false; //阻止事件冒泡，同事阻止默认行为
}

/**
 * 创建弹出层窗口
 * @param _options
 */
function _createWin(_options) {
	if(!_options.url) {
		Cmessager.alert('', '弹出层缺少url参数！');
	}
	if(_options.buttons == 'formSubmit') {
		_options.buttons = [{
			text:'保存',
			handler:function(){
				if(disabledBtn(this) == false) {
					return false;
				}
				
				var _winId = GV.top$(this).parent().parent().attr('id');
				if(_options.isIframe) {
					var _window = GV.topWindow.frames[_winId+'_Iframe'].window;
					var _form = _window.$('form');
				} else {
					var _form = GV.top$(this).parent().siblings('.panel').find('form');
				}
				if(_form.length > 0) {
					if(_options.isIframe) {
						//提交前需要处理的函数
						if(_window.MybeforeSubmit && typeof(_window.MybeforeSubmit) == 'function') {
							if(_window.MybeforeSubmit() === false) {
								enableBtn();
								return false;
							}
						}
						//验证表单，如果验证通过则提交
						if(_form.form('validate')) {
							_form.submit();
						}
						return false;
					} else {
						//提交前需要处理的函数
						if(GV.topWindow.MybeforeSubmit && typeof(GV.topWindow.MybeforeSubmit) == 'function') {
							if(GV.topWindow.MybeforeSubmit() === false) {
								enableBtn();
								return false;
							}
						}						
						
						_form.form('submit',{
							dataType : 'json',
							onSubmit: function(){
								var _validate_status = GV.top$(this).form('validate');
								if(!_validate_status) enableBtn();
								return _validate_status;
							},
							success: function(result){
								enableBtn();
								
								//提交成功后需要处理的函数
								if(GV.topWindow.MyafterSubmit && typeof(GV.topWindow.MyafterSubmit) == 'function') {
									if(GV.topWindow.MyafterSubmit(result) === false) {
										return false;
									}
								}
								doFormSubmit(result, _winId, (_options._Clist?_options._Clist:null));
							}
						});
					}
				}
			}
		}];
	}
	if(_options.buttons == undefined || _options.buttons == false) {
		_options.buttons = [];
	}

	GV.top$.createWin({
		winId:(_options.winId?_options.winId:''),
		title:_options.title,
		url:_options.url,
		height:_options.height?_options.height:'auto',
		width:_options.width?_options.width:'auto',
		maximizable:(_options.maximizable?_options.maximizable:true), //是否最大化图标
		mask:(_options.mask?_options.mask:true), //遮罩层
		resizable:(_options.resizable?_options.resizable:true),
		isIframe:(_options.isIframe?_options.isIframe:false),
		//isMax:true, //最大化
		buttons:(_options.buttons?_options.buttons:[]),
		onClose:(_options.onClose?_options.onClose:function(targetjq){
			if(!_options.isIframe && ( GV.topWindow.MybeforeSubmit || GV.topWindow.MyafterSubmit)) {
				GV.topWindow.MybeforeSubmit  = undefined;
				GV.topWindow.MyafterSubmit  = undefined;
			}
		}),
		onComplete:(_options.onComplete?_options.onComplete():function(targetjq){})
	});
}

function enableBtn() {
	if(GV.btnObj != undefined) {
		GV.btnObj.data('disabled', false);
		GV.btnObj.find('span.l-btn-text').html(GV.btnObj.find('span.l-btn-text').html().slice(0, -4));
		GV.btnObj.css('cursor', 'Pointer');
		GV.btnObj = undefined;
	}
}
function disabledBtn(obj) {
	/*var curobj = GV.top$('body');
	curobj.append('<div class=\"datagrid-mask\" style=\"display:block;z-index:100000;\"></div><div class=\"datagrid-mask-msg\" style=\"display:block;left:50%;z-index:100000;top:40%;\">正在处理，请稍待...</div>');
	var msg = GV.top$('.datagrid-mask-msg');
	msg.outerHeight(40);
	msg.css({
	    marginLeft: ( - msg.outerWidth() / 2),
	    lineHeight: (msg.height() + "px")
	});*/
	GV.btnObj = $(obj);
	if(GV.btnObj.hasClass('l-btn')) {
		if(GV.btnObj.data('disabled')) return false;
		GV.btnObj.data('disabled', true);
		
		GV.btnObj.find('span.l-btn-text').html(GV.btnObj.find('span.l-btn-text').html()+'中...');
		GV.btnObj.css('cursor', 'wait');
	}
	
	return true;
}

$.fn.extend({
	  //getCheckedTreeReturnTree
	  gctrt:function(options){
		  var tree = $(this);
		  var nodes = tree.tree("getRoots");
		  var newTree = traverse(nodes);;
		  return newTree;
		  
		  function traverse(nodes){
			  var newNode = [];
			  $.each(nodes,function(){
				  var target = $(this.target);
				  //如果非勾选的节点及其你节点，不操作
				  if(target.find(".tree-checkbox2").length==0 && target.find(".tree-checkbox1").length==0){
					  return;
				  }
				  var cuNode = {};
				  cuNode.id = this.id;
				  cuNode.text = this.text;
				  cuNode.attributes = this.attributes;
				  cuNode.children = this.children;
				  cuNode.state = this.state;
				  cuNode.checked = this.checked;
				  if(cuNode.children){
					  var arr = [];
					  $.each(this.children,function(){
						  arr.push(tree.tree("getNode",$("#"+this.domId)[0]));	  
					  });
					  cuNode.children = traverse(arr);
					  newNode.push(cuNode);
				  }else{
					  newNode.push(cuNode);
				  }
				  	
			  });
			  return newNode;
		  }
	  }
});

/** datagrid 相关函数 *************/
function getDatagridId(isToolbar, elem) {
	if(isToolbar) {
		var _datagridId = $(elem).parents('div.datagrid-toolbar').siblings('div.datagrid-view').find('table.easyui-datagrid').attr('id');
	} else {
		var _datagridId = $(elem).parents('div.datagrid-view2').siblings('table.easyui-datagrid').attr('id');
	}
	
	return _datagridId;
}

/** tree 相关函数 *************/
/*
 * 获取easyui tree checkbox选中的值
 *treeId:树ID；pkey:父节点key，默认为pid；ckey：子节点key,默认为id
 *举例：TreeGetChecked('tt', 'pid', 'cid')
 * 返回：[{"pid":"0","cid":"1"},{"pid":"1","cid":"12"},{"pid":"12","cid":"124"}]
 */
function TreeGetChecked(treeId, keyid, pkey, ckey , vkey){
	if(keyid==undefined || !keyid) keyid = 'attributes';
	//keys = keys.split(',');
	if(pkey==undefined || !pkey) pkey = 'pid';
	if(ckey==undefined || !ckey) ckey = 'id';
	if(vkey==undefined || !vkey) vkey = 'text';
	var nodes = $("#"+treeId).tree("getRoots");
	var data = "";
	for(var i in nodes){
		var temp = _getData($("#"+treeId),nodes[i]);
		data += temp!=""?temp+",":"";
		
	}
	
	var value = null;
//	if(data!=null){
//		data = "["+data+"]";
//	}

	return data==null?'':data.substring(0, data.length-1);		//############得到的值
	//console.log($.parseJSON(data));
	
	function _getData(obj,node){
		//console.log(node.target);
		var txt = "";
		if($(node.target).find(".tree-checkbox").hasClass("tree-checkbox0")){
			return "";
		}
		//console.log(node.text+"#");
		var pid = 0;
		var cid = node[keyid];
		var text = node[vkey];
		var pNode = $("#"+treeId).tree("getParent", node.target); 
//		if(pNode){			//判断是否有父节点，没有作0处理
//			var pid = pNode[keyid];
//			txt = '{"'+pkey+'":"'+(pid.ID?pid.ID:'')+'","'+ckey+'":"'+(cid.ID?cid.ID:'')+'","'+vkey+'":"'+text+'"}';
//		} else {
//			txt = '{"'+pkey+'":"0","'+ckey+'":"'+(cid.ID?cid.ID:'')+'","'+vkey+'":"'+text+'"}';
//		}
		if(pNode){			//判断是否有父节点，没有作0处理
			var pid = pNode[keyid];
			txt = (pid.ID?pid.ID:'')+':'+(cid.ID?cid.ID:'')+':'+text ;
		} else {
			txt = '0:'+(cid.ID?cid.ID:'')+':'+text;
		}
		
		
		if(node.children){
			var cNodes = node.children;
			for(var i=0;i<cNodes.length;i++){
				var curNode = $("#"+treeId).tree("getNode",$("#"+treeId).find("#"+cNodes[i].domId));
				//console.log(curNode);
				var temp = _getData(obj, curNode);
				if(temp!=""){
					if(txt!="")txt+=",";
					txt += temp;
				}
			}
		}
		
		return txt;
	}
}
function TreeGetChecked2(treeId, keyid, pkey, ckey , vkey){
	if(keyid==undefined || !keyid) keyid = 'attributes';
	//keys = keys.split(',');
	if(pkey==undefined || !pkey) pkey = 'pid';
	if(ckey==undefined || !ckey) ckey = 'id';
	if(ckey==undefined || !vkey) vkey = 'text';

	var data = _getData($("#"+treeId), $("#"+treeId).tree("getRoots"), null);
	return data==null?'':data;		//############得到的值

	function _getData(obj, nodes, parentNode){
		var txt = '';
		if(!nodes.length) {
			return txt;
		}
		var temp = '';
		var pid=0, cid, text;
		for(var i in nodes) {
			temp = '';
			var tree_checkbox = $('#'+nodes[i].domId).children('.tree-checkbox');
			if(tree_checkbox.hasClass('tree-checkbox0') || tree_checkbox.hasClass('tree-checkbox2')) {
				if(nodes[i].children && nodes[i].children.length) {
					temp = _getData(obj, nodes[i].children, nodes[i]);
				}
			} else {
				cid = nodes[i][keyid];
				text = nodes[i][vkey];
				temp = cid.ID?cid.ID:'';
				if(parentNode){			//判断是否有父节点，没有作0处理
					pid = parentNode[keyid];
					//temp = (pid.ID?pid.ID:'')+':'+(cid.ID?cid.ID:'')+':'+text ;
				} else {
					//temp = '0:'+(cid.ID?cid.ID:'')+':'+text;
				}
			}
			if(temp) txt += (txt?',':'')+temp;
		}
		
		return txt;
	}
}
/** 通用函数库 end****************************/


/***数据列表页面 处理函数******************************************************/
//格式化操作列
function rowFormater(val,row,index) {
	return '&nbsp;<a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a> | <a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a>&nbsp;';
}

//获取datagrid配置信息
function doBeforeDatagrid(isToolbar, elem) {
	var _datagridId = getDatagridId(isToolbar, elem);
	
	if(!isToolbar) {
		preventBubble(); //阻止事件冒泡
	}

	if(Clist.id!=undefined && Clist.id) {
		var _Clist = Clist;
	} else {
		var _Clist = Clist[_datagridId];
		_Clist.id = _datagridId;
	}

	return _Clist;
}
//添加
function doAdd(elem){
	var _Clist = doBeforeDatagrid(1, elem);
	var _options = {};
	_options = parseOptions(elem);
	if(_options.buttons == undefined) {
		_options.buttons = 'formSubmit';
	}
	
	
	if(_options.title == undefined || !_options.title) {
		_options.title = '新增';
	}
	
	_options.url = _Clist.addUrl;
	if(!_options.width) {
		_options.width = (_Clist.dialogWidth?_Clist.dialogWidth:'auto');
	}
	if(!_options.height) {
		_options.height = (_Clist.dialogHeight?_Clist.dialogHeight:'auto');
	}
	_options._Clist = _Clist;
	_createWin(_options);
}

//编辑
function doEdit(elem, index) {
	var _Clist = doBeforeDatagrid(0, elem);
	var rows = $('#'+_Clist.id).datagrid('getData');
	var row = rows.rows[index];
	
	var _options = {};
	_options = parseOptions(elem);
	
	//弹出层编辑的url
	var _dialogUrl = (_options.url?_options.url:_Clist.editUrl);
	var _url = _dialogUrl+(_dialogUrl.indexOf('?')!=-1?'&':'?');
	var _extUrl = [];
	var _args = _Clist.idField.split('&');
	for(var i in _args) {
		_extUrl.push(_args[i]+'='+row[_args[i]]);
	}
	_url += _extUrl.join('&');

	//按钮
	if(_options.buttons == undefined) {
		_options.buttons = 'formSubmit';
	}
	
	//标题
	if(_options.title == undefined || !_options.title) {
		_options.title = '编辑';
	}
	
	_options.url = _url;
	if(!_options.width) {
		_options.width = (_Clist.dialogWidth?_Clist.dialogWidth:'auto');
	}
	if(!_options.height) {
		_options.height = (_Clist.dialogHeight?_Clist.dialogHeight:'auto');
	}
	_options._Clist = _Clist;
	
	_createWin(_options);
	return false;
}

//删除操作
function doDelete(elem, index, postType) {
	var _Clist = doBeforeDatagrid(0, elem);
	
	var row = $('#'+_Clist.id).datagrid('getRows')[index];
	postType = (postType?postType:'POST');
	Cmessager.confirm(GV.MESSAGER_TITLE, '确认要删除吗?', function(r){
		if (r){
			var _data = {};
			
			var _args = _Clist.idField.split('&');
			for(var i in _args) {
                _data[_args[i]] = row[_args[i]];//bug fixed：yangfan
			}
			
			$.ajax({
					url: _Clist.deleteUrl,
					type: postType,
					data: _data
				}).done(function(result) {
					doFormSubmit(result, null, _Clist);
				});
		}
	});
}

//删除选中
function doDeleteChecked(elem) {
	var _Clist = doBeforeDatagrid(1, elem);

	var _data = {};
	var ischecked = false;
	var _args = _Clist.idField.split('&');
	var rows = $('#'+_Clist.id).datagrid('getChecked');
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		for(var j in _args) {
			ischecked = true;
			if(_data[_args[j]] == undefined) {
				_data[_args[j]] = [];
			}
			_data[_args[j]].push(row[_args[j]]);
		}
	}

	if(!ischecked) {
		Cmessager.alert(GV.MESSAGER_TITLE, '请选择要删除的项目！', 'warning');
		return false;
	}
	Cmessager.confirm(GV.MESSAGER_TITLE, '确认要删除吗?', function(r){
		if (r){
			$.ajax({
				url: _Clist.deleteUrl,
				type: "POST",
				data: _data
			}).done(function(result) {
				doFormSubmit(result, null, _Clist);
			});
		}
	});
}

//搜索
function doSearch(formId, elem) {
	var _Clist = doBeforeDatagrid(1, elem);
	
	var _url = _Clist.listUrl;
	_url += _Clist.listUrl.indexOf('?')!=-1?'&':'?';
	_url += $('#'+formId).serialize();
	
	//重置datagrid 的 url
	var _dg = $('#'+_Clist.id).data('datagrid');
	_dg.options.url = _Clist.listUrl;
	
	var fields = $('#'+formId).serializeArray();
	var params = {};
	if(fields.length > 0) {
		for (var i in fields) {
			params[fields[i].name] = fields[i].value;
		}
	}
	$('#'+_Clist.id).datagrid('load', params);
}

//datagrid 
function doDatagridOnDblClickRow(rowIndex, rowData) {
	preventBubble(); //阻止事件冒泡
	_datagridId = $(this).attr('id');
	if(Clist.id!=undefined && Clist.id) {
		var _Clist = Clist;
	} else {
		var _Clist = Clist[_datagridId];
		_Clist.id = _datagridId;
	}

	var rows = $('#'+_Clist.id).datagrid('getData');
	var row = rows.rows[rowIndex];
	
	var _options = {};
	
	//弹出层编辑的url
	var _dialogUrl = _Clist.viewUrl;
	var _url = _dialogUrl+(_dialogUrl.indexOf('?')!=-1?'&':'?');
	var _extUrl = [];
	var _args = _Clist.idField.split('&');
	for(var i in _args) {
		_extUrl.push(_args[i]+'='+row[_args[i]]);
	}
	_url += _extUrl.join('&');

	//按钮
	_options.buttons = false;
	
	//标题
	_options.title = '查看';
	
	_options.url = _url;
	if(!_options.width) {
		_options.width = (_Clist.dialogWidth?_Clist.dialogWidth:'auto');
	}
	if(!_options.height) {
		_options.height = (_Clist.dialogHeight?_Clist.dialogHeight:'auto');
	}
	_options._Clist = _Clist;
	_createWin(_options);
	
}
//重置
function doReset() {
	$(".searchForm").find("input").val("");
	//resetSe = $(".searchForm").find("select > option:first-child").text();
	$(".searchForm select").each(function(){
		resetSe = $(this).children("option:first-child").text();
		$(this).next("span").children("input").first().val(resetSe);
	});
}

function parseReturn(result) {
	if(typeof result == 'string') {
		try {
			result = $.parseJSON(result);
		} catch(err) {
			//Cmessager.alert('', result);
			return false;
		}
	}
	
	return result;
}
//处理弹出层form表单提交，服务端返回数据后
function doFormSubmit(result, _winId, _Clist) {
	result = parseReturn(result);
	if(!result) return false;

	if(result.callback) {
		eval(result.callback+'()');
	}
	if (result.status == 'success'){
		Cmessager.show({
			title:GV.MESSAGER_TITLE,
			msg:result.message,
			timeout:1500
		});
		if(_winId!=undefined && _winId) GV.top$('#'+_winId).dialog('close'); //关闭弹出层
		if(result.closeTab == true) {
			var tab = GV.top$('#tt').tabs('getSelected');  // 获取选择的面板
			GV.top$('#tt').tabs('close', GV.top$('#tt').tabs('getTabIndex', tab));
		} else if(result.reloadTab == true) { //刷新tab
			var tab = GV.top$('#tt').tabs('getSelected');  // 获取选择的面板
			tab.panel('refresh');
		} else {
			if(_Clist!=undefined && _Clist) $('#'+_Clist.id).datagrid('reload');
		}
	} else {
		Cmessager.alert(GV.MESSAGER_TITLE, result.message, 'error');
	}
}
/***数据列表页面 处理函数 end******************************************************/


/***扩展EasyUI 和 Jquery Begin******************************************************/
/**
 *
 *
 * @requires jQuery
 *
 * 将form表单元素的值序列化成对象
 *
 * @returns object
 */
$.serializeObject = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

/**
 *
 * @requires jQuery,EasyUI
 *
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for ( var i = 0; i < frame.length; i++) {
                frame[i].src = '';
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
                try {
                    CollectGarbage();
                } catch (e) {
                }
            }
        }
    } catch (e) {
    }
};

/**
 * @author 夏悸
 *
 * @requires jQuery,EasyUI
 *
 * 扩展tree，使其可以获取实心节点
 */
$.extend($.fn.tree.methods, {
    getCheckedExt : function(jq) {// 获取checked节点(包括实心)
        var checked = $(jq).tree("getChecked");
        var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
        $.each(checkbox2, function() {
            var node = $.extend({}, $.data(this, "tree-node"), {
                target : this
            });
            checked.push(node);
        });
        return checked;
    },
    getSolidExt : function(jq) {// 获取实心节点
        var checked = [];
        var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
        $.each(checkbox2, function() {
            var node = $.extend({}, $.data(this, "tree-node"), {
                target : this
            });
            checked.push(node);
        });
        return checked;
    }
});

/**
 * @author 夏悸
 *
 * @requires jQuery,EasyUI
 *
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
    var opt = $(this).data().tree.options;
    var idFiled, textFiled, parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
            data[i]['children'] = [];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

/**
 * @author 孙宇
 *
 * @requires jQuery,EasyUI
 *
 * 扩展treegrid，使其支持平滑数据格式
 */
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
    var opt = $(this).data().treegrid.options;
    var idFiled, textFiled, parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

/**
 * @author 孙宇
 *
 * @requires jQuery,EasyUI
 *
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/***扩展EasyUI 和 Jquery end******************************************************/


/** report begin */

/**
 * 获得打印机数量
 * @returns {*}
 */
function getPrinterCount() {
    var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
    return LODOP.GET_PRINTER_COUNT();
}
/**
 * 获得打印机名称
 * @param iPrinterNO
 * @returns {*}
 */
function getPrinterName(iPrinterNO) {
    var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
    return LODOP.GET_PRINTER_NAME(iPrinterNO);
}



/**
 * 不预览直接打印
 * @param index 打印机序号
 * @param rurl 带参数的报表地址
 */
function printByNoPreview(index,rurl){
    var localtor=new ActiveXObject("WbemScripting.SWbemLocator");
    var service=localtor.ConnectServer(".");
    var properties=service.ExecQuery("SELECT * FROM Win32_Printer");
    var e=new Enumerator(properties);
    var i = 0;
    for(;!e.atEnd();e.moveNext()){
        var p=e.item();
        if(i==index){
            p.setDefaultPrinter();
            break;
        }
        i++;
    }
    //console.log(rurl+"&printe=1");

    var randomnumber=Math.floor(Math.random()*100000)
    var url=cjkEncode(rurl+"&printe=1&randomnumber="+randomnumber);

    var iframe = document.getElementById('reportFrame');

    if (!iframe) {
        // 如果iframe还没有加进去，则加进去
        iframe = document.createElement('iframe');
        document.getElementsByTagName('body')[0].appendChild(iframe);
        iframe.setAttribute('name','reportFrame');
        iframe.setAttribute('id','reportFrame');
    }

    iframe.setAttribute('src', url);
}

/**
 * 编码url，帆软报表用
 * @param text
 * @returns {string}
 */
function cjkEncode(text) {
    if (text == null) {
        return "";
    }
    var newText = "";
    for (var i = 0; i < text.length; i++) {
        var code = text.charCodeAt (i);
        if (code >= 128 || code == 91 || code == 93) {//91 is "[", 93 is "]".
            newText += "[" + code.toString(16) + "]";
        } else {
            newText += text.charAt(i);
        }
    }
    return newText;
}
/* report end */

/** jquery-ztree封装树**/
$.fn.extend({
    initZtree: function (conf) {
        var ztreeObj = false;
        var setting = {
            async: {
                enable: true,
                url: conf.url,
                type: 'post',
                dataType: 'json'
            },
            view: {
                showIcon: true,
                showLine: true,
                expandSpeed: 'normal',
                txtSelectedEnable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: conf.id || 'id',
                    pIdKey: conf.parentId || 'pid',
                    rootPId: null
                },
                key: {
                    url: 'xurl', //屏蔽URL
                    name: conf.name || 'text'
                }
            },
            check: {
                enable: conf.check || true,
                chkStyle: "checkbox",
                chkboxType: conf.chkboxType || { "Y": "s", "N": "p" }
            },
            callback: {
                onClick: conf.onClick,
                onAsyncSuccess: conf.onAsyncSuccess,
                onRightClick: conf.onRightClick
            }
        };
        ztreeObj = $.fn.zTree.init($(this), setting);
        return ztreeObj;
    }
});