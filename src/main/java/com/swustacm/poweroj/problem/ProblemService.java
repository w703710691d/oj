package com.swustacm.poweroj.problem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.problem.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import org.apache.poi.hssf.record.Record;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张成云
 * @since 2020-10-09
 */
public interface ProblemService extends IService<Problem> {

    Page<Problem> searchProblem(ProblemSearchParam param,Integer status);

    Integer getPrevPid(Integer pid, Integer status);

    Integer getNextPid(Integer pid, Integer status);

    Integer getRandomPid(Integer pid, Integer status);

    List<Record> getTags(Integer pid);

    Integer getUserResult(Integer pid);
}
