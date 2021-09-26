<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
</head>
<script>
	$(function () {

		//如果当前窗口不是顶级窗口, 就把当前窗口设置为顶级窗口
		if (window.top != window){
			window.top.location = window.location;
		}

		//页面加载完毕后, 清空文本内容;
		$("#loginAct").val("");
		//加载完页面自动获得焦点;
		$("#loginAct").focus();
		
		//为登录绑定事件, 执行登录操作
		$("#submit_btn").click(function () {
			login();
		})

		//为当前登录窗口绑定键盘事件;
		$(window).keydown(function (evt) {
			if (evt.keyCode === 13){
				login();
			}
		})
	})

	//登录验证
	function login() {
		// alert("验证登录2021年9月25日");
		//验证账号密码不为空;
		let loginAct = $.trim($("#loginAct").val());
		let loginPwd = $.trim($("#loginPwd").val());
		console.log(loginAct);
		console.log(loginPwd);

		//非空验证
		if (loginAct === "" || loginPwd === ""){
			$("#msg").html("账号密码不能为空!");
			return;
		}
		//验证后台账号密码;
		$.ajax({
			url: "settings/user/login.do",
			data: {"loginAct":loginAct, "loginPwd": loginPwd},
			type: "post",
			dataType: "json",
			success: function (data) {
				/*
					data
						{"sucess": true/false, "msg":"error"}
					登录成功失败状态
				 */
				//登录成功
				if (data.success){
					//跳转到工作台
					console.log(data.success)
					window.location.href = "workbench/index.jsp";
				}else{
					$("#msg").html(data.msg);
				}
			}
		})
	}
</script>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;LUCIUS</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>LOGIN</h1>
			</div>
			<form action="user/login.do" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="Username" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="Password" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
							<span id="msg" style="color: red"></span>
					</div>
				</div>
			</form>
			<button id="submit_btn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">LOGIN</button>
		</div>
	</div>
</body>
</html>