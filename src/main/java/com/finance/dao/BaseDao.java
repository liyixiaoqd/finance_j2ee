package com.finance.dao;

import java.util.List;

import com.finance.util.Page;
import com.finance.util.exception.PojoCheckException;

/***
 * 基本查询操作
 * 
 * @author Liyixiao
 *
 */
public interface BaseDao {
	//根据Id获取单条数据
	public Object getById(int id,boolean isLock);
	//根据SQL获取单条数据
	public Object getByBean(String sql,Object bean,boolean isLock);
	
	//获取表所有数据
	public List<?> listObject(boolean isLock);
	//获取表所有数据-分页
	public List<?> listObjectByPage(Page page,boolean isLock);
	//根据SQL获取表数据-分页
	public List<?> listByBean(String sql,Object bean, Page page,boolean isLock);
	//根据 多条件获取数据
	public List<?> listByPairParams(Page page,Object... pairParams);
	

	//获取表总数
	public int getObjectTotal();
	public int getTotalByBean(String sql,Object bean);
	
	//插入表数据
	public void addObject(Object bean) throws PojoCheckException;
	public void updateObject(Object bean) throws PojoCheckException;
}
