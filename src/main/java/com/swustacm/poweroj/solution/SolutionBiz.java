package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionBiz {

    @Autowired
    SolutionService solutionService;

    @Autowired
    UserService userService;

    public CommonResult<IPage<Solution>> showSolution(ShowSolutionParam param) {
        if (param.getLanguage() != null) {
            param.setLanguage(solutionService.findLanguageId(param.getLanguage()).toString());
        }
        IPage<Solution> solutionPage = solutionService.showSolution(param);
        return CommonResult.ok(solutionPage);
    }
}
