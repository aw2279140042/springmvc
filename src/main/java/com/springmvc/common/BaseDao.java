package com.springmvc.common;

import java.lang.reflect.ParameterizedType;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseDao<T> extends SqlSessionDaoSupport {
	/**
	 * 获取传递过来的泛型类名称
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getClassName(){
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
		return clazz.getSimpleName().toString().toLowerCase();
	}
	
	public void add(T t){
		getSqlSession().insert(this.getClassName()+".add", t);
	}
	
	public void delete(Object id){
		getSqlSession().delete(this.getClassName()+".deleteById", id);
	}
	
	@SuppressWarnings("unchecked")
	public T getById(Object id){
		return (T) getSqlSession().selectOne(this.getClassName()+".getById", id);
	}
	
}
