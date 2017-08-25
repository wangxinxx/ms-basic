<@ms.html5>
	 <@ms.nav title="角色编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="roleForm" isvalidation=true>
    		<@ms.hidden name="roleId" value="${roleEntity.roleId?default('')}"/>
    			<@ms.text label="角色名" name="roleName" value="${roleEntity.roleName?default('')}"  width="240px;" placeholder="请输入角色名" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"角色名长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="角色管理员编号" name="roleManagerid" value="${roleEntity.roleManagerid?default('')}" width="240px;" placeholder="请输入角色管理员编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"角色管理员编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/basic/role/save.do";
	if($("input[name = 'roleId']").val() > 0){
		url = "${managerPath}/basic/role/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#roleForm").data("bootstrapValidator").validate();
			var isValid = $("#roleForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'roleForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.result == true) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/basic/role/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
					location.href= "${managerPath}/basic/role/index.do";
				}
			}
		})
	}	
</script>
