function checkLogin(){
	user.checkLogin(callbackLogincData);
}

function login(){
	var login_name = $("#login-name").val();
	var login_pass = $("#login-pass").val();
	
	if(login_name == "" || login_pass == ""){
		$("#login_button").text("用户名或密码未填");
	} else {
		user.login(login_name,login_pass,callbackLogincData);
	}
}

function callbackLogincData(LoginData){
	if(LoginData == null || LoginData.userName == "" || LoginData.userID == ""){
		$("#already-login").css({"display":"none"});
	} else {
		if(LoginData.wrongTime > 0){
			$("#login_button").text("用户名或密码错误");
		} else {
			if(LoginData.wrongTime < 0){
				$("#login_button").text("系统错误");
			} else {
				$("#already-login").css({"display":"block"});
				if(LoginData.firstLogin == 1){
					$("[name='loginBlock']").css({"display":"none"});
					$("[name='registBlock']").css({"display":"none"});
					$("div[name!='loginBlock']").css({"pointer-events":"auto","filter":"alpha(opacity=100)","opacity":"1.0"});
				}
				$("#not-logined").css({"display":"none"});
				$("#already-login-name").text(LoginData.userName);
				$("#show-name").text("用户： " + LoginData.userName + "，您好！");
				$("#show-ID").text("UID:" + LoginData.userID);
				$("#show-logintime").text("上次登陆时间：" + LoginData.lastloginDate);
				$("#show-IP").text("本地登陆IP：" + LoginData.IP);
				$("#show-lastIP").text("上次登陆IP：" + LoginData.lastIP);
				if(LoginData.userType == 0){
					$("#d_create_match").text("创建比赛");
					$("#d_change_match_model").text("设置比赛类型");
					$("#d_admin").text("主持管理");
					$("#d_banlist").text("禁卡表管理");
					$("#d_change_gonggao").text("公告管理");
					$("#d_create_match,#d_change_match_model,#d_admin,#d_banlist,#d_change_gonggao,#d_match_create").css({"display":"block"});
				} else {
					if(LoginData.userType == 2){
						$("#d_create_match").text("创建比赛");
						$("#d_change_match_model").text("设置比赛类型");
						$("#d_banlist").text("禁卡表管理");
						$("#d_change_gonggao").text("公告管理");
						$("#d_create_match,#d_change_match_model,#d_banlist,#d_change_gonggao,#d_match_create").css({"display":"block"});
					} else {
						
					}
				}
				
			}
		}
	}
}

function regist(){
	var regist_name = $("#regist-name").val();
	console.log("regist_name=" + regist_name);
	var regist_pass = $("#regist-pass").val();
	console.log("regist_pass=" + regist_pass);
	var regist_passag = $("#regist-pass-again").val();
	console.log("regist_passag=" + regist_passag);
	
	if(regist_pass == "" || regist_passag == "" || regist_name == ""){
		$("#regist_button").text("请填写所有项目");
	}
	if(regist_pass != regist_passag){
		$("#d_regist-pass").attr("class","form-group has-error");
		$("#d_regist-pass-again").attr("class","form-group has-error");
		$("#regist_button").text("两次密码输入不一致");
	} else {
		user.register(regist_name,regist_pass,callbackRegisterData);
	}
}

function callbackRegisterData(RegisterData){
	$("#d_regist-name").attr("disabled","disabled");
	$("#d_regist-pass").attr("disabled","disabled");
	$("#d_regist-pass-again").attr("disabled","disabled");
	if(RegisterData.wrongReason == 0){
		
	} else {
		if(RegisterData.wrongReason == 1){
			$("#d_regist-name").attr("class","form-group has-error");
			$("#d_regist-pass").attr("class","form-group has-error");
			$("#d_regist-pass-again").attr("class","form-group has-error");
			$("#regist_button").text("用户已存在");
		} else {
			$("#d_regist-name").attr("class","form-group has-error");
			$("#d_regist-pass").attr("class","form-group has-error");
			$("#d_regist-pass-again").attr("class","form-group has-error");
			$("#regist_button").text("系统错误");
		}
	}
}

function logout(){
	user.logout();
	window.location.href="index.jsp";
}