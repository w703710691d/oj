package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
public interface SolutionService extends IService<Solution> {
    Page<Solution> showSolution(ShowSolutionParam param,Integer status);
}
