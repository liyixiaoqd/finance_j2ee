package com.finance.timer;


import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.finance.util.DateUtil;

public class TimerTest {
	public void test() {
		System.out.println("quartz定时任务:"+DateUtil.getStrTodayYMDHMS());
	}

	public static void main(String args[]) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}
