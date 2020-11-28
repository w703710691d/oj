package com.swustacm.poweroj.news;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.news.entity.News;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-22
 */
public interface NewsService extends IService<News> {
    /**
     * 所有新闻分页查询
     * @param param
     * @return
     */
    IPage<News> getNewsPage(PageParam param);

    /**
     * 查询单条新闻
     * @param id
     * @return
     */
    News getNews(Integer id);

}
