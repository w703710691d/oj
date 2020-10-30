package com.swustacm.poweroj.notice;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.notice.entity.Notice;
import com.swustacm.poweroj.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-29
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeBiz noticeBiz;

    @Autowired
    NoticeService noticeService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * Notice首页
     * @param param
     * @return
     */
    @PostMapping("/index")
    public CommonResult<IPage<Notice>> index(@RequestBody @Validated PageParam param){
        return noticeBiz.getAllNotices(param);
    }

    /**
     * 查询单条notice
     * @param id Notice的id
     * @return
     */
    @PostMapping("/getNotice")
    public CommonResult<Notice> getNotice(@RequestParam Integer id){
        return CommonResult.ok(noticeService.getNotice(id));
    }



    /**
     * 根据新闻id删除notice
     * @param id
     * @return
     */
    @PostMapping("/deleteNotice")
    public CommonResult<Notice> deleteNotice(@RequestParam Integer id){
        return noticeBiz.deleteNotice(id);
    }

    /**
     * 添加新闻
     * @param notice
     * @return
     */
    @PostMapping("/addNotice")
    public CommonResult<Notice> addNotice(@RequestBody Notice notice,
                                          @RequestParam Date startTime,
                                          @RequestParam Date endTime){
        return noticeBiz.addNotice(notice,startTime,endTime);
    }

    /**
     * 根据notice的id更新某条notice
     * @param notice
     * @return
     */
    @PostMapping("/updateNotice")
    public CommonResult<Notice> updateNotice(@RequestBody Notice notice){
        notice.setEditorUid(jwtUtil.getUserInfo().getUid());
        return noticeBiz.updateNotice(notice);
    }
}

