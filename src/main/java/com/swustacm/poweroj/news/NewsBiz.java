package com.swustacm.poweroj.news;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.news.entity.News;
import com.swustacm.poweroj.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *新闻分页
 * @author lizhihao
 */
@Component
public class NewsBiz {
    @Autowired
    NewsService newsService;

    public CommonResult<IPage<News>> showNews(PageParam param) {
        IPage<News> newsPage = newsService.getNewsPage(param);
        return CommonResult.ok(newsPage);
    }
}
