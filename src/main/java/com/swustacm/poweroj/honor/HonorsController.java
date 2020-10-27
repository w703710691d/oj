package com.swustacm.poweroj.honor;


import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.honor.entity.Honors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/honors")
public class HonorsController {

    @Autowired
    HonorsService honorsService;

    @Autowired
    HonorBiz honorBiz;

    @PostMapping("index")
    public CommonResult<List<Honors>> index(){
        return CommonResult.ok(honorsService.getHonors());
    }

    @PostMapping("/addHonor")
    public CommonResult<Honors> addHonor(@RequestBody Honors honors){
        return honorBiz.addHonor(honors);
    }

    @PostMapping("/deleteHonor")
    public CommonResult<Honors> deleteHonor(@RequestParam Integer id){
        return honorBiz.deleteHonor(id);
    }
}

