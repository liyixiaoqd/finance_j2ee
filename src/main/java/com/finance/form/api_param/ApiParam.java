package com.finance.form.api_param;

import java.lang.reflect.Field;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public abstract class ApiParam {
	protected abstract String checkParam();

	/**
	 * 根据request.getParameterMap 进行匹配
	 * 
	 * @param mp
	 */
	public void paramTransferMap(Map<String, String[]> mp) {
		for (Field f : this.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				String value = mp.get(f.getName())[0];
				if (f.getType().getSimpleName().equals("float")) {
					f.set(this, Float.valueOf(value));
				} else {
					f.set(this, value);
				}
			} catch (Exception e) {
				System.out.println("paramTransferMap failure: " + f.getName() + "," + f.getType());
			}
		}
	}

	public void paramTransferJson(JSONObject json) {
		for (Field f : this.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getType().getSimpleName().equals("float")) {
					f.set(this, json.getFloat(f.getName()));
				} else if (f.getType().getSimpleName().equals("Date")) {
					f.set(this, json.getDate(f.getName()));
				} else {
					f.set(this, json.get(f.getName()));
				}
			} catch (Exception e) {
				System.out.println("paramTransferJson failure: " + f.getName() + "," + f.getType() + ","
						+ f.getType().getSimpleName());
			}
		}
	}

	protected String checkParam(String[] fieldnames) {
		StringBuilder checkMsg = new StringBuilder();

		for (String fieldName : fieldnames) {
			System.out.println("check "+fieldName);
			try {
				Field f = this.getClass().getDeclaredField(fieldName);
				f.setAccessible(true);
				Object value = f.get(this);
				if (value == null)
					checkMsg.append(fieldName + " is null\n");
				else if (f.getType().getSimpleName().equals("float")) {
					if ((Float) value < -0.001f)
						checkMsg.append(fieldName + " less zero\n");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				checkMsg.append(fieldName + " check failure\n");
				e.printStackTrace();
				break;
			}
		}

		System.out.println("===checkMsg===\n"+checkMsg.toString() +"===checkMsg===");
		return checkMsg.toString();
	}
}
