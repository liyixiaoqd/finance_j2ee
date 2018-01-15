package com.finance.dao.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.finance.dao.BaseDao;
import com.finance.util.Page;

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

	@Override
	public void addObject(Object bean) {
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
		getCurrentSession().save(bean);
	}

	@Override
	public void updateObject(Object bean) {
		// TODO Auto-generated method stub
		try {
			Method getMethod = bean.getClass().getMethod("getUpdated_at");
			Date updated_at = (Date) getMethod.invoke(bean);
			if (updated_at == null) {
				Method setMethod = bean.getClass().getMethod("setUpdated_at", Date.class);
				setMethod.invoke(bean, new Date());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getCurrentSession().update(bean);
	}

}
