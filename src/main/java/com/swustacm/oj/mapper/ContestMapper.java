package com.swustacm.oj.mapper;

import com.swustacm.oj.entity.Contest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
