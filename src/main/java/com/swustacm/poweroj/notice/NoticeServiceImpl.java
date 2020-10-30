package com.swustacm.poweroj.notice;

import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.notice.entity.Notice;
import com.swustacm.poweroj.mapper.NoticeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-29
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserService userService;

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public Notice getNotice(Integer id) {
        String isAdmin = null;
        Notice notice = noticeService.getById(id);
        notice.setView(notice.getView() + 1);
        noticeService.updateById(notice);
        if (userService.hasRole(GlobalConstant.ADMIN)){
            isAdmin = "AND startTime &lt; UNIX_TIMESTAMP() AND n.status=1";
            return noticeMapper.getNotice(isAdmin,id);
        }else{
            return noticeMapper.getNotice(isAdmin,id);
        }
    }
}
