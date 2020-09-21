package com.swustacm.oj.service;


import com.swustacm.oj.entity.ExperienceScore;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lixiaobo
 * @since 2020-09-17
 */
public interface ScoreService extends IService<ExperienceScore> {

    List<ExperienceScore> getScore(Integer num_1, Integer num_2, Integer num_3, Integer num_4, Integer num_5, Integer num_6, Integer num_7, Integer num_8);
}
