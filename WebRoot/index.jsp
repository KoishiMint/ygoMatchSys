<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta charset="utf-8">
    <base href="<%=basePath%>">
    
    <title>项目名称</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="项目关键词">
	<meta http-equiv="description" content="项目描述">
	
    <link href="dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="dist/css/flat-ui.css" rel="stylesheet">
	
	<!-- 日历 -->
	<link rel="stylesheet" href="calendar/css/style.css" />

    <link rel="shortcut icon" href="img/favicon.ico">
  </head>
  
  <body onload="checkLogin()">
  	<!-- 顶部固定条 -->
    <div class="row demo-row" id="main">
      <div class="col-xs-12">
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-01">
              <span class="sr-only">Toggle navigation</span>
            </button>
            <a class="navbar-brand" href="#">这里放logo</a>
          </div>
          <div class="collapse navbar-collapse" id="navbar-collapse-01">
            <ul class="nav navbar-nav navbar-left">
              <li><a href="#fakelink">主页</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">比赛管理<b class="caret"></b></a>
                <span class="dropdown-arrow"></span>
                <ul class="dropdown-menu">
                  <li><a href="#" id="d_create_match" style="display:none;"></a></li>
                  <li><a href="#" id="d_look_match">查看比赛</a></li>
                  <li><a href="#" id="d_change_match_model" style="display:none;"></a></li>
                </ul>
              </li>
              <li><a href="#fakelink" id="d_admin" style="display:none;"></a></li>
              <li><a href="#fakelink" id="d_banlist" style="display:none;"></a></li>
              <li><a href="#fakelink" id="d_change_gonggao" style="display:none;"></a></li>
              <li id="not-logined"><a href="#fakelink" onclick="getLoginBlock()">登陆</a></li>
              <li class="dropdown" id="already-login">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" id="already-login-name">用户名 <b class="caret"></b></a>
                <span class="dropdown-arrow"></span>
                <ul class="dropdown-menu">
                  <li><a href="#" id="show-name">尊敬的用户您好</a></li>
                  <li><a href="#" id="show-ID">UID：</a></li>
                  <li><a href="#" id="show-logintime">上次登陆时间</a></li>
                  <li><a href="#" id="show-IP">本次登陆IP</a></li>
                  <li><a href="#" id="show-lastIP">上次登陆IP</a></li>
                  <li class="divider"></li>
                  <li><a href="#" onclick="logout()">注销</a></li>
                  <li><a href="#" onclick="help()">联系我们</a></li>
                </ul>
              </li>
             </ul>
             <form class="navbar-form navbar-right" action="#" role="search">
              <div class="form-group">
                <div class="input-group">
                  <input class="form-control" id="navbarInput-01" type="search" placeholder="查找选手、比赛">
                  <span class="input-group-btn">
                    <button type="submit" class="btn"><span class="fui-search"></span></button>
                  </span>
                </div>
              </div>
            </form>
          </div>
        </nav>
      </div>
    </div>
    
    <br>
    <br>
    <!-- 登陆 -->
    <div class="container" style="position:fixed;width:50%;left:0; right:0; top:10%; bottom:0;display:none;" name="loginBlock">
      <div class="login-form" name="loginBlock">
        <div class="form-group" name="loginBlock">
          <input class="form-control login-field" value="" placeholder="Enter your name" id="login-name" type="text">
          <label class="login-field-icon fui-user" for="login-name"></label>
        </div>
        <div class="form-group" name="loginBlock">
          <input class="form-control login-field" value="" placeholder="Password" id="login-pass" type="password">
          <label class="login-field-icon fui-lock" for="login-pass"></label>
        </div>

        <a class="btn btn-primary btn-lg btn-block" onclick="login()" id="login_button">登陆</a>
        <a class="login-link" href="#">遗失密码？</a>
        <a class="login-link" onclick="getRegistBlock()">还没有账号？</a>
        <a class="login-link" onclick="exitLoginBlock()">返回</a>
      </div>
    </div>
    
    <!-- 注册 -->
    <div class="container" style="position:fixed;width:50%;left:0; right:0; top:10%; bottom:0;display:none;" name="registBlock">
      <div class="login-form" name="registBlock">
        <div class="form-group" name="registBlock" id="d_regist-name">
          <input class="form-control login-field" value="" placeholder="Enter your name" id="regist-name" type="text">
          <label class="login-field-icon fui-user" for="login-name"></label>
        </div>
        <div class="form-group" name="registBlock" id="d_regist-pass">
          <input class="form-control login-field" value="" placeholder="Password" id="regist-pass" type="password">
          <label class="login-field-icon fui-lock" for="login-pass"></label>
        </div>
        <div class="form-group" name="registBlock" id="d_regist-pass-again">
          <input class="form-control login-field" value="" placeholder="Password" id="regist-pass-again" type="password">
          <label class="login-field-icon fui-lock" for="login-pass"></label>
        </div>
        <a class="btn btn-primary btn-lg btn-block" onclick="regist()" id="regist_button">注册</a>
        <a class="login-link" onclick="exitRegistBlock()">返回</a>
      </div>
    </div>
    
    <div class="container">
      <div class="col-xs-4" id="proclamation_info">
      	<a>这里写公告</a>
      	<!-- 创建比赛 -->
      	<div class="row" id="d_match_create" style="display:none;">
	        <div class="col-md-12">
	          <h4>创建比赛</h4>
	          <form action="#" class="form">
	            <div class="form-group">
	              <input type="text" class="form-control" placeholder="请输入比赛名称" id="match-name">
	            </div>
	            <div class="form-group">
	              <textarea class="form-control" rows="3" placeholder="请输入比赛介绍" style="max-width:100%;" id="match-info"></textarea>
	            </div>
	            <div class="form-group">
	              <h9>比赛时间</h9>
	              <div class="case">
					<div class="calendarWarp" style="">
						<input type="text" name="date" class='ECalendar' id="ECalendar_date"  value=""/>
					</div>
			      </div>
	              <div>
	              <label class="radio" for="radio4a">
	                <input type="radio" name="macth-type" data-toggle="radio" value="1" id="radio4a" required checked>
	                 	淘汰赛
	              </label>
	              <label class="radio" for="radio4b">
	                <input type="radio" name="macth-type" data-toggle="radio" value="2" id="radio4b" required>
	                 	双败淘汰赛
	              </label>
	              <label class="radio" for="radio4c">
	                <input type="radio" name="macth-type" data-toggle="radio" value="3" id="radio4c" required>
	                 	瑞士轮
	              </label>
	              </div>
	              <h9>上限人数</h9>
	              <select data-toggle="select" class="form-control select select-default mrs mbm" id="match-max-num">
         			 <option value="4">4</option>
         			 <option value="8">8</option>
         			 <option value="16">16</option>
         			 <option value="32">32</option>
         			 <option value="64">64</option>
         			 <option value="128">128</option>
	    		  </select>
	              <h9>禁止卡表</h9>
	              <select data-toggle="select" class="form-control select select-default mrs mbm" id="match-ban-list">
         			 <option value="4">4</option>
	    		  </select>
	    		  <input type="button" value="发布" class="btn btn-primary" onclick="create_match()">
	           </div>
	          </form>
	        </div>
   		</div><!-- /.row -->
      </div>
      <div class="col-xs-8">
      	<!-- 比赛内容 -->
      	<blockquote id="match_info">
      	  <h6>比赛名称</h6>
      	  <a>报名中</a>&nbsp&nbsp<text>比赛时间：2017年9月19日</text>&nbsp&nbsp<text>人数：1</text>
      	  <p><small>比赛说明</small></p>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-primary">查看人员</a></div>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-inverse">报名</a></div>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-danger">取消报名</a></div>
      	  <br><br>
      	  
      	  <h6>比赛名称</h6>
      	  <a>比赛中</a>&nbsp&nbsp<text>比赛时间：2017年9月19日</text>&nbsp&nbsp<text>人数：1</text>
      	  <p><small>比赛说明</small></p>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-primary">查看人员</a></div>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-inverse disabled">报名</a></div>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-danger disabled">取消报名</a></div>
      	  <br><br>
      	  <h6>比赛名称</h6>
      	  <a>已结束</a>&nbsp&nbsp<text>比赛时间：2017年9月19日</text>&nbsp&nbsp<text>人数：1</text>
      	  <p><small>比赛说明</small></p>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-primary">查看人员</a></div>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-inverse">导出比赛信息</a></div>
      	  <div class="col-xs-4"><a href="#fakelink" class="btn btn-block btn-lg btn-danger">取消比赛</a></div>
      	</blockquote>
      </div>
    </div>
    
    
   	<!-- 回到最上方 -->
   	<div class="col-xs-12">
     <a class="btn btn-block btn-lg btn-default" style="position:fixed;right:30px;buttom:30px;width:100px;"><span class="fui-triangle-up" onclick="goTop()"></span></a>
   	</div>
    <!-- dwr -->
	<script src="dwr/engine.js"></script>
	<script src="dwr/util.js"></script>
	<!-- dwr interface -->
	<script src="dwr/interface/user.js"></script>
	<script src="dwr/interface/host.js"></script>
	<!-- jquery -->
    <script src="dist/js/vendor/jquery.min.js"></script>
    <script src="dist/js/vendor/video.js"></script>
    <!-- falt ui -->
    <script src="dist/js/flat-ui.min.js"></script>
    <script src="docs/assets/js/application.js"></script>
    <!-- 日历 -->
	<script src="calendar/js/Ecalendar.jquery.min.js" charset="utf-8"></script>
    <!-- 自定义 -->
    <script src="myjs/index.js?version="<%=Math.random() %> ></script>
    <script src="myjs/login.js?version="<%=Math.random() %>></script>
    <script src="myjs/host.js?version="<%=Math.random() %>></script>
    <script charset="utf-8">
      $(function(){
		$("#ECalendar_date").ECalendar({
			 type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
			 stamp : true,   //是否转成时间戳，默认true;
			 offset:[250,-100],   //弹框手动偏移量;
			 format:"yyyy年mm月dd日 hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
			 skin:5,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
			 step:10,   //选择时间分钟的精确度;
			 callback:function(v,e){
			 } //回调函数
		});
	  });
	  $(function(){
		$("#ECalendar_date").ECalendar({
			 type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
			 stamp : true,   //是否转成时间戳，默认true;
			 offset:[250,-100],   //弹框手动偏移量;
			 format:"yyyy年mm月dd日 hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
			 skin:5,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
			 step:10,   //选择时间分钟的精确度;
			 callback:function(v,e){
			 } //回调函数
		});
	  });
    </script>
  </body>
</html>
