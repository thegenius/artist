package com.lvonce.artist.example.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.lvonce.artist.annotation.SqlDataSource;
import com.lvonce.artist.example.dal.entity.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@SqlDataSource(name="h2-mem")
//@SqlDataSource(value="prod", name="mysql")
public interface PersonMapper extends BaseMapper<Person> {

     @Select("select * from person where name = #{name}")
     Person selectByName(String name);

     @Select("select * from person where age = #{age}")
     List<Person> selectByAge(int age);

     Person getStudentById(int id);

     Person getStudentByName(String name);
}
