package com.swustacm.poweroj.download;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.download.entity.Resource;
import com.swustacm.poweroj.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @Autowired
    DownloadBiz downloadBiz;

    /**
     * 显示资源（分页）
     * @param page
     * @return
     */
    @PostMapping("index")
    public CommonResult<IPage<Resource>> index(@RequestBody @Validated PageParam page){
        return downloadBiz.showAllResource(page);
    }

    /**
     * 添加资源
     * @param resource
     * @return
     */
    @PostMapping("addResource")
    public CommonResult<Resource> addResource(@RequestBody Resource resource){
        return downloadBiz.addResource(resource);
    }

    /**
     * 根据id删除资源
     * @param id
     * @return
     */
    @PostMapping("deleteResource")
    public CommonResult<Resource> deleteResource(@RequestParam Integer id){
        return downloadBiz.deleteResource(id);
    }

}

