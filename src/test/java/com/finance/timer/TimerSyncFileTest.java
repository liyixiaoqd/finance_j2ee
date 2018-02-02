package com.finance.timer;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TimerSyncFileTest {
	@Autowired
	TimerSyncFile tsf;
	
	@Test
	public void testUserFinanceFile(){
		tsf.genUserFinanceFile();
	}
}
