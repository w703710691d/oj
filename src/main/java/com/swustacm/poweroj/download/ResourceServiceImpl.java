package com.swustacm.poweroj.download;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.download.entity.Resource;
import com.swustacm.poweroj.download.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-27
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public IPage<Resource> resourcePage(PageParam param) {
        IPage<Resource> page = new Page<>(param.getPage(),param.getLimit());
        return resourceMapper.getAllResource(page);
    }
}
