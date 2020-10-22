package com.swustacm.poweroj.solution;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.conest.ConestVar;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;
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

    @Autowired
    UserService userService;

    @PostMapping("/index")
    public CommonResult<IPage<Solution>> showStatus(@RequestBody @Validated ShowSolutionParam param) {
        return solutionBiz.showSolution(param);
    }

    /**
     * 查看提交的代码（只有正确的代码能够查看）
     */

    @PostMapping("/code")
    public CommonResult<Map> show(@RequestParam Integer sid) {
        Solution solution = solutionService.findSolution(sid);
        HashMap<Object, Object> map = new HashMap<>();
        Integer cid = solution.getCid();
        if (cid != null && cid > 0) {
            Integer num = solution.getNum();
            String problemTitle;
            problemTitle = solutionService.getProblemTitle(cid, num);
            map.put("problemTitle", problemTitle);
        }
        map.put("submitLanguage", solutionService.getLanguage(solution.getLanguage()));
        map.put("time", solution.getTime());

        if (userService.hasRole("admin") || userService.hasRole("teacher")) {
            map.put("source",solution.getSource());
            map.put("result", ConestVar.resultType.get(solution.getResult()));
            return CommonResult.ok(map);
        }else if (solution.getResult().equals(0)){
            map.put("source",solution.getSource());
            map.put("result", ConestVar.resultType.get(solution.getResult()));
            return CommonResult.ok(map);
        }else{
            return CommonResult.error("没有查看权限");
        }
    }
}

