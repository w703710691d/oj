package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SolutionBiz {

    @Autowired
    SolutionService solutionService;

    @Autowired
    UserService userService;

    public CommonResult<IPage<Solution>> showSolution(ShowSolutionParam param){
        Integer status = null;
        if (!userService.hasRole("admin") && !userService.hasRole("teacher")) {
            status = 1;
        }
        IPage<Solution> solutionPage = solutionService.showSolution(param,status);
        HashMap<String, Object> map = new HashMap<>();
        return CommonResult.ok(solutionPage);
    }
}
