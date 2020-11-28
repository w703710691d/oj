package com.swustacm.poweroj.honor;

import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.honor.entity.Honors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HonorBiz {

    @Autowired
    HonorsService honorsService;

    public CommonResult<Honors> addHonor(Honors honors){
        if (honorsService.save(honors)){
            return CommonResult.ok();
        }else {
            return CommonResult.error("添加失败");
        }
    }

    public CommonResult<Honors> deleteHonor(Integer id){
        if (honorsService.removeById(id)) {
            return CommonResult.ok();
        }else{
            return CommonResult.error("删除失败");
        }
    }

}
