package com.swustacm.poweroj.problem;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.constant.Constant;
import com.swustacm.poweroj.params.PageParam;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;

import com.swustacm.poweroj.user.entity.LogicalEnum;
import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ProblemBiz {
    @Autowired
    ProblemService problemService;
    @Autowired
    UserService userService;

    public  CommonResult getStatus(Integer pid ,PageParam page) {
        Problem problem = problemService.getProblem(pid);
        Map<String,Object>  map = new HashMap<>();

        map.put("program_languages", Constant.languageName);
        map.put("problem",problem);
        map.put("resultList",problemService.getProblemStatus(pid));

        Page<Solution> pageList = problemService.getProblemUser(pid,page);
        Map<String,Object> mapIn = new HashMap<>();
        mapIn.put("total",pageList.getTotal());
        mapIn.put("current",pageList.getCurrent());
        mapIn.put("queryList",pageList.getRecords());

        map.put("userPage",mapIn);
        return CommonResult.ok(map);
    }

    public CommonResult<IPage<Problem>> searchAll(ProblemSearchParam param) {
        Integer status = null;
        if (!userService.hasRole(Arrays.asList("admin", "teacher"), LogicalEnum.AND)) {
                 status = 1;
        }
        Page<Problem> problemPage = problemService.searchProblem(param,status);


        return CommonResult.ok(problemPage);
    }

    public CommonResult<Map<String,Object>> showProblem(Integer pid) {
        Problem  problem = problemService.getProblemForShow(pid);
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
