package com.swustacm.poweroj.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.poweroj.honor.entity.Honors;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-23
 */
@Component
public interface HonorsMapper extends BaseMapper<Honors> {
    /**
     * 获取荣誉列表
     * @return
     */
    List<Honors> getHoners();
}
