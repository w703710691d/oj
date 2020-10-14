package com.swustacm.poweroj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.solution.entity.Solution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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
}
