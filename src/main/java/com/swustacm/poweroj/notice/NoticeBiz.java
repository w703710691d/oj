package com.swustacm.poweroj.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.notice.entity.Notice;
import com.swustacm.poweroj.notice.mapper.NoticeMapper;
import com.swustacm.poweroj.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@Component
public class NoticeBiz {

    @Autowired
    UserService userService;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    NoticeService noticeService;

    @Autowired
    JwtUtil jwtUtil;

    public CommonResult<IPage<Notice>> getAllNotices(PageParam pageParam){
        IPage<Notice> noticeIPage = new Page<>(pageParam.getPage(),pageParam.getLimit());
        String notAdmin = null;
        if (userService.hasRole(GlobalConstant.ADMIN)){
            return CommonResult.ok(noticeMapper.getAllNotices(noticeIPage,notAdmin));
        }else{
            notAdmin = "where notice.status=1";
            return CommonResult.ok(noticeMapper.getAllNotices(noticeIPage,notAdmin));
        }
    }

    public CommonResult<Notice> deleteNotice(Integer id){
        Notice notice = noticeService.getById(id);
        notice.setStatus(false);
        if (noticeService.updateById(notice)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("删除失败");
        }
    }

    public CommonResult<Notice> addNotice(Notice notice,Date startTime_Temp,Date endTime_Temp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date newDate = new Date();
            String publishTime = sdf.format(newDate);
            String startTime = sdf.format(startTime_Temp);
            String endTime = sdf.format(endTime_Temp);
            notice.setCtime((int) (sdf.parse(publishTime).getTime() / 1000));
            notice.setStartTime((int) (sdf.parse(startTime).getTime() / 1000));
            notice.setEndTime((int) (sdf.parse(endTime).getTime() / 1000));
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
        }

        notice.setUid(jwtUtil.getUserInfo().getUid());
        notice.setEditorUid(jwtUtil.getUserInfo().getUid());
        notice.setStatus(true);
        if (noticeService.save(notice)){
            return CommonResult.ok();
        }else {
            return  CommonResult.error("添加失败");
        }
    }

    public CommonResult<Notice> updateNotice(Notice notice){
        notice.setUid(jwtUtil.getUserInfo().getUid());
        if (noticeService.updateById(notice)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("更新失败");
        }
    }
}
