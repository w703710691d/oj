package com.swustacm.poweroj.download.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.download.entity.Resource;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-27
 */
@Component
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查找所以的资源
     * @param page
     * @return
     */
    IPage<Resource> getAllResource(IPage<Resource> page);
}
