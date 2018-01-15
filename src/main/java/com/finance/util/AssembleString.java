package com.finance.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class AssembleString {
	/***
	 * 
	 * @param sb
	 *            - 组装的总SQL
	 * @param con
	 *            - 条件,空则不进行处理
	 * @param sql
	 *            - 本次需要拼接的SQL
	 * @return
	 */
	public static StringBuilder conSqlAssemble(StringBuilder sb, String con, String sql) {
		if (StringUtils.isEmpty(con))
			return sb;

		if (sb.indexOf("where") == -1)
			sb.append(" where ");
		else
			sb.append(" and ");
		sb.append(sql + " ");

		return sb;
	}

	public static StringBuilder conSqlAssemble(StringBuilder sb, String sql) {
		return conSqlAssemble(sb, "t", sql);
	}

	public static StringBuilder conPageAssemble(StringBuilder sb, String con, String str) {
		if (StringUtils.isEmpty(con))
			return sb;

		if (sb.length() != 0)
			sb.append("&");

		sb.append(str + con);

		return sb;
	}

	/**
	 * 根据类设置标题与内容
	 * 
	 * @param sb
	 * @param clzss
	 * @param os
	 * @return
	 */
	public static StringBuilder execlPojoAssemble(StringBuilder sb, Class<?> clzss, List<?> os) {
		// 通过反射设置标题
		Field[] fields = clzss.getDeclaredFields();
		Method methods[] = new Method[fields.length];
		for (int i=0;i<fields.length;i++) {
			Field f = fields[i];
			if (i > 0)
				sb.append("\t");
			sb.append(f.getName());
			
			String methodName = "get" + StringUtils.capitalize(f.getName());
			try{
				Method method = clzss.getMethod(methodName);
				methods[i]=method;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("\n");

		for (Object o : os) {
			for (int i=0;i<methods.length;i++) {
				try{
					Object value = methods[i].invoke(o);
					if (i > 0)
						sb.append("\t");
					if(value==null)
						sb.append("");
					else
						sb.append(value.toString());
				} catch (Exception e) {
					System.out.println(fields[i]+" out failure");
				}
			}
			sb.append("\n");
		}

		return sb;
	}
}
