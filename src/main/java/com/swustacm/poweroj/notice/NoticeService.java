package com.swustacm.poweroj.notice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.notice.entity.Notice;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-29
 */

public interface NoticeService extends IService<Notice> {

    /**
     * 根据新闻id取出新闻
     * @param id
     * @return
     */
    Notice getNotice(Integer id);

}
