var basePath=$("#basePath").val();

// 信息回显
function updateRecord(id,phone,relName,address,awardCode){
	$("#username").val(relName);
	$("#tel").val(phone);
	$("#address").val(address);
	$("#recordId").val(id);
	$("#awardCode").html(awardCode);
	$(".myprizes").css("display","block");
	$("#myprizes").css("display","block");
}

// 提交领奖信息
function updateMyrecord(){
	var tel = $("#tel").val();
	var name  = $("#username").val();
	var address = $("#address").val();
	var username = $("#username").val();
	var recordId = $("#recordId").val();
	if (username == '') {
		alert("请输入姓名");
		return
	}
	if (tel == '') {
		alert("请输入手机号");
		return
	}else if (!isMobile(tel)){
		alert("手机号格式不正确，请重新输入！");
		return
	}
	jQuery.ajax({
		url : basePath+"/shaketicket/updateMyAwards.do?id="+recordId+"&mobile="+tel+"&address="+address+"&name="+username,
		type:"post",
		dataType : "json",
		success : function(data){
			if(data.success){
				alert("提交成功，谢谢参与！");
				location.reload();
			}else{
				
			}
		}
	});
}

// 手机号校验
function isMobile(s){
	var patrn= /^1((3\d)|(4[579])|(5[012356789])|(7[01235678])|(8\d))\d{8}$/;
	if (!patrn.exec(s)) return false;
	return true;
}

function hideMyprizes(type){
	$(".myprizes").css("display","none");
	$("#myprizes").css("display","none");
}
