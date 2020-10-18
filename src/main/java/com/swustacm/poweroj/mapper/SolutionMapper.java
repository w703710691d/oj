package com.swustacm.poweroj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.solution.entity.Solution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.poweroj.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
@Component
public interface SolutionMapper extends BaseMapper<Solution> {
    Page<Solution> solutionPage(Page<Solution> page,@Param("pid") String pid,@Param("userName") String userName,@Param("language") String language,@Param("result") String result,Integer status);

    Solution findSolution(@Param("sid") Integer sid);

    String getProblemTitle(@Param("cid") Integer cid,@Param("num") Integer num);

    String getLanguage(@Param("key") Integer key);

    User getUser(@Param("uid") Integer uid);
}
