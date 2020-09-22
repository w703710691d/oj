package com.swustacm.oj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.oj.entity.ExperienceScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lixiaobo
 * @since 2020-09-17
 */
@Repository
public interface ScoreMapper extends BaseMapper<ExperienceScore> {

    List<ExperienceScore> getScore(Integer num_1, Integer num_2, Integer num_3, Integer num_4, Integer num_5, Integer num_6, Integer num_7, Integer num_8);

    List<Map<String, Object>> getScoreByTime(@Param("sql") String sql);
}
