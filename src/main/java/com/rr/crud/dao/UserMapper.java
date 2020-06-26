package com.rr.crud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.rr.crud.bean.User;

@Mapper
public interface UserMapper {
	/*
    * 对于多个参数来说，每个参数之前都要加上@Param注解，
    * 要不然会找不到对应的参数进而报错
    */
		User selectUserByNameAndPassword(@Param("username")String username, @Param("password")String password);
}
