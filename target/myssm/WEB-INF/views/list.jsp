<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工列表</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.12.4.min.js"></script>
<!-- 引入bs样式 (两种资源相对路径写法, 以\开头的和不以\开头的，下面选用前者)-->
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet"/>
<script  src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script> 
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>雇员部门管理系统</h1>
			</div>
		</div>
		<!-- 新增删除按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button> &nbsp;&nbsp;&nbsp;
				<button class="btn btn-danger">删除</button> 
			</div>
		</div>
		<div class="row">
		  <!-- 换行分割 -->
			<div class="col-md-12">
				<br>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>员工号</th>
						<th>姓名</th>
						<th>邮箱</th>
						<th>性别</th>
						<th>部门</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pageInfo.list}" var="emp">
						<tr>
							<td>${emp.empId}</td>
							<td>${emp.empName}</td>
							<td>${emp.email}</td>
							<td>${emp.gender == "M" ? "男":"女"}</td>
							<td>${emp.department.deptName}</td>
							<td>
								<button class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										编辑
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">
				<span>当前${pageInfo.pageNum}页, 总${pageInfo.pages}页, 总${pageInfo.total}记录数</span>
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				<ul class="pagination">
					<li><a href="${pageContext.request.contextPath}/emps?pn=1">首页</a></li>
					<c:if test="${pageInfo.hasPreviousPage}">
						<li><a href="${pageContext.request.contextPath}/emps?pn=${pageInfo.pageNum - 1}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
					</c:if>
					<c:if test="${!pageInfo.hasPreviousPage}">
						<li class="disabled"><a href="javascript:;" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
					</c:if>
					<c:forEach items="${pageInfo.navigatepageNums}" var="page_num">
						<c:if test="${page_num == pageInfo.pageNum}">
							<li class="active"><a href="javascript:;">${page_num}</a></li>
						</c:if>
						<c:if test="${page_num != pageInfo.pageNum}">
							<li><a href="${pageContext.request.contextPath}/emps?pn=${page_num}">${page_num}</a></li>
						</c:if>
					</c:forEach>
					<c:if test="${pageInfo.hasNextPage }">
						<li><a href="${pageContext.request.contextPath}/emps?pn=${pageInfo.pageNum + 1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
					</c:if>
					<c:if test="${!pageInfo.hasNextPage }">
						<li class="disabled"><a href="javascript:;" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
					</c:if>
					<li><a href="${pageContext.request.contextPath}/emps?pn=${pageInfo.pages}">末页</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>