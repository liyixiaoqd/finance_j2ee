package com.finance.timer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserFinanceWaterService;
import com.finance.util.DateUtil;

/***
 * quartz 同步各种文件方法
 * 
 * @author Liyixiao Component 为了junit测试
 */

@Component
public class TimerSyncFile {
	@Autowired
	UserFinanceWaterService ufwService;

	public static final String FILESPLIT = "|&|";
	public static final String dirPath;
	static {
		dirPath = System.getProperty("user.dir") + File.separator + "file" + File.separator + "sync" + File.separator
				+ "user_finance";
	}

	public void genUserFinanceFile() {
		System.out.println("======= genUserFinanceFile start " + new Date() + " =======");

		File f = new File(dirPath, "user_finance_" + DateUtil.getStrTodayFormat("yyyyMMdd") + ".txt");
		if (f.exists())
			f.delete();
		else
			f.getParentFile().mkdirs();

		System.out.println("file: " + f.getAbsolutePath());

		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(f);
			pw = new PrintWriter(fw);
			List<UserFinanceWater> ufws = ufwService.listByDate(DateUtil.calcDateByToday(-8));
			System.out.println(ufws.isEmpty());

			StringBuilder sb = new StringBuilder();
			sb.append("用户标识,流水类型,操作符,原金额,金额,现金额,原因描述,操作日期,财务系统ID");
			pw.println(sb.toString().replaceAll(",", FILESPLIT));

			for (UserFinanceWater ufw : ufws) {
				sb.setLength(0);
				sb.append(ufw.getUser().getUserid());
				sb.append(FILESPLIT);
				sb.append(ufw.getTypeDesc());
				sb.append(FILESPLIT);
				sb.append(ufw.getSymbol());
				sb.append(FILESPLIT);
				sb.append(ufw.getSymbol());
				sb.append(FILESPLIT);
				sb.append(ufw.getOld_amount());
				sb.append(FILESPLIT);
				sb.append(ufw.getAmount());
				sb.append(FILESPLIT);
				sb.append(ufw.getNew_amount());
				sb.append(FILESPLIT);
				sb.append(ufw.getReason());
				sb.append(FILESPLIT);
				sb.append(ufw.getOperdate());
				sb.append(FILESPLIT);
				sb.append(ufw.getId());
				pw.println(sb.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("genUserFinanceFile fail: "+e.getMessage());
		} finally {
			if (pw != null)
				pw.close();
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		System.out.println("======= genUserFinanceFile end " + new Date() + " =======");
	}

}
