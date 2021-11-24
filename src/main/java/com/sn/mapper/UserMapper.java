package com.sn.mapper;

import com.sn.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Mapper 注解，就能够扫描mapper接口，生成接口的代理对象（接口的实现类）
 * 使用到了mubatis框架，引入框架相关的依赖
 */
@Mapper
public interface UserMapper {
    List<User> findAllUsers();
}
