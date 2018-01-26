package com.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.finance.enums.UserEnum;
import com.finance.form.api_param.ModifyApiParam;

public class ApiUserActionTest {
	private final static String URL_REGISTE = "http://127.0.0.1:8080/finance/api/v1_registe";
	private final static String URL_OBTAIN = "http://127.0.0.1:8080/finance/api/v1_obtain";
	private final static String URL_MODIFY = "http://127.0.0.1:8080/finance/api/v1_modify";
	private final static String URL_WATER_OBTAIN = "http://127.0.0.1:8080/finance/api/v1_water_obtain";

	private CloseableHttpClient httpClient = null;
	RequestConfig requestConfig = null;

	@Before
	public void setUp() {
		System.out.println("=========== ApiUserActionTest2 setUp .. ===========");
		httpClient = HttpClients.createDefault();
		// 配置超时时间
		requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
	}

	@After
	public void tearDown() {
		System.out.println("=========== ApiUserActionTest2 tearDown .. ===========");
	}

	// @Test
	public void testPostRegiste() {
		System.out.println("post registe : " + URL_REGISTE);
		HttpPost httpPost = new HttpPost(URL_REGISTE);
		httpPost.setConfig(requestConfig);

		JSONObject jsonParam = new JSONObject();
		String timestamp = String.valueOf(System.currentTimeMillis());

		jsonParam.put("system", "junit_test");
		jsonParam.put("channel", "junit");
		jsonParam.put("userid", timestamp);
		jsonParam.put("username", "测试用户:" + (int) (Math.random() * 100));
		jsonParam.put("email", timestamp + "@hotmail.com");
		jsonParam.put("accountInitAmount", 33.3);
		jsonParam.put("accountInitReason", "create user add");
		jsonParam.put("scoreInitAmount", 88.8);
		jsonParam.put("scoreInitReason", "activity add ");

		System.out.println(jsonParam.toJSONString());
		StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");// 解决中文乱码问题
		entity.setContentType("application/json");
		entity.setContentEncoding("UTF-8");
		httpPost.setEntity(entity);

		HttpResponse httpResponse;
		String strResult = null;
		try {
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse != null) {
				int StatusCode = httpResponse.getStatusLine().getStatusCode();
				if (StatusCode == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				} else {
					System.out.println("Error Response: " + httpResponse.getStatusLine().toString());
				}
			} else {
				System.out.println("httpResponse is null?");
			}

			System.out.println("strResult:" + strResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(strResult);
		if (strResult != null) {
			JSONObject object = JSONObject.parseObject(strResult);
			assertEquals("success", object.get("status"));
		}
	}

	// @Test
	public void testPostObtain() throws UnsupportedEncodingException {
		System.out.println("post obtain : " + URL_OBTAIN);
		HttpPost httpPost = new HttpPost(URL_OBTAIN);
		httpPost.setConfig(requestConfig);

		JSONObject jsonParam = new JSONObject();
		jsonParam.put("system", "web_test");
		jsonParam.put("userid", "001");
		System.out.println(jsonParam.toJSONString());
		StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");// 解决中文乱码问题
		entity.setContentType("application/json");
		entity.setContentEncoding("UTF-8");
		httpPost.setEntity(entity);

		HttpResponse httpResponse;
		String strResult = null;
		try {
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse != null) {
				int StatusCode = httpResponse.getStatusLine().getStatusCode();
				if (StatusCode == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				} else {
					System.out.println("Error Response: " + httpResponse.getStatusLine().toString());
				}
			} else {
				System.out.println("httpResponse is null?");
			}

			System.out.println("strResult:" + strResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(strResult);
	}

	// @Test
	public void testPostModify() {
		System.out.println("post modify : " + URL_MODIFY);
		HttpPost httpPost = new HttpPost(URL_MODIFY);
		httpPost.setConfig(requestConfig);

		JSONObject jsonParam = new JSONObject();
		jsonParam.put("system", "web_test");
		jsonParam.put("channel", "junit");
		jsonParam.put("userid", "001");
		jsonParam.put("operator", "system");
		jsonParam.put("datetime", new Date());

		JSONArray oper = new JSONArray();
		for (int i = 0; i < 2; i++) {
			JSONObject oper_v = new JSONObject();
			if (i % 2 == 0) {
				oper_v.put("symbol", "Add");
				oper_v.put("amount", 1500.5);
				oper_v.put("pay_amount", 0);
				oper_v.put("currency", "RMB");
				oper_v.put("send_country", "de");
				oper_v.put("reason", "share awards");
				oper_v.put("watertype", UserEnum.UserWaterType.score);
				oper_v.put("is_pay", "N");
				oper_v.put("order_no", null);
				oper_v.put("order_type", null);
			} else {
				oper_v.put("symbol", "Sub");
				oper_v.put("amount", 33333333.3);
				oper_v.put("pay_amount", 33.3);
				oper_v.put("currency", "RMB");
				oper_v.put("send_country", "de");
				oper_v.put("reason", "share awards");
				oper_v.put("watertype", UserEnum.UserWaterType.score);
				oper_v.put("is_pay", "Y");
				oper_v.put("order_no", "ORDER001");
				oper_v.put("order_type", "parcel");
			}

			oper.add(oper_v);
		}

		jsonParam.put("oper", oper);

		System.out.println(jsonParam.toJSONString());
		StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");// 解决中文乱码问题
		entity.setContentType("application/json");
		entity.setContentEncoding("UTF-8");

		httpPost.setEntity(entity);

		HttpResponse httpResponse;
		String strResult = null;
		try {
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse != null) {
				int StatusCode = httpResponse.getStatusLine().getStatusCode();
				if (StatusCode == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				} else {
					System.out.println("Error Response: " + httpResponse.getStatusLine().toString());
				}
			} else {
				System.out.println("httpResponse is null?");
			}

			System.out.println("strResult:" + strResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(strResult);
		if (strResult != null) {
			JSONObject object = JSONObject.parseObject(strResult);
			assertEquals("success", object.get("status"));
		}
	}

	@Test
	public void testGetWaterObtain() {
		System.out.println("get water_obtain : " + URL_WATER_OBTAIN);
		HttpPost httpPost = new HttpPost(URL_WATER_OBTAIN);
		httpPost.setConfig(requestConfig);

		JSONObject jsonParam = new JSONObject();
		jsonParam.put("system", "web_test");
		jsonParam.put("userid", "001");
		jsonParam.put("water_no", 5);
		System.out.println(jsonParam.toJSONString());
		StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");// 解决中文乱码问题
		entity.setContentType("application/json");
		entity.setContentEncoding("UTF-8");
		httpPost.setEntity(entity);

		HttpResponse httpResponse;
		String strResult = null;
		try {
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse != null) {
				int StatusCode = httpResponse.getStatusLine().getStatusCode();
				if (StatusCode == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				} else {
					System.out.println("Error Response: " + httpResponse.getStatusLine().toString());
				}
			} else {
				System.out.println("httpResponse is null?");
			}

			System.out.println("strResult:" + strResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(strResult);
		if(strResult!=null){
			JSONObject object = JSONObject.parseObject(strResult);
			assertFalse(object.getBoolean("has_next"));
		}
	}
}
