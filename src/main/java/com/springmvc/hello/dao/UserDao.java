package com.springmvc.hello.dao;

import org.springframework.stereotype.Repository;

import com.springmvc.common.BaseDao;
import com.springmvc.entity.TUser;
@Repository("userDao")
public class UserDao extends BaseDao<TUser> {

}
