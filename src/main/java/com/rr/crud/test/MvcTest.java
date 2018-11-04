package com.rr.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.rr.crud.bean.Employee;


/*
 * ʹ��spring����ģ���ṩ�Ĳ��������ܣ�����crud�������ȷ��
 * Spring4���Ե�ʱ����Ҫservlet3.0֧��
 */
@ContextConfiguration(locations= {"classpath:applicationContext.xml", "classpath:springmvc.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class MvcTest {
	// ����mvc���󣬻�ȡ��������
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPage() throws Exception {
		// ģ�������õ�����ֵ
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "4")).andReturn();
		
		// ����ɹ���, �������л���pageInfo, ���ǿ���ȡ��pageInfo������֤
		MockHttpServletRequest request = result.getRequest();
		PageInfo<Employee> pi = (PageInfo<Employee>) request.getAttribute("pageInfo");
		System.out.println("��ǰҳ��:" + pi.getPageNum());
		System.out.println("��ҳ��:" + pi.getPages());
		System.out.println("�ܼ�¼��:" + pi.getTotal());
		System.out.println("��ҳ����Ҫ������ʾ��ҳ��");
		int[] nums = pi.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(i + " ");
		}
		
		// ��ȡԱ������
		List<Employee> lists = pi.getList();
		for (Employee employee : lists) {
			System.out.println(employee.getEmpId() + "----" + employee.getEmpName());
		}
	}
}
