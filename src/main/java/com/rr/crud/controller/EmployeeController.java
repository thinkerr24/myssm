package com.rr.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rr.crud.bean.Employee;
import com.rr.crud.bean.Msg;
import com.rr.crud.service.EmployeeService;

/*
 * 处理员工CRUD请求
 */
@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	/*
	 * 根据id删除单一(批量)员工
	 * 批量删除:1-2-3
	 * 单一删除:1
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{id}", method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("id")String ids) {
		if (ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");
			// 组装id集合
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		} else {
			Integer id = Integer.parseInt(ids);
		employeeService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	/*
	 * 修改员工信息(/emp/{empId}和empId字段一致)
	 * 如果直接发ajax type=PUT请求，employee中只有empId有值，其它字段全为null
	 * 问题:
	 * 请求体中有数据，但是Employee对象封装不上
	 * update emp_tb where emp_id = xxx
	 * 原因:
	 * Tomcat将请求中的数据，封装成一个map。
	 * request.getParameter("xxx")就从map中取值
	 * 而springmvc封装pojo对象的时候，会把POJO中每个属性的值通过此方法获取
	 * Ajax发送put请求引发的血案：
	 * 		PUT请求， 请求体中的数据，通过request.getParameter("xxx")拿不到
	   我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
	  方法:在web.xml配置springMVC提供的HttpPutFormContentFilter过滤器
	  它将请求体中的数据解析包装成一个map, request被重新包装，request.getParameter()方法被
	  重写, put请求方法就调用自己的map
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}", method=RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	/*
	 * 根据id查询员工
	 */
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/*
	 * 检查用户名是否可用
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName")String empName) {
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.failure().add("va_msg", "用户名必须是2-5位中文或者是6-16位英文和数字的组合");
		}
		// 数据库用户名重复校验
		boolean check = employeeService.checkUser(empName);
		return check?Msg.success():Msg.failure().add("va_msg", "用户名不可用");
	}
	/** 
	 * 保存员工
	 * 1.支持JSR303校验
	 * 2.导入Hibernate-Validator
	 * 3.在bean上加Pattern注解
	 * 4.在controller层方法的参数列表中加入@Valid
	 */
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (!result.hasErrors()) {
		employeeService.saveEmp(employee);
			return Msg.success();
		} else {
			Map<String, String > map = new HashMap<>();
			// 检验失败, 返回失败, 在模态框中显示校验失败的错误信息
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				//System.out.println("错误的字段名:"+fieldError.getField());
				//System.out.println("错误的信息:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.failure().add("errorFields", map);
		}
		
	}
	/*
	 * 导入jackson包
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model) {
		PageHelper.startPage(pn, 5);
		List<Employee> emps =  employeeService.getAll();
		PageInfo<Employee>  page = new PageInfo<>(emps, 5);
		return Msg.success().add("pageInfo", page);
	}
	/*
	 * v1 查询员工数据(分页查询)
	   v2改成以json字符串的方式返回(见方法getEmpsWithJson)
	@RequestMapping("/emps")*/
	public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model) {
		// 这不是一个分页查询，引入PageHelper分页插件
		
		// 在查询之前只需调用(传入页码已经每页大小)
		PageHelper.startPage(pn, 5);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<Employee> emps =  employeeService.getAll();
		// 使用PageInfo封装分页好的数据, 只需将pageinfo交给页面即可
		// PageInfo构造器第二个参数表示连续显示的页数< 1 2 3 4 5 > < 3 4 5 6 7 >
		PageInfo<Employee>  page = new PageInfo<>(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	} 
}
