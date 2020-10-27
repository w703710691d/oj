package com.swustacm.poweroj.download;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.download.entity.Resource;
import com.swustacm.poweroj.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lizhihao
 */
@Component
public class DownloadBiz {

    @Autowired
    ResourceService resourceService;

    public CommonResult<IPage<Resource>> showAllResource(@RequestBody PageParam param){
        IPage<Resource> page = resourceService.resourcePage(param);
        return CommonResult.ok(page);
    }


    public CommonResult<Resource> addResource(Resource resource){
        if (resourceService.save(resource)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("添加失败");
        }
    }

    public CommonResult<Resource> deleteResource(Integer id){
        if (resourceService.removeById(id)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("删除失败");
        }
    }

}
