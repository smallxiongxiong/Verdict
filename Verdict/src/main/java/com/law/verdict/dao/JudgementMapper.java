package com.law.verdict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.law.verdict.model.Judgement;
import com.law.verdict.model.JudgementExample;
import com.law.verdict.model.JudgementWithBLOBs;

public interface JudgementMapper {
    int countByExample(JudgementExample example);

    int deleteByExample(JudgementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(JudgementWithBLOBs record);

    int insertSelective(JudgementWithBLOBs record);

    List<JudgementWithBLOBs> selectByExampleWithBLOBs(JudgementExample example);

    List<Judgement> selectByExample(JudgementExample example);

    JudgementWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JudgementWithBLOBs record, @Param("example") JudgementExample example);

    int updateByExampleWithBLOBs(@Param("record") JudgementWithBLOBs record, @Param("example") JudgementExample example);

    int updateByExample(@Param("record") Judgement record, @Param("example") JudgementExample example);

    int updateByPrimaryKeySelective(JudgementWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(JudgementWithBLOBs record);

    int updateByPrimaryKey(Judgement record);
    
    @Select("select * from judgement where id >1 order by id limit #{start}, #{offset}")
    public List<JudgementWithBLOBs> selectJudgement(@Param("start") int start , @Param("offset") int offset);
    
    @Select("select * from judgement where doc_id = #{docId}")
    public List<JudgementWithBLOBs> selectJudgementByDocId(@Param("docId") String docId);
}