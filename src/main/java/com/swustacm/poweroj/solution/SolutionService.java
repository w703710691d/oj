package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.user.entity.User;

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
     * 通过代码语言查询对应代码语言的id
     * @param language
     * @return
     */
    Integer findLanguageId(String language);

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

    /**
     * 通过语言id找到对应的语言
     * @param value
     * @return
     */
    String getLanguage(Integer value);

    /**
     * 通过uid查找用户
     * @param uid
     * @return
     */
    User findUser(Integer uid);
}
