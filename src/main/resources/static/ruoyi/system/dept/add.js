$("#form-dept-add").validate({
	rules:{
		deptName:{
			required:true,
		},
		orderNum:{
			required:true,
		},
	},
	submitHandler:function(form){
		update();
	}
});

function update() {
	var parentId = $("input[name='parentId']").val();
	var orderNum = $("input[name='orderNum']").val();
	var deptName = $("input[name='deptName']").val();
	var leader = $("input[name='leader']").val();
	var phone = $("input[name='phone']").val();
	var email = $("input[name='email']").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/dept/save",
		data : {
			"parentId": parentId,
			"deptName": deptName,
			"orderNum": orderNum,
			"leader": leader,
			"phone": phone,
			"email": email,
			"status": status
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", "error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg('新增成功',{icon:1,time:1000});
				$.modalClose();
				parent.loading();
			} else {
				$.modalAlert(data.msg, "error");
			}

		}
	});
}