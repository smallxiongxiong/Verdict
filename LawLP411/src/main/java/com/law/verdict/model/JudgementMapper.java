package com.law.verdict.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JudgementMapper {
    long countByExample(JudgementExample example);

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
}