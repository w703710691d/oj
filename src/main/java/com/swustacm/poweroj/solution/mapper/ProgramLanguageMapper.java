package com.swustacm.poweroj.solution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.poweroj.problem.entity.ProgramLanguage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张成云
 * @since 2020-10-17
 */
@Service
public interface ProgramLanguageMapper extends BaseMapper<ProgramLanguage> {

     List<ProgramLanguage> getAll();
}
