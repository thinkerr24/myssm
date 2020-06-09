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
 *  测试dao层工作
 *  使用spring测试注解来进行测试
 *  1.maven导入spring test依赖
 *  2.ContextConfiguration注解
 *  3.直接Autowired要使用的注解即可
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {
	/*
	 * 测试DepartmentMapper
	 */
	@Autowired
	DepartmentMapper deparMapper;
	@Autowired
	EmployeeMapper employMapper;
	@Autowired
	SqlSession sqlSession;

	@Test
	public void testCRUD() {
		/*
		 * 1.创建SpringIOC容器 ApplicationContext ac = new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); // 2.从容器中取mapper
		 * DepartmentMapper bean = ac.getBean(DepartmentMapper.class);
		 */
		// System.out.println(deparMapper);

		// 1.插入几个部门
		/*
		 * deparMapper.insertSelective(new Department(null, "开发部"));
		 * deparMapper.insertSelective(new Department(null, "测试部"));
		 */

		// 2.生成员工数据，测试员工插入
		// employMapper.insertSelective(new Employee(null, "Tom", "M", "Tom@qq.com",
		// 1));
		// 3.批量插入多个员工

		//EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 100; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5) + i;
			employMapper.insertSelective(new Employee(null, uid, "M", uid + "@126.com", 1));
		}
		System.out.println("批量完成");

	}
}
