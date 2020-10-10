package com.swustacm.poweroj.problem;


import com.swustacm.poweroj.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.swustacm.poweroj.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ProblemBiz {
    @Autowired
    ProblemService problemService;
    @Autowired
    UserService userService;
    public CommonResult searchAll(ProblemSearchParam param) {
        Integer status = null;
        if (!userService.hasRole("admin") && !userService.hasRole("teacher")) {
                 status = 1;
        }
        Page<Problem> problemPage = problemService.searchProblem(param,status);
        Map<String,Object> map = new HashMap<>();
        map.put("total",problemPage.getTotal());
        map.put("current",problemPage.getCurrent());
        map.put("queryList",problemPage.getRecords());
        return CommonResult.ok(map);
    }
}
