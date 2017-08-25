<@ms.html5>
	 <@ms.nav title="管理员编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="managerForm" isvalidation=true>
    		<@ms.hidden name="managerId" value="${managerEntity.managerId?default('')}"/>
    			<@ms.text label="管理员用户名" name="managerName" value="${managerEntity.managerName?default('')}"  width="240px;" placeholder="请输入管理员用户名" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"管理员用户名长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="管理员昵称" name="managerNickname" value="${managerEntity.managerNickname?default('')}"  width="240px;" placeholder="请输入管理员昵称" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"管理员昵称长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="管理员密码" name="managerPassword" value="${managerEntity.managerPassword?default('')}"  width="240px;" placeholder="请输入管理员密码" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"管理员密码长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="角色编号" name="managerRoleid" value="${managerEntity.managerRoleid?default('')}" width="240px;" placeholder="请输入角色编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"角色编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="用户编号即商家编号" name="managerPeopleid" value="${managerEntity.managerPeopleid?default('')}" width="240px;" placeholder="请输入用户编号即商家编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"用户编号即商家编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="管理员创建时间" name="managerTime" value="${managerEntity.managerTime?string('yyyy-MM-dd')}"  width="240px;"/>
    			<@ms.number label="管理员主界面样式" name="managerSystemSkinId" value="${managerEntity.managerSystemSkinId?default('')}" width="240px;" placeholder="请输入管理员主界面样式" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"管理员主界面样式长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/basic/manager/save.do";
	if($("input[name = 'managerId']").val() > 0){
		url = "${managerPath}/basic/manager/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#managerForm").data("bootstrapValidator").validate();
			var isValid = $("#managerForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'managerForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.result == true) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/basic/manager/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
					location.href= "${managerPath}/basic/manager/index.do";
				}
			}
		})
	}	
</script>
