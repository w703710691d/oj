package com.swustacm.poweroj.solution;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;

import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.conest.ConestVar;
import com.swustacm.poweroj.solution.entity.CodeInfo;
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
    public CommonResult<CodeInfo> show(@RequestParam Integer sid) {
        Solution solution = solutionService.findSolution(sid);
        CodeInfo codeInfo = new CodeInfo();
        Integer cid = solution.getCid();
        System.out.println(cid);
        if (cid != null && cid > 0) {
            Integer num = solution.getNum();
            codeInfo.setProblemTitle(solutionService.getProblemTitle(cid, num));
        }
        codeInfo.setSubmitLanguage(solutionService.getLanguage(solution.getLanguage()));
        codeInfo.setTime((solution.getTime()));

        if (userService.hasRole(GlobalConstant.ADMIN) || userService.hasRole(GlobalConstant.TEACHER)) {
            codeInfo.setSource(solution.getSource());
            codeInfo.setResult(ConestVar.resultType.get(solution.getResult()));
            return CommonResult.ok(codeInfo);
        } else if (solution.getResult().equals(0)) {
            codeInfo.setSource(solution.getSource());
            codeInfo.setResult(ConestVar.resultType.get(solution.getResult()));
            return CommonResult.ok(codeInfo);
        } else {
            return CommonResult.error("没有查看权限");
        }
    }
}

