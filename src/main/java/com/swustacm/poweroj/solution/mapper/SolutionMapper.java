package com.swustacm.poweroj.solution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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
    Page<Solution> solutionPage(Page<Solution> page, @Param("param")ShowSolutionParam param);

    Solution findSolution(@Param("sid") Integer sid);

    String getProblemTitle(@Param("cid") Integer cid,@Param("num") Integer num);

    Page<Solution> getSolutionPro(Page<Solution> page1, Integer pid);

}
