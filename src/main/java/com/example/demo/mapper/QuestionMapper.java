package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_Create,gmt_Modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> List(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=${userId} limit #{offset},#{size}")
    List<Question> ListById(@Param(value = "userId")Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question where creator=${userId}")
    Integer countById(Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);
}
