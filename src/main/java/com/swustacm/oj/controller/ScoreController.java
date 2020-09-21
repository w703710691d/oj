package com.swustacm.oj.controller;


import com.swustacm.oj.common.CommonResult;
import com.swustacm.oj.entity.ExperienceScore;
import com.swustacm.oj.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩管理接口
 *
 * @author lixiaobo
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/user")
public class ScoreController {

    @Autowired
    ScoreService userService;

    /**
     * 根据实验id查询成绩 8个实验的id
     * @param num_1 实验id
     * @param num_2
     * @param num_3
     * @param num_4
     * @param num_5
     * @param num_6
     * @param num_7
     * @param num_8
     * @return
     */
    @GetMapping("/findScore")
    public CommonResult<List<ExperienceScore>> findScore(
            @RequestParam Integer num_1,
            @RequestParam Integer num_2,
            @RequestParam Integer num_3,
            @RequestParam Integer num_4,
            @RequestParam Integer num_5,
            @RequestParam Integer num_6,
            @RequestParam Integer num_7,
            @RequestParam Integer num_8

    ) {
        List<ExperienceScore> experienceScores = userService.getScore(num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8);

        return CommonResult.ok(experienceScores);
    }

}

