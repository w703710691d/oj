package com.swustacm.poweroj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.swustacm.poweroj.biz.ScoreBiz;
import com.swustacm.poweroj.common.util.DateConvert;
import com.swustacm.poweroj.common.util.ListUtils;
import com.swustacm.poweroj.entity.Contest;
import com.swustacm.poweroj.entity.ExperienceScore;
import com.swustacm.poweroj.mapper.ContestMapper;
import com.swustacm.poweroj.mapper.ScoreMapper;
import com.swustacm.poweroj.service.ContestService;
import com.swustacm.poweroj.service.ScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lixiaobo
 * @since 2020-09-17
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, ExperienceScore> implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    ContestMapper contestMapper;

    @Override
    public List<ExperienceScore> getScore(Integer num_1, Integer num_2, Integer num_3, Integer num_4, Integer num_5, Integer num_6, Integer num_7, Integer num_8) {
        return scoreMapper.getScore(num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8);
    }

}
