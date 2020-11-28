package com.swustacm.poweroj.solution;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * solution
 *
 *
 * @author lizhihao
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/solution")
public class SolutionController {
    @Autowired
    SolutionBiz solutionBiz;

    @Autowired
    SolutionService solutionService;

    @Autowired
    UserService userService;

    /**
     * 提交状态分页查询
     *
     * @param param result language pid userName limit page
     */
    @PostMapping("/index")
    public CommonResult<IPage<Solution>> showStatus(@RequestBody @Validated ShowSolutionParam param) {
        return solutionBiz.showSolution(param);
    }

    /**
     * 查看提交的代码（只有正确的代码能够查看）
     */
    @PostMapping("/code")
    public CommonResult<Solution> show(@RequestParam Integer sid) {
        Solution solution = solutionService.findSolution(sid);
        if(!userService.getCurrentUser().getUid().equals(solution.getUid()) &&
                userService.hasRole(GlobalConstant.ADMIN)) {
            return CommonResult.error("没有查看权限");
        } else {
            return CommonResult.ok(solution);
        }
    }
}

