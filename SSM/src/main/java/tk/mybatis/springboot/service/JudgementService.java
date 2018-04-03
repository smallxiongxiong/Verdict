package tk.mybatis.springboot.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;
import tk.mybatis.springboot.mapper.CountryMapper;
import tk.mybatis.springboot.mapper.JudgementMapper;
import tk.mybatis.springboot.model.Country;
import tk.mybatis.springboot.model.Judgement;

import java.util.List;

@Service
public class JudgementService {

    @Autowired
    private JudgementMapper judgementMapper;

    public List<Judgement> getAll(Judgement judgement) {
        if (judgement.getPage() != null && judgement.getRows() != null) {
            PageHelper.startPage(judgement.getPage(), judgement.getRows());
        }
        Example example = new Example(Judgement.class);
        Example.Criteria criteria = example.createCriteria();
        if (judgement.getHead() != null && judgement.getHead().length() > 0) {
            criteria.andLike("head", "%" + judgement.getHead() + "%");
        }
        if (judgement.getHead2() != null && judgement.getHead2().length() > 0) {
            criteria.andLike("head2", "%" + judgement.getHead2() + "%");
        }
        return judgementMapper.selectByExample(example);
    }

    public List<Judgement> getAllByWeekend(Judgement judgement) {
        if (judgement.getPage() != null && judgement.getRows() != null) {
            PageHelper.startPage(judgement.getPage(), judgement.getRows());
        }
        Weekend<Judgement> weekend = Weekend.of(Judgement.class);
        WeekendCriteria<Judgement, Object> criteria = weekend.weekendCriteria();
        if (judgement.getHead() != null && judgement.getHead().length() > 0) {
            criteria.andLike(Judgement::getHead, "%" + judgement.getHead() + "%");
        }
        if (judgement.getHead2() != null && judgement.getHead2().length() > 0) {
            criteria.andLike(Judgement::getHead2, "%" + judgement.getHead2() + "%");
        }
        if (judgement.getDocId() != null && judgement.getDocId().length() > 0) {
            criteria.andLike(Judgement::getDocId, "%" + judgement.getDocId() + "%");
        }
        return judgementMapper.selectByExample(weekend);
    }

    public Judgement getById(Integer id) {
        return judgementMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
    	judgementMapper.deleteByPrimaryKey(id);
    }

    public void save(Judgement judgement) {
        if (judgement.getId() != null) {
        	judgementMapper.updateByPrimaryKey(judgement);
        } else {
        	judgementMapper.insert(judgement);
        }
    }
}
