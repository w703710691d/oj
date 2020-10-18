package com.swustacm.poweroj.solution;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.problem.ProblemService;
import com.swustacm.poweroj.service.ContestService;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;
import com.swustacm.poweroj.user.entity.User;
import jdk.internal.instrumentation.Logger;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/solution")
public class SolutionController {

    /**
     * 提交状态分页查询
     *
     * @param
     */
    @Autowired
    SolutionBiz solutionBiz;

    @Autowired
    SolutionService solutionService;

    @PostMapping("/index")
    public CommonResult<IPage<Solution>> showStatus(@RequestBody @Validated ShowSolutionParam param) {
        return solutionBiz.showSolution(param);
    }

    /**
     * 查看提交的代码（只有正确的代码能够查看）
     */

    @PostMapping("code")
    public CommonResult<Map> show(@RequestParam Integer sid) {
        HashMap<Object, Object> map = new HashMap<>();
        Solution solution = solutionService.findSolution(sid);
        User user = solutionService.findUser(solution.getUid());

        String problemTitle;
        Integer cid = solution.getCid();

        if (cid != null && cid > 0) {
            Integer num = solution.getNum();
            problemTitle = solutionService.getProblemTitle(cid, num);
            map.put("problemTitle", problemTitle);
        }
        map.put("submitUser", solution.getUid());
        map.put("submitLanguage", solutionService.getLanguage(solution.getLanguage()));
        map.put("time", solution.getMtime());
        map.put("solution", solution);
        map.put("user", user.getName());
        return CommonResult.ok(map);
    }
}

