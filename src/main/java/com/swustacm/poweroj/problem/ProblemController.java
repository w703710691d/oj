package com.swustacm.poweroj.problem;


import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
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
     * @param problemSearchParam 页码，每页数据数，title，source
     * @return
     */
    @PostMapping("/index")
    public CommonResult index(@RequestBody ProblemSearchParam problemSearchParam){
        return problem.searchAll(problemSearchParam);
    }

}

