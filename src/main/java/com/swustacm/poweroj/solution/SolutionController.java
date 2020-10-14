package com.swustacm.poweroj.solution;


import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/solution")
public class SolutionController {

    /**
     * 提交状态分页查询 pageNumber, pageSize, result, language, pid, userName
     * @param
     */

    @Autowired
    SolutionBiz solutionBiz;
    @PostMapping("/index")
    public CommonResult showStatus(@RequestBody @Validated ShowSolutionParam param){
        return solutionBiz.showSolution(param);
    }

}

