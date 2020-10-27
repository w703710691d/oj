package com.swustacm.poweroj.download;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.download.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.params.PageParam;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-27
 */

public interface ResourceService extends IService<Resource> {

    /**
     *查找所有的资源
     * @param page
     * @return
     */
    IPage<Resource> resourcePage(PageParam page);



}
