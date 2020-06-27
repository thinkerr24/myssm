<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工列表</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.12.4.min.js"></script>
<!-- 引入bs样式 (两种资源相对路径写法, 以\开头的和不以\开头的，下面选用前者)-->
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet"/>
<script  src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script> 
</head>
<body>
<!-- 员工修改的模态框 -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">员工修改</h4>
      </div>
      <div class="modal-body">
       	<form class="form-horizontal">
  <div class="form-group">
    <label for="empName_add_input" class="col-sm-2 control-label">员工名</label>
    <div class="col-sm-10">
    	<p class="form-control-static" id="empName_update_static"></p>
    	<span class="help-block"></span>
    </div>
  </div>
  <div class="form-group">
    <label for="email_update_input" class="col-sm-2 control-label">邮箱</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" name="email" id="email_update_input" placeholder="email@github.com">
      <span class="help-block"></span>
    </div>
  </div>
    <div class="form-group">
    <label class="col-sm-2 control-label">性别</label>
    <div class="col-sm-10">
     	<label class="radio-inline">
		    <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
		</label>
		<label class="radio-inline">
			<input type="radio" name="gender" id="gender2_update_input" value="F"> 女
		</label>
    </div>
  </div>
     <div class="form-group">
    <label  class="col-sm-2 control-label">部门</label>
    <div class="col-sm-4">
    	<!--  提交部门id即可-->
     	<select class="form-control" name="dId" >
     	</select>
    </div>
  </div>
</form>
       	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary"  id="emp_update_btn">更新</button>
      </div>
    </div>
  </div>
</div>

<!-- 员工添加的模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
      </div>
      <div class="modal-body">
       	<form class="form-horizontal">
  <div class="form-group">
    <label for="empName_add_input" class="col-sm-2 control-label">员工名</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="empName" id="empName_add_input" placeholder="empName">
    	<span class="help-block"></span>
    </div>
  </div>
  <div class="form-group">
    <label for="email_add_input" class="col-sm-2 control-label">邮箱</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" name="email" id="email_add_input" placeholder="email@github.com">
      <span class="help-block"></span>
    </div>
  </div>
    <div class="form-group">
    <label class="col-sm-2 control-label">性别</label>
    <div class="col-sm-10">
     	<label class="radio-inline">
		    <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
		</label>
		<label class="radio-inline">
			<input type="radio" name="gender" id="gender2_add_input" value="F"> 女
		</label>
    </div>
  </div>
     <div class="form-group">
    <label  class="col-sm-2 control-label">部门</label>
    <div class="col-sm-4">
    	<!--  提交部门id即可-->
     	<select class="form-control" name="dId" >
     	</select>
    </div>
  </div>
</form>
       	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary"  id="emp_save_btn">保存</button>
      </div>
    </div>
  </div>
</div>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>员工部门管理系统</h1>
			</div>
		</div>
		<!-- 新增删除按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<a href="${pageContext.request.contextPath}/login">登录</a> &nbsp;&nbsp;&nbsp;
				<c:if test="${cookie['currentUser'] != null}">
							<button class="btn btn-primary" id="emp_add_modal_btn">新增</button> &nbsp;&nbsp;&nbsp;
							<button class="btn btn-danger" id="emp_delete_all_btn">删除</button> 
				</c:if>
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
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>员工号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>邮箱</th>
							<th>部门</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area">
			
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
			
			</div>
		</div>
	</div>
	<script type="text/javascript">
		// 定义一个全局记录数 
		var totalRecord, currentPage;
		// 1.页面加载完成以后, 发送ajax请求, 要到分页数据
		$(function(){
			// 去首页
			to_page(1);
		});
		
		function to_page(pn){
			$.ajax({
 				url:"${pageContext.request.contextPath}/emps",
				data:"pn="+pn,
				type:"GET",
				success:function(result){
					// 1.解析并显示员工数据
					//	console.log(result);
					build_emps_table(result);
				
				
					// 2.解析并显示分页信息
					build_page_info(result);
					
					// 3.解析并显示分页条
					build_page_nav(result);
				}
			}); 
		}
		// 解析显示员工数据
 		function build_emps_table(result) {
			// 清空table表格
			 $("#emps_table tbody").empty();
			var emps = result.jdata.pageInfo.list;
			$.each(emps, function(index, item){
				var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
				//alert(item.empName);
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(item.gender == 'M' ? "男":"女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>").append(item.department.deptName);
				var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
							  .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
				// 为编辑按钮添加一个自定义属性来表示当前当前员工id
				editBtn.attr("edit-id", item.empId);
				var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
				 			 .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除 ");
				
				var operationTd = $("<td></td>").append(editBtn).append("&nbsp;&nbsp;").append(delBtn);
				delBtn.attr("del-id", item.empId);
				$("<tr></tr>").append(checkBoxTd)
							  .append(empIdTd)
							  .append(empNameTd)
							  .append(genderTd)
							  .append(emailTd)
							  .append(deptNameTd)
							  .append(operationTd)
							  .appendTo("#emps_table tbody");
			});
		}
		
 		// 解析显示分页条
		function build_page_info(result) {
 				$("#page_info_area").empty();
 				$("#page_info_area").append("<span>当前"+ result.jdata.pageInfo.pageNum +"页, 总 "+ result.jdata.pageInfo.pages +"页, 总"+ result.jdata.pageInfo.total +"记录数</span>");
 				totalRecord = result.jdata.pageInfo.total;
 				currentPage = result.jdata.pageInfo.pageNum;
 		}
		// 解析显示分页条, 点击分页去某一页
		function build_page_nav(result) {
			$("#page_nav_area").empty();
			// 构建元素
			var ul = $("<ul></ul>").addClass("pagination");
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "javascript:;"));
			var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
			if (result.jdata.pageInfo.hasPreviousPage == false){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			} else {
				// 为元素添加点击事件
				firstPageLi.click(function(){
					to_page(1);
				});
				prePageLi.click(function(){
					to_page(result.jdata.pageInfo.pageNum - 1);
				});
			}
			
			var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "javascript:;"));
			if (result.jdata.pageInfo.hasNextPage == false) {
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			} else {
				nextPageLi.click(function(){
					to_page(result.jdata.pageInfo.pageNum + 1);
				});
				lastPageLi.click(function(){
					to_page(result.jdata.pageInfo.pages);
				});
			}
			
			ul.append(firstPageLi).append(prePageLi);
			$.each(result.jdata.pageInfo.navigatepageNums, function(index, item){
				var numPageLi = $("<li></li>").append($("<a></a>").append(item));
				if (result.jdata.pageInfo.pageNum == item) {
					numPageLi.addClass("active");
				} 
				numPageLi.click(function(){
					to_page(item);
				});
				ul.append(numPageLi);
			});
			ul.append(nextPageLi).append(lastPageLi);
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		} 
		
		// 表单重置
		function reset_form(ele){
			// 表单重置(表单数据和样式)
			$(ele).get(0).reset();
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		
		// 点击新增按钮弹出模态框
		$("#emp_add_modal_btn").click(function(){
			reset_form("#empAddModal form");
			//$("#empAddModal select").empty();
			getDepts("#empAddModal select");
			
			//发送Ajax，查出部门信息，显示在下拉列表中
			$("#empAddModal").modal({
				backdrop:"static"
			});
		});
		
		// 查出部门信息
		function getDepts(ele){
			// 清空下拉列表的值
			$(ele).empty();
			$.ajax({
				url:"${pageContext.request.contextPath}/depts",
				type:"GET",
				success:function(result){
					//console.log(result);
				// {"code":100,"msg":"处理成功!","jdata":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
					$.each(result.jdata.depts, function(){
						var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
						optionEle.appendTo(ele);
					});	
				}
			});
		}
		// 校验新增员工表单数据的方法
		function validate_add_form(){
			// 1.拿到要校验的数据, 使用正则表达式
			var empName = $("#empName_add_input").val();
			var regName =  /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			if(!regName.test(empName)){
				//alert("用户名可以是2-5位中文或者是6-16位英文和数字的组合");
				show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者是6-16位英文和数字的组合");
				return false;
			} else  {
				show_validate_msg("#empName_add_input", "success", "");
				
			}
			// 2.校验邮箱信息
			var email = $("#email_add_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!regEmail.test(email)){
				//alert("邮箱格式不正确!");
				// 清空这个元素之前的样式
				show_validate_msg("#email_add_input", "error","邮箱格式不正确!");
				return false;
			} else {
				show_validate_msg("#email_add_input", "success","");
			}
			return true;
		}
		
		// 显示校验结果的提示信息
		function show_validate_msg(ele, status, msg) {
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if ("success" == status) {
				$(ele).parent().addClass("has-success");
			
			} else if ("error" == status) {
				$(ele).parent().addClass("has-error");
			}
			$(ele).next("span").text(msg);
		}
		// 检验用户名是否可用
		$("#empName_add_input").change(function(){
			// 发送ajax请求看用户名是否可用
			var empName = this.value;
			$.ajax({
				url:"${pageContext.request.contextPath}/checkuser",
				data:"empName="+empName,
				type:"POST",
				success:function(result){
					if (result.code == 100){
						show_validate_msg("#empName_add_input", "success", "用户名可用");
						$("#emp_save_btn").attr("ajax-va", "success");
					} else {
						show_validate_msg("#empName_add_input", "error", result.jdata.va_msg);
						$("#emp_save_btn").attr("ajax-va", "error");
					}
				}
			});
		});
		
		// 点击保存, 保存员工
		$("#emp_save_btn").click(function(){
			
			// 1.将模态框中的数据提交给服务器保存(先要对提交给服务器的数据进行校验)
			// 并添加 员工名可用判断
			// 绕开前端校验
			/* if(!validate_add_form() || $(this).attr("ajax-va")=="error"){
				return false;
			} */
			
			// 2.发送ajax请求保存员工(serialize():Jquery序列化方法，方便收集表单数据)
	 		$.ajax({
				url:"${pageContext.request.contextPath}/emp",
				type:"POST",
				data:$("#empAddModal form").serialize(),
				success:function(result){
					//alert(result.msg);
					if (result.code == 100) {
					// 1.员工保存成功; 
					$("#empAddModal").modal('hide');
					// 2.来到最后一页，显示刚才保存的数据 
					// 发送ajax请求显示最后一页数据即可
					to_page(totalRecord);
					} else {
						// 显示失败信息
						// console.log(result);
						// 有哪个字段的错误信息就显示那个字段的
						if (undefined != result.jdata.errorFields.email) {
							show_validate_msg("#email_add_input", "error", result.jdata.errorFields.email);
						}
						if (undefined != result.jdata.errorFields.empName) {
							show_validate_msg("#empName_add_input", "error", result.jdata.errorFields.empName);
						}
					}
				}
			}); 
		});
		
		
		// 为编辑按钮绑定事件
		$(document).on("click",".edit_btn", function(){
			// 查出员工信息，显示员工信息
			getDepts("#empUpdateModal select");
			getEmp($(this).attr("edit-id"));
			
			// 把员工的id传递给模态框的更新按钮
			$("#emp_update_btn").attr("edit-id", $(this).attr("edit-id"));
			$("#empUpdateModal").modal({
				backdrop:"static"
			});
		});
		
		function getEmp(id){
			$.ajax({
				url:"${pageContext.request.contextPath}/emp/"+id,
				type:"GET",
				success:function(result){
					//cosole.log(result);
					var empData = result.jdata.emp;
					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdateModal input[name=gender]").val([empData.gender]);
					$("#empUpdateModal select").val([empData.dId]);
				}
			});
		}
		
		// 点击更新, 更新员工信息
		$("#emp_update_btn").click(function(){
			// 1.验证邮箱是否合法
			var email = $("#email_update_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!regEmail.test(email)){
				//alert("邮箱格式不正确!");
				// 清空这个元素之前的样式
				show_validate_msg("#email_update_input", "error","邮箱格式不正确!");
				return false;
			} else {
				show_validate_msg("#email_update_input", "success","");
			}
			// 2.发送ajax请求保存更新的员工数据
			$.ajax({
				 url:"${pageContext.request.contextPath}/emp/"+$(this).attr("edit-id"),
				 type:"PUT",
				 data:$("#empUpdateModal form").serialize(),
				 success:function(result){
					// alert(result.msg);
					// 1. 关闭对话框
					$("#empUpdateModal").modal("hide");
					// 2. 回到本页面
					 to_page(currentPage);
				 }
			});
		});
		
		// 为删除按钮绑定事件(单个删除)
		$(document).on("click",".delete_btn", function(){
			// 1. 弹出是否确认删除警告框
			var empName = $(this).parents("tr").find("td:eq(2)").text();
			var empId = $(this).attr("del-id");
			if (confirm("确认删除["+ empName +"]吗?")){
				// 确认删除,发送ajax请求
				$.ajax({
					url:"${pageContext.request.contextPath}/emp/"+ empId,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						// 回到本页
						 to_page(currentPage);
					}
				});
				
			}
		});
		// 完成全选/全不选功能
		$("#check_all").click(function(){
			// attr获取checked属性是undefined(自定义属性)，改用dom原生属性prop
			$(".check_item").prop("checked", $(this).prop("checked"));
		});
		
		$(document).on("click", ".check_item", function(){
			// 判断当前选中的元素是否是五个(选满)
			var flag = $(".check_item:checked").length == $(".check_item").length;
			$("#check_all").prop("checked", flag);
		});
		
		// 点击批量删除按钮
		$("#emp_delete_all_btn").click(function(){
			var empNames = "";
			var del_idstr = "";
			$.each($(".check_item:checked"), function(){
				empNames += $(this).parents("tr").find("td:eq(2)").text() + ", ";
				// 组装员工id字符串
				del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
			});
			empNames = empNames.substring(0,  empNames.length - 2);
			del_idstr = del_idstr.substring(0, del_idstr.length - 1);
			if (confirm("确认删除["+ empNames +"]吗?")) {
				// 发送ajax请求
				$.ajax({
					url:"${pageContext.request.contextPath}/emp/"+del_idstr,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						to_page(currentPage);
					}
				});
			}
		}); 
	</script>
</body>
</html>