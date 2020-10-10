package com.swustacm.poweroj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.problem.entity.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张成云
 * @since 2020-10-09
 */
@Component
public interface ProblemMapper extends BaseMapper<Problem> {

    Page<Problem> searchAll(Page<Problem> page, @Param(value = "source") String source, @Param(value = "title") String title, @Param(value = "status")Integer status);
}
