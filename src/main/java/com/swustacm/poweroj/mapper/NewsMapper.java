package com.swustacm.poweroj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.news.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    IPage<News> getNewsPage(IPage<News> param);

    News getNews(@Param("id") Integer id);

}
