package com.swustacm.oj.service.impl;


import com.swustacm.oj.entity.ExperienceScore;
import com.swustacm.oj.mapper.ScoreMapper;
import com.swustacm.oj.service.ScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lixiaobo
 * @since 2020-09-17
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, ExperienceScore> implements ScoreService {

    @Autowired
    ScoreMapper userMapper;

    @Override
    public List<ExperienceScore> getScore(Integer num_1, Integer num_2, Integer num_3, Integer num_4, Integer num_5, Integer num_6, Integer num_7, Integer num_8) {
        return userMapper.getScore(num_1,num_2,num_3,num_4,num_5,num_6,num_7,num_8);
    }
}
