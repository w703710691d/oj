package com.swustacm.poweroj.problem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.params.PageParam;
import com.swustacm.poweroj.problem.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.swustacm.poweroj.problem.entity.ProblemStatus;
import com.swustacm.poweroj.solution.entity.Solution;
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

    Problem getProblemForShow(Integer pid);

    Problem getProblem(Integer pid);

    List<ProblemStatus> getProblemStatus(Integer pid);

    Page<Solution> getProblemUser(Integer pid , PageParam page);
}
