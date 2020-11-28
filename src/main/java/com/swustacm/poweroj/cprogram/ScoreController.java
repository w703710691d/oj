package com.swustacm.poweroj.cprogram;


import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.util.DateConvert;
import com.swustacm.poweroj.cprogram.entity.ExperienceScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 成绩管理接口
 *
 * @author xingzi
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/dev/score")
@Validated
public class ScoreController {

    @Autowired
    ScoreBiz scoreBiz;
    @Autowired
    ScoreService scoreService;

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
        List<ExperienceScore> experienceScores = scoreService.getScore(num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8);

        return CommonResult.ok(experienceScores);
    }

    /**
     * 根据时间获取成绩统计信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @apiNote 返回格式：
     * {
     *     "code": 200,
     *     "data": [
     *         {
     *             "rep2": null,
     *             "rep1": 0,
     *             "rep4": 0,
     *             "rep3": 0,
     *             "classes": null,
     *             "rep6": null,
     *             "rep5": 0,
     *             "ac1": 66,
     *             "rep8": null,
     *             "rep7": null,
     *             "ac3": 0,
     *             "realName": " 陈淼",
     *             "ac2": null,
     *             "ac5": 8,
     *             "ac4": 8,
     *             "ac7": null,
     *             "ac6": null,
     *             "ac8": null,
     *             "name": "chenmiao"
     *         }
     *         ]
     */
    @GetMapping("/getScoreByTime")
    public CommonResult<List<Map<String,Object>>> getScoreByTime(@RequestParam @NotEmpty(message = "学期开始时间不能为空") String startTime,
                                                                 @NotEmpty(message = "学期结束时间不能为空")@RequestParam String endTime){
        return  scoreBiz.getScoreTime(startTime,endTime);
    }

    /**
     * 根据学期统计成绩
     * @param year 年份
     * @param semester AUTUMN_TERM / SPRING_TERM
     * @return
     */
    @GetMapping("/getSemesterByYear")
    public CommonResult<List<Map<String,Object>>> getSemesterByYear(@RequestParam @NotEmpty(message = "学期开始时间不能为空") String year,
                                                                 @RequestParam DateConvert semester){
        return  scoreBiz.getSemesterByYear(year,semester);
    }

    @GetMapping("/getScoreExcel")
    public void getScoreExcel(HttpServletResponse response)throws IOException {
         scoreBiz.getScoreExcel(response);
    }
}
