package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
//ctrol+alt+o删除

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_Id,token,gmt_Create,gmt_Modified,avatar_Url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);

    @Select("select * from user where id=#{creator}")
    User finById(Integer creator);
}
