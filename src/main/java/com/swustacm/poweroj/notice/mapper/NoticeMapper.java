package com.swustacm.poweroj.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.notice.entity.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-29
 */
@Component
public interface NoticeMapper extends BaseMapper<Notice> {
    /**
     * 查询单条Notice
     * @param isAdmin
     * @param id
     * @return
     */
    Notice getNotice(@Param("isAdmin") String isAdmin,@Param("id") Integer id);

    /**
     * 查询所有的Notice
     * @param page
     * @param notAdmin
     * @return
     */
    IPage<Notice> getAllNotices(IPage<Notice> page,@Param("notAdmin") String notAdmin);
}
