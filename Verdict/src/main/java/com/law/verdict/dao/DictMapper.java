package com.law.verdict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.law.verdict.model.Dict;
import com.law.verdict.model.DictExample;

public interface DictMapper {
    int countByExample(DictExample example);

    int deleteByExample(DictExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    List<Dict> selectByExample(DictExample example);

    Dict selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByExample(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
    
	@Select("select * from dict where id >0 order by id limit #{start}, #{offset}")
	public List<Dict> selectAll(@Param("start") int start, @Param("offset") int offset);
	
	@Select("insert into dict(word,label,frequency,isOpen) value(#{word}, #{label},#{frequency}, #{isOpen})")
	public List<Dict> myInsert(@Param("word") String word, @Param("label") String label,@Param("frequency") int frequency, @Param("isOpen") int isOpen);
	
	@Select("delete from dict where id=#{id}")
	public void myDelete(@Param("id") int id);
}