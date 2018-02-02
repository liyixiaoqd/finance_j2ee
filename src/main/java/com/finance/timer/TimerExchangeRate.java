package com.finance.timer;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.finance.dao.ExchangeRateDao;
import com.finance.enums.CurrencyEnum;
import com.finance.pojo.ExchangeRate;
import com.finance.util.DateUtil;
import com.finance.util.HttpUtil;
import com.finance.util.exception.PojoCheckException;

/***
 * quartz 获取汇率定时任务类
 * @author Liyixiao
 *
 */
public class TimerExchangeRate {
	private final static String WEB_URL = "http://srh.bankofchina.com/search/whpj/search.jsp";
	private final static Map<CurrencyEnum, String> CURRENCY_WEB_MAPPING;
	static {
		CURRENCY_WEB_MAPPING = new HashMap<CurrencyEnum, String>();
		CURRENCY_WEB_MAPPING.put(CurrencyEnum.GBP, "1314");
		CURRENCY_WEB_MAPPING.put(CurrencyEnum.EUR, "1326");
		CURRENCY_WEB_MAPPING.put(CurrencyEnum.HKD, "1315");
		CURRENCY_WEB_MAPPING.put(CurrencyEnum.USD, "1316");
	}
	
	@Autowired
	private ExchangeRateDao erDao;
	
	/**
	 * 获取所有货币当天汇率
	 */
	public void getExchangeRate() {
		getExchangeRate(DateUtil.getStrTodayYMD());
	}

	/**
	 * 获取所有货币指定日期汇率
	 * 
	 * @param strDate -- yyyy-MM-dd
	 */
	public void getExchangeRate(String strDate) {
		for (CurrencyEnum currency : CurrencyEnum.values()) {
			System.out.println("---------- "+currency.name()+ " start "+ (new Date()) + " ----------");
			
			if (CURRENCY_WEB_MAPPING.containsKey(currency)){
				List<ExchangeRate> ers = (List<ExchangeRate>) erDao.listByPairParams(null, "currency", currency.name(),
						"rate_datetime", strDate);
				if(ers.isEmpty() || !ers.get(0).isSucc())
					getExchangeRate(strDate, currency);
				else
					System.out.println(currency.name() +" has get rate : "+ers.get(0).getRate());
			}
			else
				System.out.println("no mapping get currency :" + currency.name()+","+CURRENCY_WEB_MAPPING.containsKey(currency));
			

			System.out.println("---------- "+currency.name()+ " end "+ (new Date()) + " ----------");
		}
	}

	/**
	 * 获取 货币 指定日期汇率
	 * 
	 * @param strDate -- yyyy-MM-dd
	 * @param currency
	 */
	public void getExchangeRate(String strDate, CurrencyEnum currency) {
		JSONObject params = new JSONObject();
		params.put("erectDate", strDate);
		params.put("pjname", CURRENCY_WEB_MAPPING.get(currency));
		
		try {
			String web_body = HttpUtil.getUrlBodyByPost(WEB_URL, HttpUtil.TYPE_FORM, params);
			System.out.println("web_body get:"+web_body.length());
			String table = HttpUtil.getContent(web_body, "(BOC_main publish.*?</table>)");
			
			int lines = HttpUtil.getTableLineCount(table);
			System.out.println("table lines:"+lines);
			//最后一行为纯样式不处理,第一行为标题不处理
			for (int i = lines-2; i >0; i--) {
				// 现汇卖出价,发布时间
				String web_value = HttpUtil.getContextInTable(table, i, 3);
				String web_time = HttpUtil.getContextInTable(table, i, 7);
				
				//只获取当天9点之后的第一条
				if(web_time==null || web_time.length()<14 || web_time.substring(11,13).compareTo("09")<0){
					System.out.println("continue-1 " + i+" : "+web_value + " -- " + web_time);
					continue;
				}
				else if(!web_time.substring(0,10).replace(".", "-").equals(strDate)){
					System.out.println("TABLE CONTENT["+table+"]");
					System.out.println("continue-2 " + i+" : "+web_value + " -- " + web_time);
					continue;					
				}
				
				System.out.println("proc " + i+" : "+web_value + " -- " + web_time);
				float rate = Float.valueOf(web_value);
				//保留4位小数
				rate = (float)Math.round((rate / 100 * 1.015)*10000)/10000;
				
				ExchangeRate er = new ExchangeRate();
				er.setCurrency(currency);
				er.setFlag(ExchangeRate.SUCC);
				
				//与上一期利率相比 是否误差在5%以上
				List<ExchangeRate> ers = (List<ExchangeRate>) erDao.listByPairParams(null, "currency", currency.name(),
						"rate_datetime", DateUtil.calcDate(strDate, "yyyy-MM-dd", -1));
				if(ers.isEmpty() || !ers.get(0).isSucc())
					er.setFlag(ExchangeRate.SUCC_WARN);
				else{
					if(ers.get(0).getRate()*0.95 > rate || ers.get(0).getRate()*1.05 < rate)
						er.setFlag(ExchangeRate.SUCC_WARN);
				}
				er.setRate(rate);
				er.setRate_datetime(DateUtil.strToDate(web_time,"yyyy.MM.dd"));
				er.setCreated_at(DateUtil.strToDate(web_time, "yyyy.MM.dd HH:mm:ss"));
				
				try {
					erDao.addObject(er);
				} catch (PojoCheckException e) {
					// TODO Auto-generated catch block
					System.out.println("save ExchangeRate failure:"+e.getCause());
				}
				
				break;
			}
		} catch (IOException e) {
			System.out.println("getExchangeRate failure :" + currency.name() + " in " + strDate);
		}
	}


	public static void main(String args[]) {
		String t = "2018.02.01 09:18:53";
		System.out.println(t.substring(0, 10).substring(0,10).equals("2018.02.01"));
	}
}
