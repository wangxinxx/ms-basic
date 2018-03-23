  <base target="mainFrame" />
  <#assign static="http://cdn.mingsoft.net">
  <#setting url_escaping_charset='utf-8'> 
  <#assign bootstrap="3.3.5">
  <#assign manager_ui="4.6.0">
  <#assign easyui="1.5">
  <meta content="IE=edge" http-equiv="X-UA-Compatible" /> 
  <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
  <meta content="text/html; charset=utf-8" http-equiv="Content-Type" /> 
  <link rel="stylesheet" type="text/css" href="${static}/plugins/animate/1.0.0/animate.css" media="all" /> 
  <link rel="stylesheet" type="text/css" href="${static}/plugins/iconfont/1.0.0/iconfont.css"/>
    
  <script type="text/javascript" src="${static}/plugins/jquery/1.9.1/jquery-1.9.1.js"></script> 
  <script type="text/javascript" src="${static}/plugins/jquery.serializeJSON/2.8.1/jquery.serializejson.min.js"></script> 
  
  <link rel="stylesheet" type="text/css" href="${static}/plugins/ztree/3.5/zTreeStyle.css" media="all" /> 
  <script type="text/javascript" src="${static}/plugins/ztree/3.5/jquery.ztree.all-3.5.min.js"></script> 
  
  <link rel="stylesheet" type="text/css" href="${static}/plugins/bootstrap/${bootstrap}/css/bootstrap.min.css" media="all" /> 
  <link rel="stylesheet" type="text/css" href="${static}/plugins/bootstrap/${bootstrap}/css/bootstrap-switch.css" media="all" /> 
  <link rel="stylesheet" type="text/css" href="${static}/plugins/bootstrap/${bootstrap}/css/bootstrapValidator.css" media="all" /> 
  <link rel="stylesheet" type="text/css" href="${static}/plugins/bootstrap/${bootstrap}/css/bootstrap-notify.css" media="all" />
  
  <script type="text/javascript" src="${static}/plugins/bootstrap/${bootstrap}/js/bootstrap.min.js"></script> 
  <script type="text/javascript" src="${static}/plugins/bootstrap/${bootstrap}/js/bootstrap-switch.min.js"></script>   
  <script type="text/javascript" src="${static}/plugins/bootstrap/${bootstrap}/js/bootstrapValidator.js"></script> 
  <script type="text/javascript" src="${static}/plugins/bootstrap/${bootstrap}/js/bootstrap-notify.js"></script>
  
  <link rel="stylesheet" type="text/css" href="${static}/plugins/select2/4.0.3/css/select2.min.css" media="all" />
  <script type="text/javascript" src="${static}/plugins/select2/4.0.3/js/select2.min.js"></script>
  
	<!--时间插件-->
  <script type="text/javascript" src="${static}/plugins/jquery.validation/1.15.0/jquery.validate.min.js"></script>
  <script type="text/javascript" src="${static}/plugins/jquery.tmpl/1.4.2/jquery.tmpl.min.js"></script>
  <!--时间插件-->
  <link href="${static}/plugins/bootstrap.daterangepicker/1.3.4/daterangepicker.css" rel="stylesheet">
  <script type="text/javascript" src="${static}/plugins/bootstrap.daterangepicker/1.3.4/moment.js"></script>
  <script type="text/javascript" src="${static}/plugins/bootstrap.daterangepicker/1.3.4/daterangepicker.js"></script>
  <script type="text/javascript" src="${static}/plugins/jquery.cookie/2.2.0/jquery.cookie.js"></script>
    
  <!----上传图片--->
  <script type="text/javascript" src="${static}/plugins/jquery.swfupload/1.0.0/swfupload.js"></script>
  <script type="text/javascript" src="${static}/plugins/jquery.swfupload/1.0.0/jquery.swfupload.js"></script>
  <script type="text/javascript" src="${static}/plugins/jquery.swfupload/1.0.0/fileprogress.js"></script>
  <!--script type="text/javascript" src="${static}/plugins/plupload/2.2.1/plupload.full.min.js"></script-->
  
  <!--后台UI--> 
  <script type="text/javascript" src="${static}/skin/manager/${manager_ui}/js/ms.manager.js"></script>
  <script type="text/javascript" src="${static}/skin/manager/${manager_ui}/js/ms.web.js"></script>
  
  <!--easyUI-->
  <link rel="stylesheet" type="text/css" href="${static}/plugins/jquery.easyui/${easyui}/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="${static}/plugins/jquery.easyui/${easyui}/themes/icon.css">
  <script type="text/javascript" src="${static}/plugins/jquery.easyui/${easyui}/jquery.easyui.min.js"></script>

  <!--bootstrap=table-->
  <link rel="stylesheet" href="${static}/plugins/bootstrap-table/1.11.1/bootstrap-table.min.css">
  <link rel="stylesheet" href="${static}/plugins/bootstrap-table/1.11.1/extensions/tree-column/bootstrap-table-tree-column.css">
  <script src="${static}/plugins/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
  <script src="${static}/plugins/bootstrap-table/1.11.1/extensions/tree-column/bootstrap-table-tree-column.min.js"></script>
   
 
  <script src="${static}/plugins//bootstrap-table/1.11.0/locale/bootstrap-table-zh-CN.min.js"></script>
  
  <!-- vue框架所需要的资源 -->
  <script src="${static}/plugins/hammerjs/2.0.8/hammer.min.js"></script>
  <script src="${static}/plugins/vue/2.3.3/vue.min.js"></script>
	
  <link rel="stylesheet" type="text/css" href="${static}/skin/manager/${manager_ui}/css/ms.manager.min.css" media="all" />
  <#assign skin_manager_logo="${static}/skin/manager/${manager_ui}/images/logo.png"/>  
  <#assign skin_manager_loadding="${static}/skin/manager/${manager_ui}/images/loading.gif"/>  
  

       
  <#include "${managerViewPath}/include/macro.ftl"/>
  <script>
    var basePath = "${basePath}";
    var base = "${base}";
    var managerPath = "${managerPath}";
    var static = "${static}";
    $(function() {
    			//启用工具提示
		   //	$("[data-toggle='tooltip']").tooltip();
		   //	$("[data-toggle='popover']").popover({html:true});
    })
	<#if manager_session?exists>
		var websiteId= "${manager_session.basicId?default('0')}" ;
	</#if>
  </script>	 
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		  <script src="${base}/https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="${base}/https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	
	<script type="text/javascript" charset="utf-8" src="${base}/static/plugins/ueditor/1.4.3.1/ueditor.parse.js"></script>
	<script type="text/javascript" charset="utf-8" src="${base}/static/plugins/ueditor/1.4.3.1/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${base}/static/plugins/ueditor/1.4.3.1/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${base}/static/plugins/ueditor/1.4.3.1/lang/zh-cn/zh-cn.js"></script>

	<script>		
		$(function(){
			$.extend($.fn.validatebox.defaults.rules, {
				number: {
				   validator: function (value, param) {
				   		return /^[0-9]*$/.test(value);
				   }, 
				   message: '输入必须为整数'
				  },
				float: {
				   validator: function (value, param) {
				   		return /^([0-9]+[.0-9]{1,3})?$/.test(value);
				   }, 
				   message: '输入必须为数字'
				  },
				isName: {
				   validator: function (value, param) {
				   		return /^[\u0391-\uFFE5]+$/i.test(value) || /^[a-zA-Z]*$/i.test(value);
				   }, 
				   message: '只能输入中文或英文'
				  },
				unnormal: {
				   validator: function (value, param) { 
				   		return /^([\u4E00-\u9FA5]|\w)*$/.test(value); 
				   }, 
				   message: '含有非法字符'
				  },
				code: {
				   validator: function (value, param) { 
				   		return /^[a-zA-Z0-9]*$/.test(value); 
				   }, 
				   message: '编号不能有文字'
				},
				CHS: {  
        			validator: function (value) {  
            			return /^[\u0391-\uFFE5]+$/.test(value);  
        			},  
        			message: '只能输入汉字'  
    			},
    			smallerDate: {
	                validator: function(value, param){
	                    var d1 = $.fn.datebox.defaults.parser(param[0]);
	                    var d2 = $.fn.datebox.defaults.parser(value);
	                    param.push(d1);
	                    return d2 <= d1;
	                },
	                message: '当前时间必须小于{1}'
            	},
            	biggerDate: {
            		validator: function(value, param){
	                    var d1 = $.fn.datebox.defaults.parser(param[0]);
	                    var d2 = $.fn.datebox.defaults.parser(value);//当前时间框时间
	                    param.push(d1);
	                    return d2 >= d1;
	                },
	                message: '当前时间必须大于{1}'
            	},
            	greater : {
					validator: function (value, param) { 
					   	return Date.parse(value) > Date.parse($(param[0]).datebox('getValue'));
					}, 
					message: '结束时间必须大于开始时间'
				}
			});
		})
	</script>