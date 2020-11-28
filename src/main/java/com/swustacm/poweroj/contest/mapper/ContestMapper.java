package com.swustacm.poweroj.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.poweroj.contest.Contest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xingzi
 * @since 2020-09-22
 */
@Repository
public interface ContestMapper extends BaseMapper<Contest> {

    List<String> getExperimentCid(@Param("startTime") Long startTime,@Param("endTime") Long endTime);
}
