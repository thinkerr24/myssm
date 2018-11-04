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
 * ����Ա��CRUD����
 */
@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	/*
	 * ����idɾ����һ(����)Ա��
	 * ����ɾ��:1-2-3
	 * ��һɾ��:1
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{id}", method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("id")String ids) {
		if (ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");
			// ��װid����
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
	 * �޸�Ա����Ϣ(/emp/{empId}��empId�ֶ�һ��)
	 * ���ֱ�ӷ�ajax type=PUT����employee��ֻ��empId��ֵ�������ֶ�ȫΪnull
	 * ����:
	 * �������������ݣ�����Employee�����װ����
	 * update emp_tb where emp_id = xxx
	 * ԭ��:
	 * Tomcat�������е����ݣ���װ��һ��map��
	 * request.getParameter("xxx")�ʹ�map��ȡֵ
	 * ��springmvc��װpojo�����ʱ�򣬻��POJO��ÿ�����Ե�ֵͨ���˷�����ȡ
	 * Ajax����put����������Ѫ����
	 * 		PUT���� �������е����ݣ�ͨ��request.getParameter("xxx")�ò���
	   ����Ҫ��֧��ֱ�ӷ���PUT֮�������Ҫ��װ�������е�����
	  ����:��web.xml����springMVC�ṩ��HttpPutFormContentFilter������
	  �����������е����ݽ�����װ��һ��map, request�����°�װ��request.getParameter()������
	  ��д, put���󷽷��͵����Լ���map
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}", method=RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	/*
	 * ����id��ѯԱ��
	 */
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/*
	 * ����û����Ƿ����
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName")String empName) {
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.failure().add("va_msg", "�û���������2-5λ���Ļ�����6-16λӢ�ĺ����ֵ����");
		}
		// ���ݿ��û����ظ�У��
		boolean check = employeeService.checkUser(empName);
		return check?Msg.success():Msg.failure().add("va_msg", "�û���������");
	}
	/** 
	 * ����Ա��
	 * 1.֧��JSR303У��
	 * 2.����Hibernate-Validator
	 * 3.��bean�ϼ�Patternע��
	 * 4.��controller�㷽���Ĳ����б��м���@Valid
	 */
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (!result.hasErrors()) {
		employeeService.saveEmp(employee);
			return Msg.success();
		} else {
			Map<String, String > map = new HashMap<>();
			// ����ʧ��, ����ʧ��, ��ģ̬������ʾУ��ʧ�ܵĴ�����Ϣ
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				//System.out.println("������ֶ���:"+fieldError.getField());
				//System.out.println("�������Ϣ:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.failure().add("errorFields", map);
		}
		
	}
	/*
	 * ����jackson��
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
	 * v1 ��ѯԱ������(��ҳ��ѯ)
	   v2�ĳ���json�ַ����ķ�ʽ����(������getEmpsWithJson)
	@RequestMapping("/emps")*/
	public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model) {
		// �ⲻ��һ����ҳ��ѯ������PageHelper��ҳ���
		
		// �ڲ�ѯ֮ǰֻ�����(����ҳ���Ѿ�ÿҳ��С)
		PageHelper.startPage(pn, 5);
		// startPage��������������ѯ����һ����ҳ��ѯ
		List<Employee> emps =  employeeService.getAll();
		// ʹ��PageInfo��װ��ҳ�õ�����, ֻ�轫pageinfo����ҳ�漴��
		// PageInfo�������ڶ���������ʾ������ʾ��ҳ��< 1 2 3 4 5 > < 3 4 5 6 7 >
		PageInfo<Employee>  page = new PageInfo<>(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	} 
}
