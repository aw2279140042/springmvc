package com.springmvc.common;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseDao<T> extends SqlSessionDaoSupport {
	/**mybatis-spring-1.2.0中取消了自动注入SqlSessionFactory 和 SqlSessionTemplate*/
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * 获取传递过来的泛型类名称
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getClassName() {
		// getGenericSuperclass()获得带有泛型的父类
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// getActualTypeArguments获取参数化类型的数组，泛型可能有多个
		Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
		return clazz.getSimpleName().toString().toLowerCase();
	}

	public void insert(T t) {
		getSqlSession().insert(this.getClassName() + ".insert", t);
	}

	public void update(T t) {
		getSqlSession().update(getClassName() + ".update", t);
	}

	public void delete(Object id) {
		getSqlSession().delete(this.getClassName() + ".deleteById", id);
	}

	@SuppressWarnings("unchecked")
	public T getById(Object id) {
		return (T) getSqlSession().selectOne(this.getClassName() + ".getById", id);
	}

}
