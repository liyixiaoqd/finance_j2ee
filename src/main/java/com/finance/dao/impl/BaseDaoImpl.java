package com.finance.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dao.BaseDao;
import com.finance.util.Page;
import com.finance.util.exception.PojoCheckException;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class BaseDaoImpl implements BaseDao {
	@Autowired
	protected SessionFactory sf;

	private Class<?> pojoClass;
	private final String POJO_PACKAGE = "com.finance.pojo.";

	public Session getCurrentSession() {
		return sf.getCurrentSession();
	}

	public BaseDaoImpl() {
		String class_name = this.getClass().getSimpleName();
		if (class_name == "BaseDaoImpl") {
			System.out.println("base无对应Pojo");
		} else {
			try {
				pojoClass = Class.forName(POJO_PACKAGE + StringUtils.substringBefore(class_name, "DaoImpl"));
				System.out.println("对应Pojo:" + pojoClass.getSimpleName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("对应Pojo出错: " + class_name);
			}
		}
	}

	@Override
	public Object getById(int id, boolean isLock) {
		// TODO Auto-generated method stub
		if (isLock)
			return getCurrentSession().get(pojoClass, id, LockOptions.UPGRADE);
		else
			return getCurrentSession().get(pojoClass, id);
	}

	@Override
	public Object getByBean(String sql, Object bean, boolean isLock) {
		// TODO Auto-generated method stub
		List o = listByBean(sql, bean, null, isLock);
		if (o.isEmpty())
			return null;
		return o.get(0);
	}

	@Override
	public List<?> listObject(boolean isLock) {
		// TODO Auto-generated method stub
		return listByBean("from " + pojoClass.getSimpleName() + " order by id desc", null, null, isLock);
	}

	@Override
	public List<?> listObjectByPage(Page page, boolean isLock) {
		// TODO Auto-generated method stub
		return listByBean("from " + pojoClass.getSimpleName() + " order by id desc", null, page, isLock);
	}

	// 查询主逻辑
	@Override
	public List<?> listByBean(String sql, Object bean, Page page, boolean isLock) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(sql);
		if (bean != null) {
			query.setProperties(bean);
			System.out.println("listByBean 注入bean");
		}

		if (page != null) {
			query.setFirstResult(page.getStart());
			query.setMaxResults(page.getCount());
			System.out.println("listByBean 注入page");
		}

		// 悲观锁
		if (isLock)
			query.setLockOptions(LockOptions.UPGRADE);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getObjectTotal() {
		// TODO Auto-generated method stub
		List<Long> totalL = getCurrentSession().createQuery("select count(*) from " + pojoClass.getSimpleName()).list();
		if (totalL.isEmpty())
			return 0;

		return totalL.get(0).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotalByBean(String sql, Object bean) {
		// TODO Auto-generated method stub
		List<Long> l = (List<Long>) listByBean(sql, bean, null, false);
		if (l.isEmpty())
			return 0;
		return l.get(0).intValue();
	}

	@Transactional(readOnly = false)
	@Override
	public void addObject(Object bean) throws PojoCheckException {
		// TODO Auto-generated method stub
		try {
			Method getMethod = bean.getClass().getMethod("getCreated_at");
			Date created_at = (Date) getMethod.invoke(bean);
			if (created_at == null) {
				Method setMethod = bean.getClass().getMethod("setCreated_at", Date.class);
				setMethod.invoke(bean, new Date());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Method checkMethod = bean.getClass().getMethod("check");
			System.out.println(checkMethod.getName() + ";" + checkMethod.getReturnType());
			checkMethod.invoke(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (e instanceof InvocationTargetException)
				throw new PojoCheckException(e.getCause().getMessage());

			System.out.println("check method call failure:" + e.getMessage());
			e.printStackTrace();
		}

		getCurrentSession().save(bean);
	}

	@Override
	public void updateObject(Object bean) throws PojoCheckException {
		// TODO Auto-generated method stub
		try {
			Method getMethod = bean.getClass().getMethod("getUpdated_at");
			Date updated_at = (Date) getMethod.invoke(bean);
			if (updated_at == null) {
				Method setMethod = bean.getClass().getMethod("setUpdated_at", Date.class);
				setMethod.invoke(bean, new Date());
			}
		} catch (Exception e) {
			;
		}

		try {
			Method checkMethod = bean.getClass().getMethod("check");
			checkMethod.invoke(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (e instanceof InvocationTargetException)
				throw new PojoCheckException(e.getCause().getMessage());
		}

		getCurrentSession().update(bean);
	}

	public List<?> listByPairParams(Page page,Object... pairParams){
		String sql = getListStr(pairParams);
		
		return listByBean(sql, null, page, false);
	
	}
	
	private String getListStr(Object... pairParams) {
		// 组装传入的各种参数
		HashMap<String, Object> m = new HashMap<String, Object>();
		for (int i = 0; i < pairParams.length; i = i + 2)
			m.put(pairParams[i].toString(), pairParams[i + 1]);

		StringBuilder sql = new StringBuilder("from " + pojoClass.getSimpleName());
		int index = 0;
		for (String key : m.keySet()) {
			if (index == 0)
				sql.append(" where ");
			else
				sql.append("and ");

			if (m.get(key) == null)
				sql.append(key + " is null ");
			else {
				sql.append(key + " = '" + m.get(key) + "' ");
			}
			index++;
		}
		sql.append(" order by id desc");

		System.out.println("getListStr run sql:" + sql.toString());

		return sql.toString();
	}
}
