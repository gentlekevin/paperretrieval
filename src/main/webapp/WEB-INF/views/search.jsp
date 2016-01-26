<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<script type="text/javascript" >
  function subForm(value){
	  
	  $("#currentPage").val(value);
	  $("#searchForm").submit();
  }
</script>
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico"
	rel="shortcut icon" />
<link href="#/favicon.ico" rel="bookmark icon" />
<style>
.demo-nav.fixed.fixed-top {
	z-index: 8;
	background: #fff;
	width: 100%;
	padding: 0;
	border-bottom: solid 3px #0a8;
	-webkit-box-shadow: 0 3px 6px rgba(0, 0, 0, .175);
	box-shadow: 0 3px 6px rgba(0, 0, 0, .175);
}
</style>
</head>

<body>
	<!--顶部-->
	<div class="layout bg-black bg-inverse">
		<div class="container height-large">
			<span class="float-right text-small text-gray hidden-l"> <a
				class="text-main" style="cursor:pointer;"  href="${ctx}/register" >注册</a><span> | </span><a
				class="text-main" style="cursor:pointer;"  href="${ctx}/login">登录</a>
			</span> 欢迎使用<a href="#">统一检索系统</a>
		</div>
	</div>

	<!--导航-->
	<div class="demo-nav padding-big-top padding-big-bottom fixed">
		<div class="container padding-top padding-bottom">

			<div class="line">
				<div class="xl12 xs3 xm3 xb2">
					<button class="button icon-navicon float-right"
						data-target="#header-demo"></button>
					<a target="_blank" href="${ctx}/" target="_self"> <img
						src="${ctx}/static/images/logo.png" alt="paperretrieval" /></a>
				</div>
				<div class=" xl12 xs9 xm9 xb10 nav-navicon" id="header-demo">

					<div class="xs8 xm6 xb5 padding-small">
						<ul class="nav nav-menu nav-inline nav-big">
							<li><a href="${ctx}/" target="_self">首页</a></li>

							<li class="active"><a href="#">关于实验室<span class="arrow"></span></a>
								<ul class="drop-menu">
									<li><a href="#">介绍</a></li>
									<li><a href="#">实验室人员<span class="arrow"></span></a>
										<ul>
											<li><a href="#">博士</a></li>
											<li><a href="#">研究生</a></li>
										</ul></li>
									<li><a href="#">科技成果介绍</a></li>
								</ul></li>
							<li><a href="#">加入我们</a></li>
						</ul>
					</div>

					<div class="hidden-l hidden-s xm3 xb3" style="float: right">
						<div class="text-red text-big icon-phone height-large text-right">
							联系我们 010-67398888</div>
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
				 
				<div class="xm8" style="width: 100%">
				<form method="post" id="searchForm" class="form-inline" action = '${ctx}/search'>
					<div class="button-group border-main checkbox">
              <c:set var="database" value="${searchBean.database}"/>
              
              <label class="button">数据库：</label> 
             
              	           <c:choose>
								<c:when	test="${fn:contains(database,'cnki')}">
							<label class="button active"><input name="database" value="cnki" checked="checked"	 type="checkbox">知网</label>
                                 </c:when>
							
								<c:otherwise>
							<label class="button"><input name="database" value="cnki"  type="checkbox">知网</label>
								</c:otherwise>
							</c:choose>
							
							 <c:choose>
								<c:when	test="${fn:contains(database,'springer')}">
							<label class="button active"><input name="database" value="springer" checked="checked"	 type="checkbox">Springer</label>
                                 </c:when>
							
								<c:otherwise>
							<label class="button"><input name="database" value="springer"  type="checkbox">Springer</label>
								</c:otherwise>
							</c:choose>
								 <c:choose>
								<c:when	test="${fn:contains(database,'ieee')}">
							<label class="button active"><input name="database" value="ieee" checked="checked"	 type="checkbox">IEEE</label>
                                 </c:when>
							
								<c:otherwise>
							<label class="button"><input name="database" value="ieee"  type="checkbox">IEEE</label>
								</c:otherwise>
							</c:choose>
							
											</div>
					<br> <br>
					

						<div class="form-group">
							<div class="field">
								<select class="input" style="width: 80px;" name="searchfield">
									<option  value="">------</option>
									<option   ${searchBean.searchfield== 'title' ? 'selected' : ''}   value="title">题目</option>
									<option   ${searchBean.searchfield== 'author' ? 'selected' : ''}  value="author">作者</option>
									<option  ${searchBean.searchfield== 'keyword' ? 'selected' : ''}   value="keyword">关键字</option>
								</select>
							</div>
						</div>
						<div class="form-group">

							<div class="field" style="width: 600px">
								<input type="text" class="input" id="searchwords" name="searchwords" size="70"
								 placeholder="关键词" value="${searchBean.searchwords}"/> 
								<span class="addbtn">
								<button	type="button" class="button bg-main icon-search" onclick="subForm(1);"></button></span>
							</div>
						</div>
                            <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}" />
					</form>
				</div>
				
				
				<div class="xl12 xm8" style="width: 100%">
				<br> 共 ${pager.totalPages} 页/${pager.totalRows}条记录 <br/>
					<div class="xl12 xm4" style="width: 100%;padding-left:0px">
								<div class="padding-big bg">
					<ul class="list-media list-underline">
					
					<c:forEach var="paper" items="${papers}">
					   <li>
							<div class="media media-x">

								<div class="media-body">
									<a href="${ctx}/search/detail?paperId=${paper.id}"><strong>${paper.title}</strong></a>
									作者：${paper.author}&nbsp&nbsp 关键字：${paper.keyword}&nbsp&nbsp 数据库：${paper.sourbase}
								</div>
							</div>
						</li>
					
					</c:forEach>
									
					</ul>
				</div>
				<br />
			</div>
		</div>		
					
				<div style="padding-left:10%">
				
				
				
					<ul class="pagination border-main">
						<c:if test="${pager.hasPreviousPage}">
							<li><a  onclick="subForm(1);">首页</a></li>
						</c:if>
						<c:forEach var="every" items="${pager.pageCodes}">
							<c:choose>
								<c:when	test="${every ne fn:trim(pager.currentPage) && every eq '..'}">
								 ${every} 
                                 </c:when>
								<c:when test="${every ne fn:trim(pager.currentPage)}">
									<li><a   onclick="subForm(${every});">${every}</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a>${every}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pager.hasNextPage}">
							<li><a onclick="subForm(${pager.nextPage});" >下页</a></li>
							<li><a onclick="subForm(${pager.totalPages});">末页</a></li>
						</c:if>
					</ul>
				</div>
				<br />

			</div>
			<div class="xl12 xm4">
				<h2 class="bg-main text-white padding">热门文献</h2>
				<div class="padding-big bg">
					<ul class="list-media list-underline">
						<c:forEach var="rp" items="${hotPapers}">
						<li>
							<div class="media media-x">

								<div class="media-body">
									<strong>${rp.title}</strong>
									作者：${rp.author}&nbsp&nbsp&nbsp&nbsp<a
										class="button button-little border-red swing-hover" href="${ctx}/search/detail?paperId=${rp.id}">查看详情</a>
								</div>
							</div>
						</li>
						
						</c:forEach>
						
					
					</ul>
				</div>
				<br />
			</div>
		</div>
	</div>

	<br />
	<br />


	<!--底部-->
	<br />
	<br />


	<div class="layout bg-black bg-inverse">
		<div class="container">
			<div class="navbar">
			
				<div class="navbar-body nav-navicon" id="navbar-footer">
					<div class="navbar-text navbar-left hidden-s hidden-l">
						版权所有 &copy; <a href="#" target="_blank">www.bjut.edu.cn</a> All
						Rights Reserved，图ICP备：380959609
					</div>
					<ul class="nav nav-inline navbar-right">
						<li><a href="#">首页</a></li>
						<li><a href="#">CSS</a></li>
						<li><a href="#">关于实验室</a></li>
						<li><a href="#">加入我们</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="hidden">
		<script src="http://s4.cnzz.com/stat.php?id=5952475&web_id=5952475"
			language="JavaScript"></script>
	</div>
</body>
</html>