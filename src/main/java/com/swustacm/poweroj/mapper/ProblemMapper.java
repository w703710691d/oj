package com.swustacm.poweroj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.problem.entity.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.poweroj.problem.entity.ProblemStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.record.Record;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张成云
 * @since 2020-10-09
 */
@Component
public interface ProblemMapper extends BaseMapper<Problem> {

    Page<Problem> searchAll(@Param(value = "page") Page<Problem> page, @Param(value = "source") String source, @Param(value = "title") String title, @Param(value = "status")Integer status);

    Integer getPrevPid(@Param(value = "pid")Integer pid,@Param(value = "status")Integer status);

    Integer getNextPid(Integer pid, Integer status);

    List<Integer> getMaxMInPid();

    Integer getPid(Integer randomPid,Integer status);

    List<Record> getTags(Integer pid);

    Integer getUserResult(Integer pid, Integer uid);

    List<ProblemStatus> getProblemStatus(Integer pid);
}
