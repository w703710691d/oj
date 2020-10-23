package com.swustacm.poweroj.news;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.news.entity.News;
import com.swustacm.poweroj.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-22
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsBiz newsBiz;

    /**
     * 新闻分页显示
     * @param param
     * @return
     */
    @PostMapping("/index")
    public CommonResult<IPage<News>> index(@RequestBody @Validated PageParam param){
        return newsBiz.showNews(param);
    }

    /**
     * 查询单条新闻
     * @param id
     * @return
     */
    @PostMapping("/show")
    public CommonResult<News> showNews(@RequestParam Integer id){
        return CommonResult.ok(newsService.getNews(id));
    }

}

