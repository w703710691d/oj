package com.swustacm.poweroj.download;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.download.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lizhihao
 */
@Slf4j
@Component
public class DownloadBiz {

    @Autowired
    ResourceService resourceService;

    public CommonResult<IPage<Resource>> showAllResource(@RequestBody PageParam param){
        IPage<Resource> page = resourceService.resourcePage(param);
        return CommonResult.ok(page);
    }


    public CommonResult<Resource> addResource(Resource resource){
        SimpleDateFormat sdf = new SimpleDateFormat();
        try{
            Date date = new Date();
            String uploadDate = sdf.format(date);
            resource.setCtime((int)sdf.parse(uploadDate).getTime () / 10000);
        }catch (ParseException e){
            log.error(e.getLocalizedMessage());
        }

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
