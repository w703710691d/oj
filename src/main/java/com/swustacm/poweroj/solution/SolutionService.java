package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
public interface SolutionService extends IService<Solution> {

    /**
     * 查询所有人提交结果分页
     * @param param 选择参数
     * @return
     */
    Page<Solution> showSolution(ShowSolutionParam param);

    /**
     * 通过sid查找对应提交人的信息和提交信息
     * @param sid
     * @return
     */
    Solution findSolution(Integer sid);

    /**
     * 通过从cid，num找到题目标题
     * @param cid
     * @param num
     * @return
     */
    String getProblemTitle(Integer cid, Integer num);

}
