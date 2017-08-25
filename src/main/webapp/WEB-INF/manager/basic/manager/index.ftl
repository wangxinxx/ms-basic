<@ms.html5>
	<@ms.nav title="管理员管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addManagerBtn"/>
					<@ms.delButton id="delManagerBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="managerList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delManager" title="授权数据删除" >
		<@ms.modalBody>删除此授权
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteManagerBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#managerList").bootstrapTable({
			url:"${managerPath}/basic/manager/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    	{
				        	field: 'managerId',
				        	title: '管理员ID(主键)',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerId="+row.managerId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerName',
				        	title: '管理员用户名',
				        	width:'15',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerName="+row.managerName;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerNickname',
				        	title: '管理员昵称',
				        	width:'15',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerNickname="+row.managerNickname;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerPassword',
				        	title: '管理员密码',
				        	width:'45',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerPassword="+row.managerPassword;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerRoleid',
				        	title: '角色编号',
				        	width:'19',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerRoleid="+row.managerRoleid;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerPeopleid',
				        	title: '用户编号即商家编号',
				        	width:'19',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerPeopleid="+row.managerPeopleid;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerTime',
				        	title: '管理员创建时间',
				        	width:'19',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerTime="+row.managerTime;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'managerSystemSkinId',
				        	title: '管理员主界面样式',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/basic/manager/form.do?managerSystemSkinId="+row.managerSystemSkinId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	}			]
	    })
	})
	//增加按钮
	$("#addManagerBtn").click(function(){
		location.href ="${managerPath}/basic/manager/form.do"; 
	})
	//删除按钮
	$("#delManagerBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#managerList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delManager").modal();
		}
	})
	
	$("#deleteManagerBtn").click(function(){
		var rows = $("#managerList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/basic/manager/delete.do",
			data: JSON.stringify(rows),
			dataType: "json",
			contentType: "application/json",
			success:function(msg) {
				if(msg.result == true) {
					<@ms.notify msg= "删除成功" type= "success" />
				}else {
					<@ms.notify msg= "删除失败" type= "fail" />
				}
				location.reload();
			}
		})
	});
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#managerList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#managerList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>