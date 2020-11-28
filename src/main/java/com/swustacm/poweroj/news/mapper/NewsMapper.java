package com.swustacm.poweroj.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.news.entity.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-22
 */

@Repository
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 新闻分页查询
     * @param param
     * @return
     */
    IPage<News> getNewsPage(IPage<News> param);

    /**
     * 查询单条新闻
     * @param id
     * @return
     */
    News getNews(@Param("id") Integer id);
}
