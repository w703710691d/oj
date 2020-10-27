package com.swustacm.poweroj.honor;

import com.swustacm.poweroj.honor.entity.Honors;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-23
 */
public interface HonorsService extends IService<Honors> {
    /**
     *查询荣誉墙
     * @return
     */
    List<Honors> getHonors();
}
