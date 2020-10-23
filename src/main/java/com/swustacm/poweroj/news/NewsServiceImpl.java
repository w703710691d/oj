package com.swustacm.poweroj.news;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.news.entity.News;
import com.swustacm.poweroj.mapper.NewsMapper;
import com.swustacm.poweroj.news.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.params.PageParam;
import com.swustacm.poweroj.solution.entity.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-22
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    NewsMapper newsMapper;

    @Override
    public IPage<News> getNewsPage(PageParam param) {
        IPage<News> newsPage = new Page<>(param.getPage(),param.getLimit());
        return newsMapper.getNewsPage(newsPage);
    }

    @Override
    public News getNews(Integer id) {
        return newsMapper.getNews(id);
    }
}
