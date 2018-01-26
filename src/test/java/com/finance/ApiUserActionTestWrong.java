package com.finance;

import org.apache.struts2.StrutsSpringJUnit4TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.finance.action.api.ApiUserAction;
import com.opensymphony.xwork2.ActionProxy;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/***
 * 程序异常
 * @author Liyixiao
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ApiUserActionTestWrong extends StrutsSpringJUnit4TestCase<ApiUserAction> {
	// private TestService testService;

	@Before
	public void setUp() throws Exception {
		super.setUp();// 必须要有，初始化用，参见UML图
		// 模拟request,response
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();

		// 创建mock对象
		// testService = EasyMock.createMock(TestService.class);
	}

	@Test
	public void testObtain() throws Exception {
		ActionProxy proxy = null;
		ApiUserAction apiUserAction = null;
		
		request.setParameter("system", "test");
		request.setParameter("userid", "001");
		proxy = getActionProxy("/api/v1_obtain");
		apiUserAction =(ApiUserAction)proxy.getAction();
		apiUserAction.obtain();
	}
}
