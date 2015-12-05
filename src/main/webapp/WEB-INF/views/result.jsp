<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>学术资源统一检索系统</title>
<link rel="stylesheet" href="${ctx}/static/styles/pintuer.css">
<script src="${ctx}/static/js/jquery.js"></script>
<script src="${ctx}/static/js/pintuer.js"></script>
<script src="${ctx}/static/js/respond.js"></script>
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon" />
<link href="#/favicon.ico" rel="bookmark icon" />
    <style>
		.demo-nav.fixed.fixed-top{z-index:8;background:#fff;width:100%;padding:0;border-bottom:solid 3px #0a8;-webkit-box-shadow:0 3px 6px rgba(0, 0, 0, .175);box-shadow:0 3px 6px rgba(0, 0, 0, .175);}
    </style>
</head>

<body>
  	<!--顶部-->
    <div class="layout bg-black bg-inverse">
      <div class="container height-large">
        <span class="float-right text-small text-gray hidden-l">
          <a class="text-main" href="#">注册</a><span> | </span><a class="text-main" href="#">登录</a>
        </span>
        欢迎使用<a href="#">统一检索系统</a>
      </div>
    </div>

	<!--导航-->
    <div class="demo-nav padding-big-top padding-big-bottom fixed">
    <div class="container padding-top padding-bottom">
    
      <div class="line">
        <div class="xl12 xs3 xm3 xb2"><button class="button icon-navicon float-right" data-target="#header-demo"></button><a target="_blank" href="#">
        <img src="${ctx}/static/images/logo.png"
						alt="paperretrieval" /></a></div>
        <div class=" xl12 xs9 xm9 xb10 nav-navicon" id="header-demo">
    
          <div class="xs8 xm6 xb5 padding-small">
            <ul class="nav nav-menu nav-inline nav-big">
              <li><a href="#">首页</a></li>
              
              <li class="active"><a href="#">关于实验室<span class="arrow"></span></a>
                <ul class="drop-menu">
                  <li><a href="#">介绍</a></li>
                  <li><a href="#">实验室人员<span class="arrow"></span></a>
                    <ul><li><a href="#">博士</a></li>
					    <li><a href="#">研究生</a></li>
					</ul>
                  </li>
                  <li><a href="#">科技成果介绍</a></li>
                </ul>
              </li>
            <li><a href="#">加入我们</a></li>
            </ul>
          </div>
          
          <div class="hidden-l hidden-s xm3 xb3" style="float:right">
            <div class="text-red text-big icon-phone height-large text-right"> 联系我们 010-67398888</div>
          </div>
    
        </div>
    
      </div>
    
    </div>
    
    </div>
    
    
        
  	<!--内容-->
    <div class="container">
      
 

      <div class="line-big">
	 
        <div class="xl12 xm8">
		
		
  	<br />
		 <div class="xm8" style="width:100%">
		 <div class="button-group border-main checkbox">
		 
  <label class="button">数据库：</label>
  <label class="button"><input name="pintuer" value="2" type="checkbox">知网</label>
  <label class="button"><input name="pintuer" value="3" type="checkbox">Springer</label>
  <label class="button"><input name="pintuer" value="4" type="checkbox">IEEE</label>

</div>
</br>
</br>
		 <form method="post" class="form-inline" >
		 
   <div class="form-group">
        <div class="field">
      <select class="input"  style="width:80px;"   name="cookies">
        <option>------</option>
		<option>题目</option>
        <option>作者</option>
        <option>关键字</option>
      </select>
    </div>
  </div>
  <div class="form-group" >
   
    <div class="field" style="width:600px">
      <input type="text" class="input" id="money" name="money" size="70" placeholder="关键词" />
	   <span class="addbtn"><button type="button" class="button bg-main icon-search"></button></span>
    </div>
  </div> 
			
			 </form>
			  <br />
共用时:
共找到：		
 <br />
  <br />
		<ul class="pagination border-main">
  <li class="disabled"><a href="#">«</a></li>
  <li><a href="#">1</a></li>
  <li class="active"><a href="#">2</a></li>
  <li><a href="#">3</a></li>
  <li><a href="#">4</a></li>
  <li><a href="#">5</a></li>
  <li><a href="#">6</a></li>
  <li><a href="#">7</a></li>
  <li><a href="#">8</a></li>
  <li><a href="#">9</a></li>
  <li><a href="#">10</a></li>
  
  <li><a href="#">»</a></li>
</ul>
        	 </div>
			
            <br />
			
        </div>
      	<div class="xl12 xm4">
        	<h2 class="bg-main text-white padding">你可能喜欢的论文</h2>
            <div class="padding-big bg">
            	<ul class="list-media list-underline">
                	<li>
                        <div class="media media-x">
                          
                          <div class="media-body"><strong>山东软科学研究发展战略方针部署和当前研究重点</strong> 作者：刘君钦&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
                	<li>
                        <div class="media media-x">
                         
                          <div class="media-body"><strong>抓住改革试点机遇 作好科技与经济结合的文章</strong>作者：王旭东&nbsp&nbsp&nbsp&nbsp <a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
                	<li>
                        <div class="media media-x">
                         
                          <div class="media-body"><strong>走科技兴工之路 促枣庄工业快速发展</strong>作者：刁继荣&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
                	<li>
                        <div class="media media-x">
                         
                          <div class="media-body"><strong>德州市一产品荣获巴黎国际发明展览会特别荣誉奖</strong>作者：刘思英&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
                	<li>
                        <div class="media media-x">
                          
                          <div class="media-body"><strong>星火之光——安丘市实施“科教兴安”战略纪实</strong>作者：张念祥,于利民,王志坚&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" style="float：right" href="#">查看详情</a></div>
                        </div>
                    </li>
                	<li>
                        <div class="media media-x">
                        
                          <div class="media-body"><strong>面向经济建设主战场 开创科技外事新局面</strong>作者：姜建国,谢明&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
					<li>
                        <div class="media media-x">
                        
                          <div class="media-body"><strong>面向经济建设主战场 开创科技外事新局面</strong>作者：姜建国,谢明&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
					<li>
                        <div class="media media-x">
                        
                          <div class="media-body"><strong>面向经济建设主战场 开创科技外事新局面</strong>作者：姜建国,谢明&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
					<li>
                        <div class="media media-x">
                        
                          <div class="media-body"><strong>面向经济建设主战场 开创科技外事新局面</strong>作者：姜建国,谢明&nbsp&nbsp&nbsp&nbsp<a class="button button-little border-red swing-hover" href="#">查看详情</a></div>
                        </div>
                    </li>
                </ul>
            </div>
            <br />
        </div>
      </div>
    </div>
    
    <br /><br />
    
    
    <!--底部-->
    <br /><br />
    

    <div class="layout bg-black bg-inverse">
      <div class="container">
        <div class="navbar">
          <div class="navbar-head"><button class="button bg-gray icon-navicon" data-target="#navbar-footer"></button><a href="#" target="_blank"><img class="logo" src="images/24-white.png" alt="拼图前端CSS框架" /></a></div>
          <div class="navbar-body nav-navicon" id="navbar-footer">
            <div class="navbar-text navbar-left hidden-s hidden-l">版权所有 &copy; <a href="#" target="_blank">www.bjut.edu.cn</a> All Rights Reserved，图ICP备：380959609</div>
            <ul class="nav nav-inline navbar-right"><li><a href="#">首页</a></li><li><a href="#">CSS</a></li><li><a href="#">关于实验室</a></li><li><a href="#">加入我们</a></li></ul>
          </div>
        </div>
      </div>
    </div>
    <div class="hidden"><script src="http://s4.cnzz.com/stat.php?id=5952475&web_id=5952475" language="JavaScript"></script></div>
</body>
</html>