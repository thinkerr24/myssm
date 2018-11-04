package com.rr.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.rr.crud.bean.Employee;
import com.rr.crud.bean.EmployeeExample;
import com.rr.crud.bean.EmployeeExample.Criteria;
import com.rr.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;
	
	/*
	 *  ��ѯ����Ա��
	 */
	public List<Employee> getAll() {

		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
		
	}

	/*
	 * �����û����Ƿ����
	 */
	public boolean checkUser(String empName) {
		EmployeeExample ex = new EmployeeExample();
		Criteria criteria = ex.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(ex);
		return count == 0;
	}

	/*
	 * ��Ա��Id��ѯԱ��
	 */
	public Employee getEmp(Integer id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

	/*
	 * Ա������
	 */
	public void updateEmp(Employee employee) {
		
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/*
	 * Ա��ɾ��
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example = new EmployeeExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
		
	}

}
