package com.swustacm.poweroj.news;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.news.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class NewsBiz {
    @Autowired
    NewsService newsService;

    public CommonResult<IPage<News>> showNews(PageParam param) {
        IPage<News> newsPage = newsService.getNewsPage(param);
        return CommonResult.ok(newsPage);
    }

    public CommonResult<News> addNews(News news){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date newDate = new Date();
            String publishTime = sdf.format(newDate);
            news.setTime((int) (sdf.parse(publishTime).getTime() / 1000));
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
        }

        if (newsService.save(news)){
            return CommonResult.ok();
        }else {
            return CommonResult.error("添加失败");
        }
    }

    public CommonResult<News> editNews(News news){
        if (newsService.updateById(news)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("更改失败");
        }
    }

    public CommonResult<News> deleteNews(Integer id){
        if (newsService.removeById(id)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("删除失败");
        }
    }

}
