package com.rr.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rr.crud.bean.Department;
import com.rr.crud.bean.Employee;
import com.rr.crud.dao.DepartmentMapper;
import com.rr.crud.dao.EmployeeMapper;



/*
 *  ����dao�㹤��
 *  ʹ��spring����ע�������в���
 *  1.maven����spring test����
 *  2.ContextConfigurationע��
 *  3.ֱ��AutowiredҪʹ�õ�ע�⼴��
 */
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {
	/*
	 *  ����DepartmentMapper
	 */
	@Autowired
	DepartmentMapper deparMapper;
	@Autowired 	
	EmployeeMapper employMapper;
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testCRUD() {
		/* 1.����SpringIOC����
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 2.��������ȡmapper
		DepartmentMapper bean = ac.getBean(DepartmentMapper.class);*/
		System.out.println(deparMapper);
		
		// 1.���뼸������
		/*deparMapper.insertSelective(new Department(null, "������"));
		deparMapper.insertSelective(new Department(null, "���Բ�"));*/
		
		// 2.����Ա�����ݣ�����Ա������
		//employMapper.insertSelective(new Employee(null, "Tom", "M", "Tom@qq.com", 1));
		// 3.����������Ա��
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i = 0;i<100;i++){
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null,uid, "M", uid+"@126.com", 1));
		}
		System.out.println("�������");
	}
}
