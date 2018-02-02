package com.finance.pojo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dao.ExchangeRateDao;
import com.finance.enums.CurrencyEnum;
import com.finance.enums.UserEnum;
import com.finance.enums.UserEnum.UserWaterType;
import com.finance.form.UserSearchForm;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserAccountService;
import com.finance.service.UserFinanceWaterService;
import com.finance.service.UserService;
import com.finance.timer.TimerSyncFile;
import com.finance.util.AssembleString;
import com.finance.util.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PojoTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	UserFinanceWaterService ufwService;

	@Autowired
	private ExchangeRateDao erDao;

	//
	// @Test
	// public void init() {
	// System.out.println("init start");
	// if (userService.getTotal() <10) {
	// for (int i = 0; i < 30; i++) {
	// User u = new User();
	// u.setSystem("test");
	// u.setEmail("test"+i+"@hotmail.com");
	// u.setUserid(System.currentTimeMillis()+""+i);
	// u.setUsername("测试账号"+i);
	// userService.add(u);
	// System.out.println("插入user表:"+i);
	// }
	// }
	// System.out.println("init end");
	// }
	//
	// @Test
	// public void listPage(){
	// System.out.println("listPage start");
	// Page p = new Page(10,20);
	// List<User> userList = userService.listUserPage(p);
	// System.out.println(userList.size());
	// System.out.println("listPage end");
	// }

	// @Test
	// public void listBean(){
	// System.out.println("listBean start");
	// Page p = new Page(0,100);
	// UserSearchForm userSearchForm = new UserSearchForm();
	// userSearchForm.setSystem("test");
	// userSearchForm.setType(UserWaterType.eCash);
	// userSearchForm.setBeg_amount(5f);
	// List<User> users = userService.listByForm(userSearchForm, p);
	// System.out.println(users.size());
	// System.out.println("listBean end");
	// }
	//
	// @Test
	// public void fill(){
	// User u = userService.getUser(116);
	// userAccountService.fillUser(u);
	// System.out.println(u.getScore());
	// }

	// @Test
	// public void t5(){
	// final User u = userService.getUser(116);
	// final int thread_num = 10;
	// final Random random = new Random();
	//
	// Thread arr[] = new Thread[thread_num];
	// for(int i=0;i<thread_num;i++){
	// Thread t = new Thread(){
	// @Override
	// public void run(){
	// System.out.println(this.getName()+" start");
	// try {
	// Thread.sleep(random.nextInt(10)*500);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// UserAccount ua = userAccountService.getByUserAndType(u,
	// UserEnum.UserWaterType.score);
	// userAccountService.getAndUpdateTest(ua);
	//
	// System.out.println(this.getName()+" end");
	// }
	// };
	// t.setName("Thread-"+i);
	// arr[i]=t;
	// t.start();
	// }
	// for(Thread t:arr){
	// try {
	// t.join();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// UserAccount ua = userAccountService.getByUserAndType(u,
	// UserEnum.UserWaterType.score);
	// System.out.println("FINALLY get
	// UserAccount:"+ua.getType().name()+","+ua.getValue());
	// }

	// @Test
	// public void t6(){
	// UserAccount ua =
	// userAccountService.getByUserAndType(userService.getUser(116),
	// UserEnum.UserWaterType.score,false);
	// userAccountService.updateUserAccount(ua);
	// }

	// @Test
	public void t7() {
		User u = userService.getUser(142);
		List<UserFinanceWater> ufws = ufwService.listByUser(u, null);

		StringBuilder excelBuf = new StringBuilder();
		AssembleString.execlPojoAssemble(excelBuf, UserFinanceWater.class, ufws);
		System.out.println(excelBuf.toString());
	}

	@Test
	public void t8() {
		CurrencyEnum currency = CurrencyEnum.EUR;
		String strDate = "2018-01-30";

		List<ExchangeRate> ers = (List<ExchangeRate>) erDao.listByPairParams(null, "currency", currency.name(),
				"rate_datetime", strDate);
		System.out.println("---1--- : " + (ers.isEmpty() || !ers.get(0).isSucc()));
	}
}
