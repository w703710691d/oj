package com.swustacm.poweroj.problem;


import com.swustacm.poweroj.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.swustacm.poweroj.user.UserService;

import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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

    public CommonResult showProblem(Integer pid) {
        Problem  problem = problemService.getById(pid);
        if (problem == null){
            return CommonResult.error();
        }
        Integer status = null;
        if (!userService.hasRole("admin")){

            status = 1;
        }
        Integer prevPid = problemService.getPrevPid(pid,status);
        Integer nextPid = problemService.getNextPid(pid,status);
        List<Record> list = problemService.getTags(pid);
        Integer userResult = problemService.getUserResult(pid);
        Map<String,Object> map = new HashMap<>();
        map.put("prevPid",prevPid);
        map.put("nextPid",nextPid);
        map.put("list",list);
        map.put("problem",problem);
        map.put("userResult",userResult);
        return CommonResult.ok(map);


    }
}
