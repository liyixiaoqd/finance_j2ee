package com.finance.form.api_param;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class ApiParam {
	protected abstract String checkParam();

	public void paramTransfer(Map<String, String[]> mp) {
		for (Field f : this.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				String value = mp.get(f.getName())[0];
				if (f.getType().getSimpleName().equals("float")) {
					f.set(this, Float.valueOf(value));
				} else
					f.set(this, value);
			} catch (Exception e) {
				System.out.println("paramTransfer failure: " + f.getName() + "," + f.getType());
			}
		}
	}

	protected String checkParam(String[] fieldnames) {
		StringBuilder checkMsg = new StringBuilder();

		for (String fieldName : fieldnames) {
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

		return checkMsg.toString();
	}
}
