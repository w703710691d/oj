package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 提交结果分页处理
 * @author lizhihao
 */
@Component
public class SolutionBiz {
    @Autowired
    SolutionService solutionService;

    public CommonResult<IPage<Solution>> showSolution(ShowSolutionParam param) {
        IPage<Solution> solutionPage = solutionService.showSolution(param);
        return CommonResult.ok(solutionPage);
    }
}
