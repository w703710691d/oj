package com.swustacm.poweroj.topic;

import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.topic.entity.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@Component
public class TopicBiz {
    @Autowired
    TopicService topicService;

    @Autowired
    JwtUtil jwtUtil;
    public CommonResult<Topic> deleteTopic(Integer id){
        Topic topic = topicService.getById(id);
        if (topic.getStatus()) {
            topic.setStatus(false);
            topicService.updateById(topic);
            return CommonResult.ok();
        }else{
            return CommonResult.error("删除失败,该Topic可能已经被删除");
        }
    }

    public CommonResult<Topic> removeTopic(Integer id){
        if (topicService.removeById(id)){
            return CommonResult.ok();
        }else{
            return CommonResult.error("彻底删除失败，可能已经不存在此Topic");
        }
    }

}
