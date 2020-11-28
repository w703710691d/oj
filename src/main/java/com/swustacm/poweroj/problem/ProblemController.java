package com.swustacm.poweroj.problem;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 *  Problem
 *
 *
 * @author 张成云
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    ProblemBiz problem;

    /**
     * 题目分页查询
     * @param problemSearchParam 页码，每页数据数，title，source, limit, page
     * @return
     */
    @PostMapping("/index")
    public CommonResult<IPage<Problem>> index(@RequestBody @Validated ProblemSearchParam problemSearchParam){
        return problem.searchAll(problemSearchParam);
    }

    @PostMapping("/getProblemListForShow")
    public CommonResult<IPage<Problem>> getProblemListForShow(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return null;
    }


    /**
     * 更具题目id返回题目的内容
     * @param pid  题目id
     * @return
     */
    @GetMapping("/show")
    public CommonResult<Map<String,Object>> show(@RequestParam Integer pid){
        return problem.showProblem(pid);
    }

    /**
     * 查询该题目的状态信息
     * @param pid 题目id
     * @return
     */
    @PostMapping("/status")
    public CommonResult status(@RequestParam Integer pid ,@RequestBody @Validated PageParam page){
        return problem.getStatus(pid ,page);
    }



}

