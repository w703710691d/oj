package com.swustacm.poweroj.mapper;

import com.swustacm.poweroj.problem.entity.ProgramLanguage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
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
